(ns hawkthorne.game
  ;; Phaser convenience functions
  (:require [cljsjs.phaser-ce]
            [hawkthorne.state :as state]
            [hawkthorne.util :as util]
            [tmx2edn.core :refer [tmx->edn]]))

(defn preload-tilesets!
  [game tilesets]
  (doall (map (fn [tileset]
                (.. game -load (image (:name tileset) (:image tileset))))
              tilesets)))

(defn preload-tilemap!
  [game tilemap]
  (.. game -load (binary tilemap tilemap
                         (fn [k data]
                           (let [map-edn (->> (util/arraybuffer->str data)
                                              tmx->edn)]
                             (aset game "stage" "backgroundColor"
                                   (:backgroundcolor map-edn))
                             (preload-tilesets! game (:tilesets map-edn))
                             (.. game -load (tilemap tilemap nil
                                                     (clj->js map-edn)
                                                     (-> js/Phaser .-Tilemap
                                                         .-TILED_JSON)))
                             map-edn)))))

(defn create-tileset-images!
  [game tilemap level]
  (doall (map (fn [tileset]
                (.addTilesetImage level (:name tileset)))
              (:tilesets (.. game -cache (getBinary tilemap))))))

(defn create-layers!
  [game tilemap level]
  (let [map-edn (.. game -cache (getBinary tilemap))]
    (.. game -world (setBounds 0 0
                               (* (:width map-edn) (:tilewidth map-edn))
                               (* (:height map-edn) (:tileheight map-edn))))
    (doall (map (fn [layer]
                  (let [l (.createLayer level (:name layer))]
                    (when-not (true? (:visible layer))
                      (aset l "visible" false))
                    (when (= "collision" (:name layer))
                      (.setCollision level (->> (set (:data layer))
                                                (remove zero?)
                                                clj->js) true l))))
                (filter #(= :tilelayer (:type %))
                        (:layers map-edn))))))

(defn create-tilemap!
  [game tilemap]
  (let [level (-> game .-add (.tilemap tilemap))]
    (create-tileset-images! game tilemap level)
    (create-layers! game tilemap level)))

(defn build-state
  "Pass game object to each state function"
  [init-state game]
  (reduce (fn [accl [k f]]
            (assoc accl k (partial f game)))
          {} init-state))

(defn create-game!
  [width height init-state {:keys [parent]
                            :or [parent (.-body js/document)]
                            :as opts}]
  (let [game (new (.-Game js/Phaser) width height (.-AUTO js/Phaser) parent
                  {} false false)]
    (.. game -state
        (add "Boot" (clj->js (build-state init-state game))
             true))
    (swap! state/state assoc :game game)))
