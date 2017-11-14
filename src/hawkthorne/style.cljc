(ns hawkthorne.style
  "Utilities to generate CSS"
  (:require [hawkthorne.util :as util]
            [garden.color :as color]
            [garden.core :as garden]
            [garden.stylesheet :as stylesheet]
            [garden.selectors :as selectors]
            [garden.units :as units]
            [clojure.string :as string])
  #?(:cljs (:require-macros
            [hawkthorne.style :refer [defbreakpoint]])))

#?(:clj
   (defmacro defbreakpoint
     [name media-params]
     `(defn ~name [& rules#]
        (stylesheet/at-media ~media-params [:& rules#]))))

(defbreakpoint non-mobile-screen
  {:screen true
   :min-width (units/px 600)})

(def calc (stylesheet/cssfn :calc))
(def scale (stylesheet/cssfn :scale))
(def saturate (stylesheet/cssfn :saturate))

(def stylesheet
  [[:html :body
    {:height (units/percent 100)}]
   [:body
    {:margin 0}]
   [:#game
    {:height (units/vh 100)
     :background "#000"}
    [:canvas
     {:position :absolute
      :width (units/percent 100)
      :height (units/percent 100)
      :object-fit :contain}]]
   [:div.heroes
    {:display :flex
     :flex-wrap :wrap
     :justify-content :center}
    (let [s 2.5]
      [:.hero-container
       {:width (units/px (* 48 s))
        :height (units/px (* 48 s))}
       [:input {:display :none}]
       [:div.hero
        {:transition [[(units/s 0.15) :linear :all]]
         :transform (scale 1)}
        [:label.image
         {:display :block
          :cursor :pointer
          :opacity 0.7
          :width (units/px 48)
          :height (units/px 48)
          :transform (scale s)
          :transform-origin [[:top :left]]
          :image-rendering :pixelated}]]
       [(selectors/+ (selectors/input (selectors/attr :disabled))
                     (selectors/> :div.hero :.image))
        {:filter (saturate 0)}]
       [(selectors/+ (-> (selectors/not (selectors/attr :disabled))
                         selectors/input)
                     (selectors/> :div.hero:hover :.image))
        (selectors/+ (selectors/input (selectors/checked))
                     (selectors/> :div.hero :.image))
        {:opacity 1}]
       [(selectors/+ (-> (selectors/not (selectors/attr :disabled))
                         selectors/input)
                     :div.hero:hover)
        (selectors/+ (selectors/input (selectors/checked)) :div.hero)
        {:transform (scale 1.1)}]])]
   [:div#overlay
    {:position :absolute
     :top 0
     :display :flex
     :flex-direction :column
     :width (units/percent 100)
     :height (units/vh 100)
     :justify-content :center}
    [:div#hud
     {:height (calc [(units/vw 100) "/"
                     (/ util/game-width util/game-height)])}]]])

(defn render [] (garden/css stylesheet))
