(ns hawkthorne.characters.buddy)

(def character
  {:animations {:pull  {:left   [:loop [[5 15] [6 15] [7 15] [8 15]] 0.16]
                        :right  [:loop [[5 16] [6 16] [7 16] [8 16]] 0.16]}
                :push  {:left   [:loop [[1 15] [2 15] [3 15] [4 15]] 0.16]
                        :right  [:loop [[1 16] [2 16] [3 16] [4 16]] 0.16]}
                :walk  {:left   [:loop [[2 1] [3 1] [4 1] [5 1]] 0.16]
                        :right  [:loop [[2 2] [3 2] [4 2] [5 2]] 0.16]}}
   :costumes
   {:base             {:name "Buddy" :category "base"}
    :master_exploder  {:name "Master Exploder" :category "fanmade"}}
   :bounding-box {:width 14
                  :height 36
                  :crouch-height 20
                  :x 17
                  :y 12}})
