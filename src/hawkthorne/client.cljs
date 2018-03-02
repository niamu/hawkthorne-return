(ns hawkthorne.client
  (:require [cljsjs.phaser-ce]
            [hawkthorne.atlas :refer [character->atlas]]
            [hawkthorne.game :as game]
            [hawkthorne.util :as util]))

(enable-console-print!)

(defn player-update
  [game player cursors collision-layer]
  (-> game .-physics .-arcade
      (.collide collision-layer player
                (fn [c o])))
  (aset (-> player .-body .-velocity) "x" 0)
  (when (and (.-isDown (-> game .-input .-keyboard
                           (.addKey (-> js/Phaser .-KeyCode .-SPACEBAR))))
             (.. player -body (onFloor)))
    (aset (-> player .-body .-velocity) "y" -300))
  (when (-> cursors .-right .-isDown)
    (aset (-> player .-body .-velocity) "x" 350)
    (.. player -animations (play "walk-right" 10 true)))
  (when (-> cursors .-left .-isDown)
    (aset (-> player .-body .-velocity) "x" -350)
    (.. player -animations (play "walk-left" 10 true)))
  (when (and (-> cursors .-left .-isUp)
             (-> cursors .-right .-isUp))
    (.. player -animations (play "idle-right"))))

(defn preload
  [game]
  (aset game "time" "advancedTiming" true)
  (.. game -load (atlas :abed
                        "images/characters/abed/base.png"
                        nil (clj->js (character->atlas :abed))))
  (game/preload-tilemap! game "maps/hallway.tmx")
  (prn :preload))

(defn create
  [game]
  (.. game -physics (startSystem (-> js/Phaser .-Physics .-ARCADE)))
  (aset game "stage" "smoothed" false)
  (game/create-tilemap! game "maps/hallway.tmx")
  (let [player (.. game -add (sprite 0 0 :abed))
        collision-layer (first (filter (fn [o]
                                         (= "collision"
                                            (aget o "layer" "name")))
                                       (-> game .-world .-children)))]
    (aset player "update"
          (partial #'player-update game player
                   (-> game .-input .-keyboard (.createCursorKeys))
                   collision-layer))
    (.. player -animations
        (add "idle-right"
             (.. js/Phaser -Animation
                 (generateFrameNames "idle-right-" 0 0))))
    (.. player -animations
        (add "walk-left"
             (.. js/Phaser -Animation
                 (generateFrameNames "walk-left-" 0 3))))
    (.. player -animations
        (add "walk-right"
             (.. js/Phaser -Animation
                 (generateFrameNames "walk-right-" 0 3))))
    (.. game -physics (enable player))
    (.. game -camera (follow player))
    (aset (-> game .-physics .-arcade .-gravity) "y" 500)
    (aset (-> player .-body .-bounce) "y" 0.2)
    (aset (-> player .-body) "damping" 1)
    (aset (-> player .-body) "collideWorldBounds" true))
  (prn :create))

(defn update-loop
  [game]
  )

(defn render
  [game]
  (.. game -debug (text (or (str "FPS: " (-> game .-time .-fps))
                            "FPS: --") 40 40 "#ff0000")))

(aset js/window "onload"
      (fn []
        (prn :onload)
        (game/create-game! util/game-width util/game-height
                           {:preload #'preload
                            :create #'create
                            :update #'update-loop
                            :render #'render}
                           {:parent "app"})))
