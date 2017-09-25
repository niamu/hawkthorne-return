(ns hawkthorne.characters.gilbert)

(def character
  {:animations {:jump {:left   [:loop [[1 3] [2 3] [3 3]] 0.16]
                       :right  [:loop [[1 4] [2 4] [3 4]] 0.16]}}
   :costumes
   {:base {:name "Gilbert Lawson" :category "base"}}
   :bounding-box {:width 14
                  :height 38
                  :crouch-height 20
                  :x 17
                  :y 10}})
