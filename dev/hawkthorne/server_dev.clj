(ns hawkthorne.server-dev
  (:require [hawkthorne.server :as server]
            [hawkthorne.state :as state]
            [figwheel-sidecar.repl-api :as ra]))

(defn stop
  []
  (ra/stop-figwheel!)
  (server/stop))

(defn start
  []
  (ra/start-figwheel!)
  (swap! state/state assoc :debugging? true)
  (server/start))

(defn restart [] (stop) (start))

(defn -main [] (start))
