(ns hawkthorne.client
  (:require [cljsjs.phaser-ce]
            [hawkthorne.util :as util]))

(enable-console-print!)

(declare game)

(defn preload
  []
  (prn :preload))

(defn create
  []
  (prn :create))

(def game
  (new (.-Game js/Phaser) util/game-width util/game-height
       (.-AUTO js/Phaser) "app"
       (clj->js {:preload preload
                 :create create})))
