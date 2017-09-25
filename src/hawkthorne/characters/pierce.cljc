(ns hawkthorne.characters.pierce)

(def character
  {:animations {:dropwalk   {:left   [:loop [[8 7] [9 7] [10 7] [11 7]] 0.16]
                             :right  [:loop [[8 8] [9 8] [10 8] [11 8]] 0.16]}
                :holdwalk   {:left   [:loop [[2 5] [3 5] [4 5] [5 5]] 0.16]
                             :right  [:loop [[2 6] [3 6] [4 6] [5 6]] 0.16]}
                :pull       {:left   [:loop [[5 15] [6 15] [7 15] [8 15]] 0.16]
                             :right  [:loop [[5 16] [6 16] [7 16] [8 16]] 0.16]}
                :push       {:left   [:loop [[1 15] [2 15] [3 15] [4 15]] 0.16]
                             :right  [:loop [[1 16] [2 16] [3 16] [4 16]] 0.16]}
                :throwwalk  {:left   [:loop [[2 7] [3 7] [4 7] [5 7]] 0.16]
                             :right  [:loop [[2 8] [3 8] [4 8] [5 8]] 0.16]}
                :walk       {:left   [:loop [[2 1] [3 1] [4 1] [5 1]] 0.16]
                             :right  [:loop [[2 2] [3 2] [4 2] [5 2]] 0.16]}}
   :costumes
   {:base              {:name "Pierce Hawthorne" :category "base"}
    :astronaut         {:name "Astronaut" :category "S02E04"}
    :beastmaster       {:name "Beastmaster" :category "S01E07"}
    :naked             {:name "Birthday Suit" :category "S03E20"}
    :canoe             {:name "Canoe" :category "S01E19"}
    :kirk              {:name "Captain Kirk" :category "S02E06"}
    :captain           {:name "Captain Pierce" :category "S01E19"}
    :hurt              {:name "Christmas Brawl" :category "S01E12"}
    :wizard            {:name "Cookie Crisp Wizard" :category "S03E20"}
    :drugs             {:name "Drugs" :category "S02E13"}
    :fatreynolds       {:name "Fat Burt Reynolds" :category "S03E12"}
    :feethands         {:name "Feet Hands" :category "S03E05"}
    :gimp              {:name "The Gimp" :category "S02E19"}
    :popnlock          {:name "Heather Popandlocklear" :category "S02E02"}
    :hotdog            {:name "Hotdog" :category "S02E21"}
    :hulapaint         {:name "Hula Paint Hallucination" :category "S03E07"}
    :janetreno         {:name "Janet Reno" :category "S01E16"}
    :lotus             {:name "Level 5 Laser Lotus" :category "S02E03"}
    :madscientist      {:name "Mad Scientist" :category "S03E05"}
    :magnum            {:name "Magnum" :category "S03E05"}
    :paintball         {:name "Paintball Trooper" :category "S02E24"}
    :dickish           {:name "Pierce the Dickish" :category "S02E14"}
    :pillow            {:name "Pillow Man" :category "S03E14"}
    :planet_christmas  {:name "Planet Christmas" :category "S03E10"}
    :teddy             {:name "Teddy Pierce" :category "S02E11"}
    :western           {:name "Western" :category "S02E23"}
    :wheelchair        {:name "Wheelchair" :category "S02E09"}
    :zombie            {:name "Zombie" :category "S02E06"}}
   :bounding-box {:width 14
                  :height 42
                  :crouch-height 20
                  :x 17
                  :y 6}})
