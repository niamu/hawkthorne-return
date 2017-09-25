(ns hawkthorne.characters.vicki)

(def character
  {:animations {:hurt {:left   [:once [[4 3] [5 3]] 1]
                       :right  [:once [[4 4] [5 4]] 1]}}
   :costumes
   {:base    {:name "Vicki Cooper" :category "base"}
    :knight  {:name "Chess Knight" :category "S03E19"}}
   :bounding-box {:width 14
                  :height 35
                  :crouch-height 20
                  :x 17
                  :y 13}})
