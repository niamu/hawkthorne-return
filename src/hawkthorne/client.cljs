(ns hawkthorne.client
  (:require [hawkthorne.camera :as camera]
            [hawkthorne.player :as player]
            [hawkthorne.router :as router]
            [hawkthorne.state :as state]
            [hawkthorne.tiled :as tiled]
            [hawkthorne.util :as util]
            [hawkthorne.websocket :as websocket]
            [play-cljs.core :as p]))

(enable-console-print!)

(defonce game (p/create-game util/game-width util/game-height "game"))

(def main-screen
  (reify p/Screen
    (on-show [this])
    (on-hide [this])
    (on-render [this]
      (when-let [{:keys [players me camera]}
                 (and (not-empty (:players @state/state)) @state/state)]
        (p/render game
                  [[:tiled-map
                    {:name (:map (players me))
                     :x (:x camera)
                     :y (:y camera)}]
                   (mapv (fn [[id p]]
                           (player/draw (= id me) camera p))
                         players)])
        (camera/move (players me)))
      (swap! state/state assoc :keys (p/get-pressed-keys game)))))

(router/mount-route (.. js/window -location -pathname))

(tiled/load-maps)

(websocket/tick-start)

(doto game
  (p/start)
  (p/set-screen main-screen))
