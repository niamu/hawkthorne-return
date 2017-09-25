(ns hawkthorne.characters.annie)

(def character
  {:animations {:dropwalk   {:left   [:loop [[8 7] [9 7] [10 7]] 0.16]
                             :right  [:loop [[8 8] [9 8] [10 8]] 0.16]}
                :holdwalk   {:left   [:loop [[2 5] [3 5] [4 5]] 0.16]
                             :right  [:loop [[2 6] [3 6] [4 6]] 0.16]}
                :hurt       {:left   [:once [[4 3] [5 3]] 1]
                             :right  [:once [[4 4] [5 4]] 1]}
                :push       {:left   [:loop [[1 15] [2 15]] 0.16]
                             :right  [:loop [[1 16] [2 16]] 0.16]}
                :throwwalk  {:left   [:loop [[2 7] [3 7] [4 7]] 0.16]
                             :right  [:loop [[2 8] [3 8] [4 8]] 0.16]}}
   :costumes
   {:base          {:name "Annie Edison" :category "base"}
    :abed          {:name "Abed" :category "S03E16"}
    :hearts        {:name "Ace of Hearts" :category "S02E23"}
    :kim           {:name "Annie Kim" :category "S03E02"}
    :armor         {:name "Armor" :category "S03E20"}
    :astronaut     {:name "Astronaut" :category "S02E04"}
    :asylum        {:name "Asylum" :category "S03E19"}
    :ballerannie   {:name "Ballerannie" :category "S02E11"}
    :security      {:name "Campus Security" :category "S01E20"}
    :changlorious  {:name "Changlorious Bastard" :category "S03E21"}
    :cheerleader   {:name "Cheerleader" :category "S01E13"}
    :mafia         {:name "Chicken Mafia" :category "S01E18"}
    :hurt          {:name "Christmas Brawl" :category "S01E12"}
    :paint         {:name "Covered In Paint" :category "S02E24"}
    :debate        {:name "Debate Uniform" :category "S01E09"}
    :dorothy       {:name "Dorothy (Judy Garland)" :category "S03E11"}
    :hanniebal     {:name "Evil Annie (Hanniebal)" :category "S04E10"}
    :reddress      {:name "Evil Annie (Red Dress)" :category "S04E13"}
    :befine        {:name "Finally Be Fine" :category "S03E01"}
    :geneva        {:name "Geneva" :category "S03E16"}
    :german        {:name "German Dirndl" :category "S04E04"}
    :baby          {:name "Greendale Baby" :category "S04E01"}
    :poplock       {:name "Heather Popandlocklear" :category "S02E02"}
    :hector        {:name "Hector the Well-Endowed" :category "S02E14"}
    :hectorlvlup   {:name "Hector the Well-Endowed (Advanced)" :category "S05E10"}
    :honey         {:name "Honey Bunny" :category "S02E19"}
    :hooded        {:name "Hooded Detective Coat" :category "S05E03"}
    :hospital      {:name "Hospital Gown" :category "S01E08"}
    :karatannie    {:name "Karatannie" :category "S02E21"}
    :lawyer        {:name "Lawyer" :category "S03E17"}
    :highschool    {:name "Little Annie Adderall" :category "S04E12"}
    :riding        {:name "Little Red Riding Hood" :category "S02E06"}
    :lingerie      {:name "Magnum's Angel" :category "S03E05"}
    :warfare       {:name "Modern Warfare" :category "S01E23"}
    :nurse         {:name "Nurse" :category "S03E14"}
    :pageant       {:name "Planet Christmas" :category "S03E10"}
    :princess      {:name "Princess Annie" :category "S03E07"}
    :reindeer      {:name "Reindeer Sweater" :category "S03E10"}
    :samara        {:name "Samara (Ring Girl)" :category "S04E02"}
    :script        {:name "Script Supervisor" :category "S03E08"}
    :santa         {:name "Sexy Santa" :category "S03E10"}
    :party         {:name "Sinful Party" :category "S03E05"}
    :skeleton      {:name "Skeleton" :category "S01E07"}
    :tightship     {:name "Tight Ship" :category "S05E11"}
    :timebattle    {:name "Timeline Battle" :category "S04E13"}
    :victorian     {:name "Victorian" :category "S03E05"}
    :werewolf      {:name "Werewolf" :category "S03E05"}
    :western       {:name "Western Dress" :category "S02E23"}
    :wig           {:name "Wigging Out" :category "S03E19"}
    :zombie        {:name "Zombie" :category "S03E20"}}
   :bounding-box {:width 14
                  :height 32
                  :crouch-height 20
                  :x 17
                  :y 16}})
