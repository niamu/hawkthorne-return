(ns hawkthorne.game
  ;; Phaser convenience functions
  (:require [cljsjs.phaser-ce]
            [hawkthorne.state :as state]))

(defn build-state
  "Pass game object to each state function"
  [init-state game]
  (reduce (fn [accl [k f]]
            (assoc accl k (partial f game)))
          {} init-state))

(defn create-game!
  [width height init-state {:keys [parent]
                            :or [parent (.-body js/document)]
                            :as opts}]
  (let [game (new (.-Game js/Phaser) width height (.-AUTO js/Phaser) parent
                  {} false false)]
    (.. game -state
        (add "Boot" (clj->js (build-state init-state game))
             true))
    (swap! state/state assoc :game game)))
