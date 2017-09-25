(ns hawkthorne.characters.garrett)

(def character
  {:animations {:walk {:left   [:loop [[2 1] [3 1] [4 1]] 0.16]
                       :right  [:loop [[2 2] [3 2] [4 2]] 0.16]}}
   :costumes
   {:base     {:name "Garrett Lambert" :category "base"}
    :jammies  {:name "Camo Jammies" :category "S03E13"}
    :alien    {:name "Creepy Alien" :category "S01E05"}}
   :bounding-box {:width 14
                  :height 43
                  :crouch-height 20
                  :x 17
                  :y 5}})
