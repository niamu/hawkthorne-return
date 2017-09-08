(ns hawkthorne.client
  (:require [hawkthorne.websocket :as websocket]
            [hawkthorne.tiled :as tiled]
            [hawkthorne.player :as player]
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
      #_(websocket/send :keys (p/get-pressed-keys game))
      #_(events/listen js/window "keydown"
                       (fn [event]
                         (when (= (.-keyCode event) 37)
                           (websocket/send :move :left)))))
    (on-hide [this])
    (on-render [this]
      (p/render game
                [[:tiled-map {:name "hallway" :x 0}]
                 (mapv #(player/player (merge player/init-state %))
                       (vals (:players @state/state)))
                 #_[:fill {:color "black"}
                    [:text {:value (pr-str @state/state)
                            :x 20
                            :y 30
                            :size 14 :font "Georgia" :style :italic}]]])
      (websocket/send :keys (p/get-pressed-keys game)))))

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
