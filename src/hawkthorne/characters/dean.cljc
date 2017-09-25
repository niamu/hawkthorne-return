(ns hawkthorne.characters.dean)

(def character
  {:animations {:dropwalk   {:left   [:loop [[8 7] [9 7] [10 7]] 0.16]
                             :right  [:loop [[8 8] [9 8] [10 8]] 0.16]}
                :holdwalk   {:left   [:loop [[2 5] [3 5] [4 5]] 0.16]
                             :right  [:loop [[2 6] [3 6] [4 6]] 0.16]}
                :throwwalk  {:left   [:loop [[2 7] [3 7] [4 7]] 0.16]
                             :right  [:loop [[2 8] [3 8] [4 8]] 0.16]}}
   :costumes
   {:base          {:name "Dean Craig Pelton" :category "base"}
    :bee           {:name "Bumblebee" :category "S02E12"}
    :construction  {:name "Construction" :category "S03E22"}
    :devil         {:name "Devil Dean" :category "S03E05"}
    :donna         {:name "Donna Reed" :category "S04E08"}
    :cowboy        {:name "How-Dean Pilgrims!" :category "S04E05"}
    :jgdiehard     {:name "Joseph Gordon Diehard" :category "S05E10"}
    :mardigras     {:name "Mardi Gras" :category "S02E21"}
    :newdean       {:name "New Dean" :category "S03E01"}
    :conductor     {:name "Train Conductor" :category "S03E15"}
    :unclesam      {:name "Uncle Sam" :category "S02E17"}}
   :bounding-box {:width 14
                  :height 34
                  :crouch-height 20
                  :x 17
                  :y 14}})
