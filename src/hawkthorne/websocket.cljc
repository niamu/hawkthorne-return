(ns hawkthorne.websocket
  (:refer-clojure :exclude [send])
  (:require [hawkthorne.state :as state]
            [hawkthorne.player :as player]
            [hawkthorne.routes :as routes]
            #?(:clj [org.httpkit.server :as http])
            [domkm.silk :as silk]
            #?(:cljs [cljs.core.async :refer [>! <! put! chan timeout]])
            [#?(:clj clojure.edn :cljs cljs.reader) :as edn])
  #?(:cljs (:require-macros [cljs.core.async.macros :refer [go]])))

(defn receive
  [m]
  (condp #(contains? %2 %1) m
    :keys (player/move (:uuid m) (:keys m))
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
           in (chan)
           out (chan)]
       (set! (.-onopen ws)
             (fn [e] (swap! state/state #(assoc % :connected true))))
       (set! (.-onclose ws)
             (fn [e] (swap! state/state #(assoc % :connected false))))
       (set! (.-onerror ws)
             (fn [e] (swap! state/state #(assoc % :connected false))))
       (set! (.-onmessage ws)
             (fn [e] (put! in (edn/read-string (.-data e)))))
       (go (loop [msg (<! out)]
             (when msg
               (.send ws msg)
               (recur (<! out)))))
       (go (loop [msg (<! in)]
             (when msg
               (receive msg)
               (recur (<! in)))))
       {:in in :out out})))

#?(:cljs
   (defonce websocket
     (connect! (str "ws://" (.. js/window -location -host)
                    (silk/depart routes/routes :websocket)))))

(defn send
  #?(:clj [channel value]
     :cljs [method value])
  #?(:clj (http/send! channel value)
     :cljs (go (<! (timeout 50))
               (>! (:out websocket) {method value}))))

#?(:clj
   (defn open
     [channel player]
     (swap! state/state assoc-in [:channels channel] player)
     (player/join player)
     (http/send! channel (pr-str {:uuid player}))
     (doseq [[c p] (:channels @state/state)]
       (let [players {:players (:players @state/state)}]
         (send c (pr-str players))))))

#?(:clj
   (defn on-close
     [channel player]
     (http/on-close channel
                    (fn [_]
                      (swap! state/state update-in [:channels] dissoc channel)
                      (player/leave player)
                      (doseq [[c p] (:channels @state/state)]
                        (let [players {:players (:players @state/state)}]
                          (send c (pr-str players))))))))

#?(:clj
   (defn on-receive
     [channel player]
     (http/on-receive channel
                      (fn [data]
                        (let [sender (get-in @state/state [:channels channel])
                              message (merge {:uuid sender}
                                             (edn/read-string data))
                              _ (receive message)
                              players {:players (:players @state/state)}]
                          (doseq [[c p] (:channels @state/state)]
                            (send c (pr-str players))))))))
