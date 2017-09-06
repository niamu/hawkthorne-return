(ns hawkthorne.websocket
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [hawkthorne.state :as state]
            [hawkthorne.router :as router]
            [cljs.core.async :refer [>! <! put! chan timeout]]
            [cljs.reader :as edn]))

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
            (state/handle-message msg)
            (recur (<! in)))))
    {:in in :out out}))

(defonce websocket
  (connect! (str "ws://" (.. js/window -location -host)
                 (router/name->path :websocket))))

(defn send
  [method value]
  (go (<! (timeout 50))
      (>! (:out websocket) {method value})))
