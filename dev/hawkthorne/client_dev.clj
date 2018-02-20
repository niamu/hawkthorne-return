(ns hawkthorne.client-dev
  (:require [figwheel-sidecar.repl-api :as ra]))

(defn stop
  []
  (ra/stop-figwheel!))

(defn start
  []
  (ra/start-figwheel!))

(defn restart [] (stop) (start))

(defn -main [] (start))
