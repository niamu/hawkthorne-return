(ns hawkthorne.client
  (:require [cljsjs.phaser-ce]
            [hawkthorne.atlas :refer [character->atlas]]
            [hawkthorne.player :as player]
            [hawkthorne.game :as game]
            [hawkthorne.util :as util]))

(enable-console-print!)

(defn preload
  [game]
  (aset game "time" "advancedTiming" true)
  (.. game -load (atlas :abed
                        "images/characters/abed/base.png"
                        nil (clj->js (character->atlas :abed))))
  (game/preload-tilemap! game "maps/forest.tmx")
  (prn :preload))

(defn create
  [game]
  (.. game -physics (startSystem (-> js/Phaser .-Physics .-ARCADE)))
  (aset game "stage" "smoothed" false)
  (game/create-tilemap! game "maps/forest.tmx")
  (let [player (.. game -add (sprite 0 0 :abed))
        collision-layer (first (filter (fn [o]
                                         (= "collision"
                                            (aget o "layer" "name")))
                                       (-> game .-world .-children)))]
    (aset player "facing" "right")
    (aset player "update"
          (partial #'player/update! game player
                   (-> game .-input .-keyboard (.createCursorKeys))
                   collision-layer))
    (player/add-animations! player)
    (.. game -physics (enable player))
    (.. game -camera (follow player))
    (aset (-> game .-physics .-arcade .-gravity) "y" 500)
    (aset (-> player .-body .-bounce) "y" 0.2)
    (aset (-> player .-body) "damping" 1)
    #_(aset (-> player .-body) "collideWorldBounds" true))
  (prn :create))

(defn update-loop
  [game]
  )

(defn render
  [game]
  (.. game -debug (text (str "FPS: " (or (-> game .-time .-fps) "--"))
                        40 40 "#ff0000")))

(aset js/window "onload"
      (fn []
        (prn :onload)
        (game/create-game! util/game-width util/game-height
                           {:preload #'preload
                            :create #'create
                            :update #'update-loop
                            :render #'render}
                           {:parent "app"})))
