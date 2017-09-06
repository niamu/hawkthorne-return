(ns hawkthorne.router
  (:require [hawkthorne.routes :as routes]
            [hawkthorne.page :as page]
            [hawkthorne.state :as state]
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
           ;; on open
           (swap! state/state assoc-in [:channels channel] player)
           (swap! state/state assoc-in [:players player] {:x 0 :y 0})
           (http/send! channel (pr-str {:uuid player}))
           (doseq [[c p] (:channels @state/state)]
             (http/send! c (pr-str {:players (:players @state/state)})))

           (http/on-close channel
                          (fn [status]
                            (swap! state/state
                                   update-in [:channels] dissoc channel)
                            (swap! state/state
                                   update-in [:players] dissoc player)
                            (doseq [[c p] (:channels @state/state)]
                              (http/send! c
                                          (-> {:players (:players @state/state)}
                                              pr-str)))))

           (http/on-receive channel
                            (fn [data]
                              (let [sender (get-in @state/state
                                                   [:channels channel])
                                    cmd (merge {:uuid sender}
                                               (edn/read-string data))]
                                (state/handle-message cmd)
                                (doseq [[c p] (:channels @state/state)]
                                  (http/send!
                                   c
                                   (pr-str
                                    {:players
                                     (:players @state/state)})))))))))))

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

(defn name->path
  [route]
  (silk/depart routes/routes route))

#?(:cljs
   (defn path->name
     [url]
     (:domkm.silk/name (silk/arrive routes/routes url))))
