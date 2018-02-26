(ns hawkthorne.atlas
  (:require [hawkthorne.characters :refer [characters]]))

(def animations
  {:acquire          {:left   [:once [[7 3]] 1]
                      :right  [:once [[7 4]] 1]}
   :attack           {:left   [:once [[1 9] [5 9]] 0.1]
                      :right  [:once [[1 10] [5 10]] 0.1]}
   :attackjump       {:left   [:once [[4 9] [8 9]] 0.1]
                      :right  [:once [[4 10] [8 10]] 0.1]}
   :attackwalk       {:left   [:once [[2 9] [7 9]] 0.1]
                      :right  [:once [[2 10] [7 10]] 0.1]}
   :crawlcrouchwalk           [:loop [[5 13] [6 13] [7 13] [8 13]] 0.16]
   :crawlgazewalk             [:loop [[5 14] [6 14] [7 14] [8 14]] 0.16]
   :crawlidle        {:left   [:once [[1 13]] 1]
                      :right  [:once [[1 14]] 1]}
   :crawlwalk        {:left   [:loop [[1 13] [2 13] [3 13] [4 13]] 0.16]
                      :right  [:loop [[1 14] [2 14] [3 14] [4 14]] 0.16]}
   :crouch           {:left   [:once [[10 5]] 1]
                      :right  [:once [[10 6]] 1]}
   :crouchhold                [:once [[7 5]] 1]
   :crouchholdwalk            [:loop [[8 5] [9 5]] 0.16]
   :crouchwalk                [:loop [[9 1] [10 1]] 0.16]
   :dance                     [:loop [[4 9] [12 7] [12 8] [4 10] [8 6]] 0.2]
   :dead             {:left   [:once [[6 3]] 1]
                      :right  [:once [[6 4]] 1]}
   :depressed        {:left   [:once [[7 1]] 1]
                      :right  [:once [[7 2]] 1]}
   :dig              {:left   [:loop [[9 13] [10 13]] 0.16]
                      :right  [:loop [[9 14] [9 14]] 0.16]}
   :digidle          {:left   [:once [[9 13]] 1]
                      :right  [:once [[9 14]] 1]}
   :digthrow         {:left   [:loop [[11 13]] 0.16]
                      :right  [:loop [[11 14]] 0.16]}
   :drop             {:left   [:once [[7 7]] 1]
                      :right  [:once [[7 8]] 1]}
   :dropjump         {:left   [:once [[12 7]] 1]
                      :right  [:once [[12 8]] 1]}
   :dropwalk         {:left   [:loop [[8 7] [9 7]] 0.16]
                      :right  [:loop [[8 8] [9 8]] 0.16]}
   :flyin                     [:once [[12 5]] 1]
   :gaze             {:left   [:once [[6 1]] 1]
                      :right  [:once [[6 2]] 1]}
   :gazehold                  [:once [[7 6]] 1]
   :gazeholdwalk              [:loop [[8 6] [9 6]] 0.16]
   :gazeidle                  [:once [[8 2]] 1]
   :gazewalk                  [:loop [[9 2] [10 2]] 0.16]
   :hold             {:left   [:once [[1 5]] 1]
                      :right  [:once [[1 6]] 1]}
   :holdjump         {:left   [:once [[6 5]] 1]
                      :right  [:once [[6 6]] 1]}
   :holdwalk         {:left   [:loop [[2 5] [3 5]] 0.16]
                      :right  [:loop [[2 6] [3 6]] 0.16]}
   :hurt             {:left   [:once [[4 3]] 1]
                      :right  [:once [[4 4]] 1]}
   :idle             {:left   [:once [[1 1]] 1]
                      :right  [:once [[1 2]] 1]}
   :interact         {:left   [:once [[11 3] [12 3]] 0.16]
                      :right  [:once [[11 4] [12 4]] 0.16]}
   :jump             {:left   [:once [[1 3]] 1]
                      :right  [:once [[1 4]] 1]}
   :kick             {:left   [:once [[9 3]] 1]
                      :right  [:once [[9 4]] 1]}
   :kneel            {:left   [:once [[11 5]] 1]
                      :right  [:once [[11 6]] 1]}
   :profile          {:left   [:once [[11 1]] 1]
                      :right  [:once [[11 2]] 1]}
   :profileaway      {:left   [:once [[12 1]] 1]
                      :right  [:once [[12 2]] 1]}
   :pull             {:left   [:loop [[5 15] [6 15] [7 15] [6 15]] 0.16]
                      :right  [:loop [[5 16] [6 16] [7 16] [6 16]] 0.16]}
   :punch            {:left   [:once [[10 3]] 1]
                      :right  [:once [[10 4]] 1]}
   :push             {:left   [:loop [[1 15] [2 15] [3 15] [2 15]] 0.16]
                      :right  [:loop [[1 16] [2 16] [3 16] [2 16]] 0.16]}
   :shootarrow       {:left   [:loop [[1 11] [2 11] [3 11] [4 11]] 0.12]
                      :right  [:loop [[1 12] [2 12] [3 12] [4 12]] 0.12]}
   :shootarrowwalk   {:left   [:loop [[5 11] [6 11] [7 11] [8 11]] 0.12]
                      :right  [:loop [[5 12] [6 12] [7 12] [8 12]] 0.12]}
   :shootarrowjump   {:left   [:loop [[9 11] [10 11] [11 11] [12 11]] 0.12]
                      :right  [:loop [[9 12] [10 12] [11 12] [12 12]] 0.12]}
   :laserpistol      {:left   [:loop [[9 9]] 0.12]
                      :right  [:loop [[9 10]] 0.12]}
   :laserpistolwalk  {:left   [:loop [[10 9] [11 9]] 0.12]
                      :right  [:loop [[10 10] [11 10]] 0.12]}
   :laserpistoljump  {:left   [:loop [[12 9]] 0.12]
                      :right  [:loop [[12 10]] 0.12]}
   :slide            {:left   [:once [[8 3]] 1]
                      :right  [:once [[8 4]] 1]}
   :throw            {:left   [:once [[1 7]] 1]
                      :right  [:once [[1 8]] 1]}
   :throwjump        {:left   [:once [[6 7]] 1]
                      :right  [:once [[6 8]] 1]}
   :throwwalk        {:left   [:loop [[2 7] [3 7]] 0.16]
                      :right  [:loop [[2 8] [3 8]] 0.16]}
   :towards                   [:once [[8 1]] 1]
   :walk             {:left   [:loop [[2 1] [3 1] [4 1] [3 1]] 0.16]
                      :right  [:loop [[2 2] [3 2] [4 2] [3 2]] 0.16]}
   :warp                      [:once [[1 1] [2 1] [3 1] [4 1]] 0.08]
   :wieldaction      {:left   [:once [[2 9] [10 9] [6 9] [2 9]] 0.11]
                      :right  [:once [[2 10] [10 10] [6 10] [2 10]] 0.11]}
   :wieldaction2     {:left   [:once [[2 9] [10 9] [6 9] [2 9]] 0.18]
                      :right  [:once [[2 10] [10 10] [6 10] [2 10]] 0.18]}
   :wieldaction3     {:left   [:once [[2 9] [10 9] [6 9] [2 9]] 0.2]
                      :right  [:once [[2 10] [10 10] [6 10] [2 10]] 0.2]}
   :wieldaction4     {:left   [:once [[2 9] [10 9] [6 9] [2 9]] 0.15]
                      :right  [:once [[2 10] [10 10] [6 10] [2 10]] 0.15]}
   :wieldaction5     {:left   [:once [[2 9] [10 9] [6 9] [2 9]] 0.07]
                      :right  [:once [[2 10] [10 10] [6 10] [2 10]] 0.07]}
   :wieldidle        {:left   [:once [[9 9]] 1]
                      :right  [:once [[9 10]] 1]}
   :wieldjump        {:left   [:once [[4 9]] 1]
                      :right  [:once [[4 10]] 1]}
   :rest             {:left   [:once [[4 13]] 1]
                      :right  [:once [[4 14]] 1]}
   :wieldwalk        {:left   [:loop [[2 9] [3 9]] 0.16]
                      :right  [:loop [[2 10] [3 10]] 0.16]}})

(defn frame
  [idx filename suffix data x-coord y-coord]
  (let [size 48]
    {:filename (str (name filename) suffix "-" idx)
     :frame {:x (* (dec x-coord) size)
             :y (* (dec y-coord) size)
             :w size
             :h size}
     :rotated false
     :trimmed false
     :spriteSourceSize {:x 0 :y 0
                        :w size :h size}
     :sourceSize {:w size :h size}}))

(defn frames
  [anims]
  (reduce (fn [accl [filename data]]
            (if (:left data)
              (apply conj accl
                     (concat
                      (map-indexed (fn [idx [x-coord y-coord]]
                                     (frame idx filename "-left"
                                            (:left data) x-coord y-coord))
                                   (nth (:left data) 1))
                      (map-indexed (fn [idx [x-coord y-coord]]
                                     (frame idx filename "-right"
                                            (:right data) x-coord y-coord))
                                   (nth (:right data) 1))))
              (apply conj accl
                     (map-indexed (fn [idx [x-coord y-coord]]
                                    (frame idx filename ""
                                           data x-coord y-coord))
                                  (nth data 1)))))
          [] anims))

(defn character->atlas
  [character]
  {:frames (frames (merge animations
                          (get-in characters
                                  [character :animations])))
   :meta {:app "hawkthorne-return",
          :version "1.0"
          :image (str (name character) ".png")
          :format "RGBA8888"
          :size {:w 576 :h 768}
          :scale 1}})
