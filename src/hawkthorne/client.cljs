(ns hawkthorne.client
  (:require [cljsjs.phaser-ce]
            [hawkthorne.atlas :refer [character->atlas]]
            [hawkthorne.util :as util]))

(enable-console-print!)

(declare game)

(defn preload
  []
  (.. game -load (atlas "abed"
                        "images/characters/abed/base.png"
                        nil
                        (clj->js (character->atlas :abed))))
  (prn :preload))

(defn create
  []
  (let [player (.. game -add (sprite 0 0 "abed"))
        walk (.. player -animations
                 (add "walk-left"
                      (.. js/Phaser -Animation
                          (generateFrameNames "walk-left-" 0 3))))]
    (.. player -animations (play "walk-left" 10 true)))
  (prn :create))

(defonce game
  (new (.-Game js/Phaser) util/game-width util/game-height
       (.-AUTO js/Phaser) "app"
       (clj->js {:preload preload
                 :create create})))
