(defproject hawkthorne "0.1.0"
  :description "Journey to the Center of Hawkthorne"
  :url "http://projecthawkthorne.com"
  :dependencies [[org.clojure/clojure         "1.9.0"]
                 [org.clojure/clojurescript   "1.9.946"]
                 [org.clojure/data.json       "0.2.6"]
                 [org.omcljs/om               "1.0.0-beta2"]
                 [com.domkm/silk              "0.1.2"]
                 [cljsjs/phaser-ce            "2.8.2-0"]
                 [garden                      "1.3.2"]
                 [tmx2edn                     "0.1.0"]]
  :main hawkthorne.client
  :profiles {:uberjar {:aot :all
                       :uberjar-name "hawkthorne.jar"
                       :prep-tasks ["compile" ["cljsbuild" "once"]]}
             :dev {:main hawkthorne.client-dev
                   :source-paths ["src" "dev"]
                   :dependencies [[figwheel-sidecar "0.5.13"]]
                   :figwheel {:server-ip "0.0.0.0"}
                   :plugins [[lein-cljsbuild "1.1.7"]]}}
  :clean-targets ^{:protect false} ["target"
                                    "figwheel_server.log"
                                    "resources/public/js"
                                    "node_modules"
                                    "package.json"
                                    "package-lock.json"]
  :cljsbuild {:builds
              [{:id "prod"
                :source-paths ["src"]
                :compiler {:main hawkthorne.client
                           :asset-path "/js/out"
                           :output-to "resources/public/js/hawkthorne.js"
                           :output-dir "resources/public/js/out"
                           :optimizations :advanced
                           :parallel-build true}}
               {:id "dev"
                :figwheel true
                :source-paths ["src" "dev"]
                :compiler {:main hawkthorne.client
                           :asset-path "/js/figwheel"
                           :output-to "resources/public/js/hawkthorne.js"
                           :output-dir "resources/public/js/figwheel"
                           :optimizations :none
                           :parallel-build true
                           :source-map true
                           :source-map-timestamp true}}]})
