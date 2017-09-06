(ns hawkthorne.tiled
  #?(:clj (:require [tmx2edn.core :as tmx2edn]))
  #?(:cljs (:require-macros
            [hawkthorne.tiled :refer [maps*]])))

#?(:clj (defmacro maps* [] (tmx2edn/assets "resources/public/maps/")))

(def maps (maps*))

;; TODO: Function that calculates collision layer index
#_(->> (maps "hallway")
       :layers
       (map-indexed (fn [idx layer]
                      (when (= (:name layer) "collision")
                        idx)))
       (remove nil?)
       first)

#?(:cljs (aset js/window "TileMaps" (clj->js {})))

#?(:cljs (doseq [[map-name map-data] maps]
           (aset js/window "TileMaps" (clj->js map-name) (clj->js map-data))))
