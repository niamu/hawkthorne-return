(ns hawkthorne.util
  (:require [clojure.string :as string]))

(def game-width 620)
(def game-height 336)
(def tickrate 33)
(def step 300)
(def friction (* 0.146875 step))
(def acceleration (* 0.15 step))
(def deceleration (* 0.5 step))
(def gravity (* 0.21875 step))
(def max-x-velocity 9)
(def max-y-velocity 20)

#?(:cljs
   (defn arraybuffer->str
     [buf]
     (string/join (map #(js/String.fromCharCode %)
                       (seq (.from js/Array (js/Int8Array. buf)))))))
