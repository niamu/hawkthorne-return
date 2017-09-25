(ns hawkthorne.characters.chang)

(def character
  {:animations {:dropwalk   {:left   [:loop [[8 7] [9 7] [10 7]] 0.16]
                             :right  [:loop [[8 8] [9 8] [10 8]] 0.16]}
                :holdwalk   {:left   [:loop [[2 5] [3 5] [4 5]] 0.16]
                             :right  [:loop [[2 6] [3 6] [4 6]] 0.16]}
                :throwwalk  {:left   [:loop [[2 7] [3 7] [4 7]] 0.16]
                             :right  [:loop [[2 8] [3 8] [4 8]] 0.16]}}
   :costumes
   {:base            {:name "Ben Chang" :category "base"}
    :brutalitops     {:name "Brutalitops" :category "S02E14"}
    :butch           {:name "Butch Coolidge" :category "S02E19"}
    :cheerleader     {:name "Cheerleader" :category "S02E23"}
    :dictator        {:name "Dictator" :category "S03E21"}
    :dingleberry     {:name "Dingleberry The Troll" :category "S05E10"}
    :evil            {:name "Evil Chang" :category "S04E00"}
    :father          {:name "Father" :category "S02E18"}
    :popnlock        {:name "Heather Popandlocklear" :category "S02E02"}
    :cannotdie       {:name "I Cannot Die" :category "S01E13"}
    :naked           {:name "Kevin" :category "S04E01"}
    :leather         {:name "Leather Jacket" :category "S01E16"}
    :peggy           {:name "Peggy Fleming" :category "S02E06"}
    :ymca            {:name "Robbed at the YMCA" :category "S01E19"}
    :safety          {:name "Safety First" :category "S01E24"}
    :shaving         {:name "Shaving Cream" :category "S04E04"}
    :soccer          {:name "Soccer Fan" :category "S02E15"}
    :snowman         {:name "Snowman" :category "S02E11"}
    :enterchangment  {:name "That's Enter-chang-ment" :category "S03E14"}
    :understudy      {:name "Understudy" :category "S03E08"}
    :zombie          {:name "Zombie" :category "S02E06"}}
   :bounding-box {:width 14
                  :height 34
                  :crouch-height 20
                  :x 17
                  :y 14}})
