(ns hawkthorne.client
  (:require [hawkthorne.router :as router]
            [hawkthorne.tiled :as tiled]
            [hawkthorne.websocket :as websocket]))

(enable-console-print!)

(defn -main
  []
  (websocket/tick-start)
  (tiled/load-maps)
  (router/mount-route (.. js/window -location -pathname)))

(-main)
