(ns hawkthorne.server-dev
  (:require [hawkthorne.server :as server]
            [figwheel-sidecar.repl-api :as ra]))

(defn stop
  []
  (ra/stop-figwheel!)
  (server/stop))

(defn start
  []
  (ra/start-figwheel!)
  (server/start))

(defn restart [] (stop) (start))

(defn -main [] (start))
