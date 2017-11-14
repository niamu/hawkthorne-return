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

(def stylesheet
  [[:body
    {:margin 0
     :background "#000"}]
   [:#game
    [:canvas
     {:position :absolute
      :width (units/percent 100)
      :height (units/percent 100)
      :object-fit :contain}]]
   [:#app
    {:position :relative
     :display :flex
     :flex-direction :column
     :height (units/vh 100)
     :justify-content :center}
    [:div.hud
     {:height (calc [(units/vw 100) "/"
                     (/ util/game-width util/game-height)])}]]])

(defn render [] (garden/css stylesheet))
