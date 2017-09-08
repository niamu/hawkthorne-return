(ns hawkthorne.camera
  (:require [hawkthorne.state :as state]
            [hawkthorne.tiled :as tiled]
            [hawkthorne.util :as util]))

(defn move
  [{:keys [x y width map] :as me}]
  (cond
    ;; player at beginning of level
    (< (+ x (/ width 2)) (/ util/game-width 2))
    (swap! state/state assoc :camera {:x 0 :y 0 :bound? :left})

    ;; player at end of level
    (> (+ x (/ width 2)) (- (tiled/width map) (/ util/game-width 2)))
    (swap! state/state assoc :camera {:x (- (tiled/width map)
                                            util/game-width)
                                      :y 0 :bound? :right})

    ;; player in middle of screen
    :else
    (swap! state/state assoc :camera {:x (- (+ x (/ width 2))
                                            (/ util/game-width 2))
                                      :y 0})))
