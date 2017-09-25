(ns hawkthorne.characters.fatneil)

(def character
  {:animations {:hurt {:left   [:once [[4 3] [5 3]] 1]
                       :right  [:once [[4 4] [5 4]] 1]}}
   :costumes
   {:base      {:name "Fat Neil" :category "base"}
    :coach     {:name "Coach" :category "S04E07"}
    :duquense  {:name "Duquesne" :category "S02E14"}}
   :bounding-box {:width 14
                  :height 37
                  :crouch-height 20
                  :x 17
                  :y 11}})
