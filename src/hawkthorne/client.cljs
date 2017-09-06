(ns hawkthorne.client
  (:require [hawkthorne.websocket :as websocket]
            [hawkthorne.tiled :as tiled]
            [hawkthorne.router :as router]
            [hawkthorne.state :as state]
            [play-cljs.core :as p]
            [goog.events :as events]
            [goog.dom :as dom]))

(enable-console-print!)

(defonce game (p/create-game 576 324 "game"))

(def main-screen
  (reify p/Screen
    (on-show [this]
      (p/load-tiled-map game "hallway")
      (events/listen js/window "keydown"
                     (fn [event]
                       (when (= (.-keyCode event) 37)
                         (websocket/send :move :left)))))
    (on-hide [this])
    (on-render [this]
      #_(prn (p/get-pressed-keys game))
      (p/render game
                [#_[:stroke {}
                    [:fill {:color "lightblue"}
                     [:rect {:x 0 :y 0
                             :width (.-innerWidth js/window)
                             :height (.-innerHeight js/window)}]]]
                 [:tiled-map {:name "hallway" :x 0}]
                 [:fill {:color "black"}
                  [:text {:value (pr-str @state/state)
                          :x 20
                          :y 30
                          :size 16 :font "Georgia" :style :italic}]]]))))

(defn mount-route
  "Mount the React DOM Root corresponding with the current path"
  [path]
  (-> path
      router/path->name
      router/route->response))

(mount-route (.. js/window -location -pathname))

(doto game
  (p/start)
  (p/set-screen main-screen))
