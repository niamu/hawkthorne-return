(ns hawkthorne.util)

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

(def keymap
  {32 :space
   37 :left
   38 :up
   39 :right
   40 :down})
