(ns hawkthorne.router
  (:require [hawkthorne.routes :as routes]
            [hawkthorne.page :as page]
            [hawkthorne.state :as state]
            [hawkthorne.websocket :as websocket]
            [hawkthorne.style :as style]
            [domkm.silk :as silk]
            [#?(:clj clojure.edn :cljs cljs.reader) :as edn]
            #?@(:clj [[org.httpkit.server :as http]
                      [domkm.silk.serve :as serve]])))

(defn serve
  "Serve pages in a map as expected by Ring"
  [{:keys [status headers body]}]
  #?(:clj (fn [request]
            {:status (or status 200)
             :headers (or headers {"Content-Type" "text/html"})
             :body (page/wrap (page/react-root body {:ring request}))})
     :cljs (page/react-root body)))

(defmulti response identity)

#?(:clj
   (defmethod response :api
     [route]
     (fn [request]
       {:status 200
        :headers {"Content-Type" "application/transit+json"}
        :body (state/parser {:state (atom {})} (:transit-params request))})))

#?(:clj
   (defmethod response :websocket
     [route]
     (fn [request]
       (http/with-channel request channel
         (when-let [player (hash (:async-channel request))]
           (websocket/open channel player)
           (websocket/on-close channel player)
           (websocket/on-receive channel player))))))

#?(:clj
   (defmethod response :css
     [route]
     (fn [request]
       {:status 200
        :headers {"Content-Type" "text/css"}
        :body (style/render)})))

(defmethod response :game
  [route]
  (serve {:body page/Game}))

(defmethod response :default
  [route]
  (serve {:body page/Game}))

(defn route->response
  [matched-route]
  (response matched-route))

#?(:clj
   (def route-handler
     (serve/ring-handler routes/routes route->response)))

#?(:cljs
   (defn path->name
     [url]
     (:domkm.silk/name (silk/arrive routes/routes url))))

#?(:cljs
   (defn mount-route
     "Mount the React DOM Root corresponding with the current path"
     [path]
     (-> path path->name route->response)))
