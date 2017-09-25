(ns hawkthorne.state
  "Utilities for state parsing and sync with server"
  (:refer-clojure :exclude [read])
  (:require [cognitect.transit :as t]
            [om.next :as om]
            [#?(:clj clojure.edn :cljs cljs.reader) :as edn])
  #?(:cljs (:import [goog.net XhrIo])))

(defonce state
  (atom (merge {:me nil
                :players {}
                :keys-pressed #{}
                :camera {:x 0 :y 0}
                :tick nil}
               #?(:clj {:debugging? false
                        :channels {}}))))

#?(:cljs
   (defn transit-post
     "Send queries to remote and await process returned data with db/import"
     [url]
     (fn [{:keys [remote] :as env} _]
       (.send XhrIo url
              (fn [e]
                (this-as this
                  (let [data (t/read (t/reader :json)
                                     (.getResponseText this))]
                    (cond
                      (contains? data :game/debugging?)
                      (swap! state assoc :debugging? (:game/debugging? data))

                      (contains? data :players)
                      (swap! state assoc :players (:players data))))))
              "POST" (t/write (t/writer :json) remote)
              #js {"Content-Type" "application/transit+json"}))))

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state query]} key params]
  {:value "default"})

(defmethod read :current/character
  [{:keys [state query]} key params]
  {:value (get-in @state [:players (:me @state) :character]
                  {:name :troy :costume :base})})

(defmethod read :game/debugging?
  [{:keys [state query]} key params]
  {:value (get @state :debugging? false)
   :remote true})

(defmulti mutate om/dispatch)

(defmethod mutate 'current/character
  [env key {:keys [character costume me]}]
  {:remote true
   :action (fn []
             (swap! state update-in [:players me]
                    assoc :character {:name character
                                      :costume costume})
             #?(:clj (:players @state)))})

(defmethod mutate 'game/debugging?
  [{:keys [state]} key _]
  {:remote true
   :action (fn []
             (swap! state assoc :debugging? (not (:debugging? @state)))
             #?(:clj (:debugging? @state)))})

(def parser
  (om/parser {:read read
              :mutate mutate}))

(def reconciler
  (om/reconciler (merge {:state state
                         :parser parser}
                        #?(:cljs {:send (transit-post "/api")}))))
