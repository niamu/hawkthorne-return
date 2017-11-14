(ns hawkthorne.state
  "Utilities for state parsing and sync with server"
  (:refer-clojure :exclude [read])
  (:require [hawkthorne.characters :as characters]
            [clojure.set :refer [difference]]
            [cognitect.transit :as t]
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

(defmethod read :game/debugging?
  [{:keys [state query]} key params]
  {:value (get @state :debugging? false)
   :remote true})

(defmethod read :game/fps
  [{:keys [state query]} key params]
  {:value (int (get @state :fps 0))})

(defn heroes-available
  []
  (vec (difference (set characters/greendale-seven)
                   (set (map (fn [[_ player]]
                               (get-in player [:character :name]))
                             (:players @state))))))

(defmethod read :heroes/available
  [{:keys [state query]} key params]
  {:value (heroes-available)})

(defmethod read :heroes/current
  [{:keys [state query]} key params]
  {:value (get-in @state [:players (:me @state) :character :name])})

(defmulti mutate om/dispatch)

(defmethod mutate 'game/debugging?
  [_ _ _]
  {:remote true
   :action (fn []
             (swap! state assoc :debugging? (not (:debugging? @state)))
             #?(:clj (:debugging? @state)))})

(defmethod mutate 'heroes/change
  [env _ {:keys [me selected]}]
  {:remote true
   :action (fn []
             (swap! state assoc-in
                    [:players me :character :name]
                    selected)
             #?(:clj {:hero selected}))})

(def parser
  (om/parser {:read read
              :mutate mutate}))

(def reconciler
  (om/reconciler (merge {:state state
                         :parser parser}
                        #?(:cljs {:send (transit-post "/api")}))))
