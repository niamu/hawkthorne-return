(ns hawkthorne.page
  "Page assets shared by server and client."
  (:require [hawkthorne.player :as player]
            [hawkthorne.state :as state]
            [hawkthorne.util :as util]
            [om.next :as om #?(:clj :refer :cljs :refer-macros) [defui]]
            [om.dom :as dom :refer [render-to-str]]
            [domkm.silk :as silk]
            [clojure.string :as string]
            #?@(:clj [[hiccup.page :as h]])))

(defn react-root
  "Add a React UI component as a root. Client needs to target DOM node."
  [root-component]
  (om/add-root! state/reconciler root-component
                #?(:clj  nil
                   :cljs (.getElementById js/document "app"))))

#?(:clj
   (defn wrap
     "Server-side wrapping of a React Root in HTML markup"
     [react-root]
     (h/html5
      [:head
       [:title "Hawkthorne"]
       [:meta {:name "viewport" :content "width=device-width,initial-scale=1"}]
       (h/include-css "/css/screen.css")]
      [:body
       [:div#app (render-to-str react-root)]
       [:div#game
        [:noscript "JavaScript is necessary to play this game"]]
       (h/include-js "/js/hawkthorne.js")])))

(defui Game
  static om/IQuery
  (query [this]
    [:current/character])
  Object
  (render [this]
    (let [current-character (:current/character (om/props this))]
      (-> [:div
           [:select
            {:value (str (name (:name current-character)) "/"
                         (name (:costume current-character)))
             :onChange
             #?(:clj nil
                :cljs (fn [e]
                        (let [[character costume]
                              (string/split (.. e -target -value) #"/")]
                          (om/transact!
                           this `[(current/character
                                   {:me ~(:me @state/state)
                                    :character ~(-> character keyword)
                                    :costume ~(-> costume keyword)})]))))}
            (map (fn [[character costumes]]
                   [:optgroup {:label (string/capitalize (name character))}
                    (map (fn [costume]
                           [:option
                            {:value (str (name character) "/" (name costume))}
                            (str (string/capitalize (name character))
                                 " (" (string/capitalize (name costume)) ")")])
                         (sort costumes))])
                 (sort player/characters))]]
          util/dom))))
