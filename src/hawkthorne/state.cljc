(ns hawkthorne.state)

(defonce state
  (atom {:game nil
         :players []
         :maps []}))
