(ns hawkthorne.websocket
  (:refer-clojure :exclude [send])
  (:require [hawkthorne.player :as player]
            [hawkthorne.routes :as routes]
            [hawkthorne.state :as state]
            [hawkthorne.util :as util]
            #?(:clj [org.httpkit.server :as http])
            [domkm.silk :as silk]
            [#?(:clj clojure.core.async :cljs cljs.core.async) :as async
             :refer [#?(:clj go-loop)]]
            [#?(:clj clojure.edn :cljs cljs.reader) :as edn])
  #?(:cljs (:require-macros [cljs.core.async.macros :refer [go go-loop]])))

(defn receive
  [m]
  (condp #(contains? %2 %1) m
    :keys (swap! state/state assoc-in
                 [:players (:uuid m) :keys-pressed] (:keys m))
    :players (swap! state/state assoc :players (:players m))
    :uuid (swap! state/state assoc :me (:uuid m))
    :no-match))

#?(:cljs
   (defn connect!
     "Creates a WebSocket connection and returns an in and out channel
      in - data coming in from the server
      out - data being sent to the server"
     [uri]
     (let [ws (js/WebSocket. uri)
           in (async/chan)
           out (async/chan)]
       (set! (.-onopen ws)
             (fn [e] (swap! state/state #(assoc % :connected true))))
       (set! (.-onclose ws)
             (fn [e] (swap! state/state #(assoc % :connected false))))
       (set! (.-onerror ws)
             (fn [e] (swap! state/state #(assoc % :connected false))))
       (set! (.-onmessage ws)
             (fn [e] (async/put! in (edn/read-string (.-data e)))))
       (go (loop [msg (async/<! out)]
             (when msg
               (.send ws msg)
               (recur (async/<! out)))))
       (go (loop [msg (async/<! in)]
             (when msg
               (receive msg)
               (recur (async/<! in)))))
       {:in in :out out})))

#?(:cljs
   (defonce websocket
     (connect! (str "ws://" (.. js/window -location -host)
                    (silk/depart routes/routes :websocket)))))

(defn send
  #?(:clj [channel value]
     :cljs [method value])
  #?(:clj (http/send! channel value)
     :cljs (go (async/<! (async/timeout 50))
               (async/>! (:out websocket) {method value}))))

#?(:clj
   (defn open
     [channel player]
     (swap! state/state assoc-in [:channels channel] player)
     (player/join player)
     (http/send! channel (pr-str {:uuid player}))))

#?(:clj
   (defn on-close
     [channel player]
     (http/on-close channel
                    (fn [_]
                      (swap! state/state update-in [:channels] dissoc channel)
                      (player/leave player)))))

#?(:clj
   (defn on-receive
     [channel player]
     (http/on-receive channel
                      (fn [data]
                        (let [sender (get-in @state/state [:channels channel])
                              message (merge {:uuid sender}
                                             (edn/read-string data))]
                          (receive message))))))

#?(:clj
   (defn send-world-state
     "Send the current state of all players to each client"
     []
     (doseq [[player-id _] (:players @state/state)]
       (player/move player-id))
     (doseq [[channel player] (:channels @state/state)]
       (send channel (pr-str {:players
                              (->> (:players @state/state)
                                   (filter (fn [p]
                                             (= (-> p val :map)
                                                (get-in @state/state
                                                        [:players player
                                                         :map]))))
                                   (into {}))})))))

#?(:cljs
   (defn send-keys
     "Send the keys pressed on the client to the server"
     []
     (send :keys (:keys @state/state))))

(defn tick
  "Tickrate for the client command rate and server communication rate"
  [f]
  (let [start (async/chan)
        stop (async/chan)]
    (go-loop [running? true]
      (let [t (async/timeout util/tickrate)
            [_ ch] (async/alts! [stop t start])]
        (when running? (f))
        (condp = ch
          stop (recur false)
          t (recur running?)
          start (recur true))))
    [start stop]))

(defn tick-start
  []
  (if-let [[start _] (:tick @state/state)]
    (async/put! start true)
    (swap! state/state assoc :tick
           (tick #?(:clj send-world-state
                    :cljs send-keys)))))

(defn tick-stop
  []
  (when-let [[_ stop] (:tick @state/state)]
    (async/put! stop true)))
