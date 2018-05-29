(ns hawkthorne.player
  (:require [hawkthorne.atlas :refer [animations]]))

#?(:cljs
   (defn update!
     [game player cursors collision-layer]
     (-> game .-physics .-arcade
         (.collide collision-layer player
                   (fn [c o])))
     (let [facing (aget player "facing")]
       (when (and (.-isDown (-> game .-input .-keyboard
                                (.addKey (-> js/Phaser .-KeyCode .-SPACEBAR))))
                  (.. player -body (onFloor)))
         (aset (-> player .-body .-velocity) "y" -300))
       (when (-> cursors .-right .-isDown)
         (aset player "facing" "right")
         (aset (-> player .-body .-velocity) "x"
               (+ (aget (-> player .-body .-velocity) "x")
                  (/ (* (-> game .-time .-elapsedMS)
                        (-> game .-time .-fps)) 100)))
         (when (.. player -body (onFloor))
           (if (-> cursors .-down .-isDown)
             (.. player -animations (play "crawlwalk-right" 10 true))
             (.. player -animations (play "walk-right" 10 true)))))
       (when (-> cursors .-left .-isDown)
         (aset player "facing" "left")
         (aset (-> player .-body .-velocity) "x"
               (- (aget (-> player .-body .-velocity) "x")
                  (/ (* (-> game .-time .-elapsedMS)
                        (-> game .-time .-fps)) 100)))
         (when (.. player -body (onFloor))
           (if (-> cursors .-down .-isDown)
             (.. player -animations (play "crawlwalk-left" 10 true))
             (.. player -animations (play "walk-left" 10 true)))))
       (when (and (-> cursors .-left .-isUp)
                  (-> cursors .-right .-isUp))
         (aset (-> player .-body .-velocity) "x" 0)
         (if (-> cursors .-down .-isDown)
           (.. player -animations (play (str "crouch-" facing) 10 true))
           (.. player -animations (play (str "idle-" facing))))
         (when (-> cursors .-up .-isDown)
           (.. player -animations (play (str "gaze-" facing)))))
       (when-not (.. player -body (onFloor))
         (if (-> cursors .-down .-isDown)
           (.. player -animations (play (str "crouch-" facing) 10 true))
           (.. player -animations (play (str "jump-" facing))))))))

#?(:cljs
   (defn add-animation
     [player animation-name [loop? coords interval]]
     (.. player -animations
         (add animation-name
              (.. js/Phaser -Animation
                  (generateFrameNames (str animation-name "-")
                                      0 (dec (count coords))))))))

(defn add-animations!
  [player]
  (doseq [[animation-name args] animations]
    (if (map? args)
      (do (add-animation player (str (name animation-name) "-left")
                         (:left args))
          (add-animation player (str (name animation-name) "-right")
                         (:right args)))
      (add-animation player (name animation-name) args))))
