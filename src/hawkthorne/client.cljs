(ns hawkthorne.client
  (:require [cljsjs.phaser-ce]
            [hawkthorne.atlas :refer [character->atlas]]
            [hawkthorne.game :as game]
            [hawkthorne.util :as util]))

(enable-console-print!)

(declare game)

(defn preload
  [game]
  (.. game -load (atlas :abed
                        "images/characters/abed/base.png"
                        nil
                        (clj->js (character->atlas :abed))))
  (game/preload-tilemap! game "maps/hallway.tmx")
  (prn :preload))

(defn create
  [game]
  (.. game -physics (startSystem (-> js/Phaser .-Physics .-P2JS)))
  (game/create-tilemap! game "maps/hallway.tmx")
  (let [player (.. game -add (sprite 100 0 :abed))
        walk (.. player -animations
                 (add "walk-left"
                      (.. js/Phaser -Animation
                          (generateFrameNames "walk-left-" 0 3))))]
    (.. player -animations (play "walk-left" 10 true))
    (-> game .-physics .-p2 (.enable player))
    (.. game -camera (follow player))
    (aset (-> game .-physics .-p2 .-gravity) "y" 250)
    #_(aset (-> player .-body .-bounce) "y" 0.2)
    #_(aset (-> player .-body) "damping" 1)
    #_(aset (-> player .-body) "collideWorldBounds" true))
  (prn :create))

(aset js/window "onload"
      (fn []
        (prn :onload)
        (game/create-game! util/game-width util/game-height
                           {:preload preload
                            :create create}
                           {:parent "app"})))
