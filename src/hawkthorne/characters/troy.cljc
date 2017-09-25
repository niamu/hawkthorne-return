(ns hawkthorne.characters.troy)

(def character
  {:animations {:dropwalk   {:left   [:loop [[8 7] [9 7] [10 7]] 0.16]
                             :right  [:loop [[8 8] [9 8] [10 8]] 0.16]}
                :holdwalk   {:left   [:loop [[2 5] [3 5] [4 5]] 0.16]
                             :right  [:loop [[2 6] [3 6] [4 6]] 0.16]}
                :hurt       {:left   [:once [[4 3] [5 3]] 1]
                             :right  [:once [[4 4] [5 4]] 1]}
                :jump       {:left   [:loop [[1 3] [2 3] [3 3]] 0.16]
                             :right  [:loop [[1 4] [2 4] [3 4]] 0.16]}
                :throwwalk  {:left   [:loop [[2 7] [3 7] [4 7]] 0.16]
                             :right  [:loop [[2 8] [3 8] [4 8]] 0.16]}
                :wieldjump  {:left   [:loop [[1 3] [2 3] [3 3]] 0.16]
                             :right  [:loop [[1 4] [2 4] [3 4]] 0.16]}}
   :costumes
   {:base            {:name "Troy Barnes" :category "base"}
    :ac              {:name "AC Repair" :category "S03E22"}
    :atv             {:name "ATV Gear" :category "S03E19"}
    :bball           {:name "B-Ball" :category "S02E07"}
    :beatitudes      {:name "Beat-itudes" :category "S02E05"}
    :bingbong        {:name "Bing Bong" :category "S02E14"}
    :bumblebee       {:name "Bumblebee" :category "S02E13"}
    :gambino         {:name "Childish Gambino" :category "fanmade"}
    :mafia           {:name "Chicken Mafia" :category "S01E18"}
    :hurt            {:name "Christmas Brawl" :category "S01E12"}
    :christmas_tree  {:name "Christmas Troy" :category "S01E12"}
    :chronicles      {:name "Community College Chronicles" :category "S01E09"}
    :constable       {:name "Constable Reggie" :category "S03E06"}
    :cowboy          {:name "Cowboy" :category "S02E21"}
    :dancer          {:name "Dancer" :category "S01E14"}
    :detective       {:name "Detective" :category "S03E17"}
    :doctor          {:name "Dr. Barnes" :category "S03E16"}
    :eddie           {:name "Eddie Murphy" :category "S01E07"}
    :evil            {:name "Evil Troy" :category "S03E04"}
    :fiddla          {:name "Fiddla Please!" :category "S02E20"}
    :fbf             {:name "Finally Be Fine" :category "S03E01"}
    :baby            {:name "Greendale Baby" :category "S04E01"}
    :popnlock        {:name "Heather Popandlocklear" :category "S02E02"}
    :hippie          {:name "Hippie" :category "S03E10"}
    :hobbes          {:name "Hobbes" :category "S04E02"}
    :invader         {:name "Home Invader" :category "S03E05"}
    :morning         {:name "In the Morning" :category "S01E20"}
    :kick            {:name "Kickpuncher" :category "S01E15"}
    :clubs           {:name "King of Clubs" :category "S02E23"}
    :lotus           {:name "Laser Lotus" :category "S05E04"}
    :letterman       {:name "Letterman Jacket" :category "S01E01"}
    :library         {:name "Library Nerd" :category "S03E17"}
    :michaeljackson  {:name "Michael Jackson" :category "S03E12"}
    :mustache        {:name "Mustache" :category "S04E09"}
    :chloroform      {:name "My Whole Brain is Crying" :category "S02E02"}
    :naked           {:name "Naked" :category "S03E20"}
    :night           {:name "Night Troy" :category "S03E19"}
    :operation       {:name "Operation" :category "S03E02"}
    :orange          {:name "Orange Paint" :category "S02E24"}
    :paintball       {:name "Paintball" :category "S01E23"}
    :pajamas         {:name "Pajamas" :category "S02E09"}
    :pantsuit        {:name "Pant Suit" :category "S02E15"}
    :pharaoh         {:name "Pharaoh" :category "S01E21"}
    :pumpkin         {:name "Pumpkin" :category "S02E19"}
    :ridley          {:name "Ripley" :category "S02E06"}
    :sexyvampire     {:name "Sexy Dracula" :category "S02E06"}
    :spidey          {:name "Spiderman" :category "S02E01"}
    :football        {:name "Star Quarterback" :category "S01E06"}
    :toga            {:name "Toga" :category "S01E22"}
    :sewn            {:name "Troy and Abed Sewn Together" :category "S03E05"}
    :troysoldier     {:name "Troy Soldier" :category "S02E11"}
    :werewolf        {:name "Werewolf" :category "S01E09"}
    :woodsman        {:name "Woodsman" :category "S03E07"}
    :zombie          {:name "Zombie" :category "S02E06"}}
   :bounding-box {:width 14
                  :height 34
                  :crouch-height 20
                  :x 17
                  :y 14}})
