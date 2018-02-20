(ns hawkthorne.tiled
  #?(:clj (:require [tmx2edn.core :as tmx2edn]))
  #?(:cljs (:require-macros
            [hawkthorne.tiled :refer [maps*]])))

#_(defn background-color
    [map-name]
    (if-let [properties (and (get-in (js->clj js/TileMaps)
                                     [map-name "properties" "red"])
                             (get-in (js->clj js/TileMaps)
                                     [map-name "properties"]))]
      [(properties "red")
       (properties "green")
       (properties "blue")]
      [0 0 0]))

(defn collision-index
  [map-data]
  (->> (:layers map-data)
       (map-indexed (fn [idx layer] (when (= (:name layer) "collision") idx)))
       (remove nil?) first))

#?(:clj (defmacro maps* [] (tmx2edn/assets "resources/public/maps/")))

(def maps
  (->> (maps*)
       (reduce (fn [accl [map-name map-data]]
                 (assoc accl map-name
                        (update-in map-data
                                   [:layers (collision-index map-data)]
                                   (fn [layer]
                                     (assoc layer :visible false)))))
               {})))

(defn width
  [map-name]
  (let [{:keys [width tilewidth]} (maps map-name)]
    (* width tilewidth)))

(defn height
  [map-name]
  (let [{:keys [height tileheight]} (maps map-name)]
    (* height tileheight)))

(defn tile-index
  [tiled-map layer-index x y]
  (when-let [ti (get-in tiled-map
                        [:layers layer-index
                         :data (+ x (* y (:width tiled-map)))])]
    (when (pos? ti)
      [(* x (:tilewidth tiled-map)) (* y (:tileheight tiled-map))])))

(defn touching-tile?
  [map-name layer-index x y width height]
  (let [{:keys [tilewidth tileheight] :as tiled-map} (maps map-name)
        start-x (int (/ x tilewidth))
        start-y (int (/ y tileheight))
        end-x (inc (int (/ (+ x width) tilewidth)))
        end-y (int (/ (+ y height) tileheight))
        tiles (for [tile-x (range start-x end-x)
                    tile-y (range end-y start-y -1)]
                (tile-index tiled-map layer-index tile-x tile-y))]
    (first (remove nil? tiles))))

#?(:cljs
   (defn load-maps
     []
     (aset js/window "TileMaps" (clj->js {}))
     (doseq [[map-name map-data] maps]
       (aset js/window "TileMaps" (clj->js map-name) (clj->js map-data)))))
