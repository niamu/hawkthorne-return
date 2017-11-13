(ns hawkthorne.game
  (:require [hawkthorne.camera :as camera]
            [hawkthorne.player :as player]
            [hawkthorne.state :as state]
            [hawkthorne.util :as util]
            [play-cljs.core :as p]))

(defonce game (p/create-game util/game-width util/game-height "game"))

(def main-screen
  (reify p/Screen
    (on-show [this]
      ;; No smoothing when scaling up images for the pixelated aesthetic
      (doto (.getContext (p/get-canvas game) "2d")
        (aset "imageSmoothingEnabled" false)
        (aset "webkitImageSmoothingEnabled" false)
        (aset "mozImageSmoothingEnabled" false)))
    (on-hide [this])
    (on-render [this]
      (when-let [{:keys [debugging? players me camera]}
                 (and (not-empty (:players @state/state)) @state/state)]
        (p/render game
                  [[:tiled-map {:name (:map (players me))
                                :x (:x camera)
                                :y (:y camera)}]
                   (mapv (fn [[i p]] (player/draw (= i me) camera p debugging?))
                         players)])
        (camera/move (players me)))
      (swap! state/state assoc :fps (/ 1000 (p/get-delta-time game)))
      (swap! state/state assoc :keys (p/get-pressed-keys game)))))

(defn start
  []
  (prn @state/state)
  (doto game
    (p/start)
    (p/set-screen main-screen)))
