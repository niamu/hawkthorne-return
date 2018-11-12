(defproject hawkthorne "0.1.0"
  :description "Journey to the Center of Hawkthorne"
  :url "http://projecthawkthorne.com"
  :dependencies [[org.clojure/clojure         "1.9.0"]
                 [org.clojure/clojurescript   "1.10.439"]
                 [org.clojure/core.async      "0.3.443"
                  :exclusions [org.clojure/tools.reader]]
                 [org.clojure/data.json       "0.2.6"]
                 [com.cognitect/transit-clj   "0.8.313"]
                 [com.cognitect/transit-cljs  "0.8.256"]
                 [org.omcljs/om               "1.0.0-beta4"]
                 [play-cljs                   "1.2.0"]
                 [http-kit                    "2.3.0"]
                 [ring/ring-core              "1.4.0"]
                 [ring/ring-codec             "1.0.0"]
                 [ring/ring-defaults          "0.2.1"]
                 [clj-time                    "0.14.0"]
                 [sablono                     "0.8.0"]
                 [hiccup                      "1.0.5"]
                 [garden                      "1.3.2"]
                 [com.domkm/silk              "0.1.2"]
                 [environ                     "1.1.0"]
                 [tmx2edn                     "0.1.0"]]
  :main hawkthorne.server
  :profiles {:uberjar {:aot :all
                       :uberjar-name "hawkthorne.jar"
                       :prep-tasks ["compile" ["cljsbuild" "once"]]}
             :dev {:main hawkthorne.server-dev
                   :source-paths ["src" "dev"]
                   :dependencies [[figwheel-sidecar "0.5.17"]]
                   :figwheel {:server-ip "0.0.0.0"}
                   :plugins [[lein-cljsbuild "1.1.7"]]}}
  :clean-targets ^{:protect false} ["target"
                                    "figwheel_server.log"
                                    "resources/public/js"]
  :cljsbuild {:builds
              [{:id "prod"
                :source-paths ["src"]
                :compiler {:main hawkthorne.client
                           :asset-path "/js/out"
                           :output-to "resources/public/js/hawkthorne.js"
                           :output-dir "resources/public/js/out"
                           :language-in :ecmascript5
                           :language-out :ecmascript5
                           :optimizations :simple
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
