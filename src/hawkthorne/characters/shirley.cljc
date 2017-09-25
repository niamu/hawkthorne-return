(ns hawkthorne.characters.shirley)

(def character
  {:animations {:jump {:left   [:loop [[1 3] [2 3]] 0.16]
                       :right  [:loop [[1 4] [2 4]] 0.16]}}
   :costumes
   {:base             {:name "Shirley Bennett" :category "base"}
    :clubs            {:name "Ace of Clubs" :category "S02E23"}
    :angel            {:name "Angel" :category "S03E05"}
    :space            {:name "Astronaut" :category "S02E04"}
    :babydoll         {:name "Baby Doll" :category "S02E11"}
    :anime            {:name "Big Cheddar" :category "S03E09"}
    :book             {:name "Book" :category "S03E08"}
    :security         {:name "Campus Security" :category "S01E20"}
    :captain          {:name "Captain" :category "S01E19"}
    :chef             {:name "Chef" :category "S03E21"}
    :hurt             {:name "Christmas Brawl" :category "S01E12"}
    :crayon           {:name "Crayon" :category "S02E13"}
    :dark             {:name "Darkest Timeline" :category "S03E04"}
    :fbf              {:name "Finally Be Fine" :category "S03E01"}
    :baby             {:name "Greendale Baby" :category "S04E01"}
    :potter           {:name "Harry Potter" :category "S01E07"}
    :headnurse        {:name "Head Nurse Bennett" :category "S02E02"}
    :poplock          {:name "Heather Popandlocklear" :category "S02E02"}
    :jules            {:name "Jules Winnfield" :category "S02E19"}
    :lingerie         {:name "Magnum's Angel" :category "S03E05"}
    :glinda           {:name "Not Miss Piggy (Glinda)" :category "S02E06"}
    :oprah            {:name "Oprah" :category "S03E12"}
    :planetchristmas  {:name "Planet Christmas" :category "S03E10"}
    :leia             {:name "Princess Leia" :category "S04E02"}
    :sandwiches       {:name "Shirley's Sandwiches" :category "S04E04"}
    :timelinebattle   {:name "Timeline Battle" :category "S04E13"}
    :zippy            {:name "Zip-a-Dee-Doo" :category "S02E14"}
    :zombie           {:name "Zombie" :category "S02E06"}}
   :bounding-box {:width 14
                  :height 30
                  :crouch-height 20
                  :x 17
                  :y 18}})
