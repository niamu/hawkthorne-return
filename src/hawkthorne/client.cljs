(ns hawkthorne.client
  (:require [cljsjs.phaser-ce]
            [tmx2edn.core :refer [tmx->edn]]
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
  (.. game -load (binary :hallway
                         "maps/hallway.tmx"
                         (fn [k data]
                           (->> data
                                util/arraybuffer->str
                                tmx->edn))))
  (prn :preload))

(defn create
  [game]
  (prn :cache (.. game -cache
                  (getKeys (-> js/Phaser .-Cache .-BINARY))))
  #_(prn :hallway (clj->js (.. game -cache (getBinary :hallway))))
  (let [player (.. game -add (sprite 100 0 :abed))
        walk (.. player -animations
                 (add "walk-left"
                      (.. js/Phaser -Animation
                          (generateFrameNames "walk-left-" 0 3))))]
    (.. player -animations (play "walk-left" 10 true)))
  (prn :create))

(aset js/window "onload"
      (fn []
        (prn :onload)
        (game/create-game! util/game-width util/game-height
                           {:preload preload
                            :create create}
                           {:parent "app"})))
