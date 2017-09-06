(ns hawkthorne.style
  "Utilities to generate CSS"
  (:require [garden.color :as color]
            [garden.core :as garden]
            [garden.stylesheet :as stylesheet]
            [garden.selectors :as selectors]
            [garden.units :as units])
  #?(:cljs (:require-macros
            [hawkthorne.style :refer [defbreakpoint]])))

#?(:clj
   (defmacro defbreakpoint
     [name media-params]
     `(defn ~name [& rules#]
        (stylesheet/at-media ~media-params [:& rules#]))))

(defbreakpoint mobile-screen
  {:screen true
   :max-width (units/px 600)})

(defbreakpoint non-mobile-screen
  {:screen true
   :min-width (units/px 600)})

(def stylesheet
  [[:body
    {:margin 0}]
   [:#game
    [:canvas
     {:position :absolute
      :width (units/percent 100)
      :height (units/percent 100)
      :object-fit :contain}]]])

(defn render [] (garden/css stylesheet))
