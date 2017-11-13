(ns hawkthorne.client
  (:require [hawkthorne.router :as router]
            [hawkthorne.tiled :as tiled]
            [hawkthorne.websocket :as websocket]))

(enable-console-print!)

(defn -main
  []
  (tiled/load-maps)
  (websocket/tick-start)
  (router/mount-route (.. js/window -location -pathname)))

(-main)
