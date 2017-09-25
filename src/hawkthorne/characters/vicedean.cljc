(ns hawkthorne.characters.vicedean)

(def character
  {:animations {:dropwalk   {:left   [:loop [[8 7] [9 7] [10 7]] 0.16]
                             :right  [:loop [[8 8] [9 8] [10 8]] 0.16]}
                :holdwalk   {:left   [:loop [[2 5] [3 5] [4 5]] 0.16]
                             :right  [:loop [[2 6] [3 6] [4 6]] 0.16]}
                :throwwalk  {:left   [:loop [[2 7] [3 7] [4 7]] 0.16]
                             :right  [:loop [[2 8] [3 8] [4 8]] 0.16]}}
   :costumes
   {:base     {:name "Vice Dean Laybourne" :category "base"}
    :ghost    {:name "Ghost" :category "S03E22"}
    :stuff    {:name "Going Through Some Stuff" :category "S03E13"}
    :pajamas  {:name "Pajamas" :category "S03E13"}}
   :bounding-box {:width 14
                  :height 44
                  :crouch-height 20
                  :x 17
                  :y 4}})
