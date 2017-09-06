(ns hawkthorne.state
  "Utilities for state parsing and sync with server"
  (:refer-clojure :exclude [read])
  (:require [cognitect.transit :as t]
            [om.next :as om]
            [#?(:clj clojure.edn :cljs cljs.reader) :as edn])
  #?(:cljs (:import [goog.net XhrIo])))

(defonce state
  (atom (merge {:players {}
                :me nil}
               #?(:clj {:channels {}}))))

(defn add-remove-players
  [players]
  (swap! state assoc :players players))

(defn move-player
  [player command]
  (condp = command
    :left (swap! state update-in [:players player :x] dec)
    :right (swap! state update-in [:players player :x] inc)
    :down (swap! state update-in [:players player :y] inc)
    :up (swap! state update-in [:players player :y] dec)))

(defn handle-message
  [m]
  (condp #(contains? %2 %1) m
    :move (move-player (:uuid m) (:move m))
    :players (add-remove-players (:players m))
    :uuid (swap! state assoc :me (:uuid m))
    :no-match))

#?(:cljs
   (defn transit-post
     "Send queries to remote and await process returned data with db/import"
     [url]
     (fn [{:keys [remote] :as env} _]
       (.send XhrIo url
              (fn [e]
                (this-as this
                  ;; TODO: Implement client-side state management
                  (prn (t/read (t/reader :json)
                               (.getResponseText this)))))
              "POST" (t/write (t/writer :json) remote)
              #js {"Content-Type" "application/transit+json"}))))

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state query]} key params]
  {:value "default"})

(def parser
  (om/parser {:read read}))

(defn reconciler
  ([]
   (reconciler {}))
  ([session]
   (om/reconciler (merge {:state (atom session)
                          :parser parser}
                         #?(:cljs {:send (transit-post "/api")})))))
