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
               #?(:clj {:channels {}}))))

#?(:cljs
   (defn transit-post
     "Send queries to remote and await process returned data with db/import"
     [url]
     (fn [{:keys [remote] :as env} _]
       (.send XhrIo url
              (fn [e]
                (this-as this
                  (swap! state assoc :players
                         (:players (t/read (t/reader :json)
                                           (.getResponseText this))))))
              "POST" (t/write (t/writer :json) remote)
              #js {"Content-Type" "application/transit+json"}))))

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state query]} key params]
  {:value "default"})

(defmethod read :current/character
  [{:keys [state query]} key params]
  {:value #?(:clj {:name :abed :costume :base}
             :cljs (get-in @state [:players (:me @state) :character]
                           {:name :abed :costume :base}))})

(defmulti mutate om/dispatch)

(defmethod mutate 'current/character
  [env key {:keys [character costume me]}]
  {:remote true
   :action (fn []
             (swap! state update-in
                    [:players me]
                    assoc :character
                    {:name character
                     :costume costume})
             #?(:clj (:players @state)))})

(def parser
  (om/parser {:read read
              :mutate mutate}))

(def reconciler
  (om/reconciler (merge {:state state
                         :parser parser}
                        #?(:cljs {:send (transit-post "/api")}))))
