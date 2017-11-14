(ns hawkthorne.page
  "Page assets shared by server and client."
  (:require [hawkthorne.characters :as characters]
            [hawkthorne.routes :as routes]
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
     [react-root route]
     (h/html5
      [:head
       [:title "Hawkthorne"]
       [:meta {:name "viewport" :content "width=device-width,initial-scale=1"}]
       (h/include-css "/css/screen.css")]
      [:body
       (when (= :game route)
         [:div#game
          [:noscript "JavaScript is necessary to play this game"]])
       [:div#app (render-to-str react-root)]
       (h/include-js "/js/hawkthorne.js")])))

(defui Hero
  Object
  (render [this]
    (let [{:keys [character checked? costumes disabled?]} (om/props this)]
      (-> [:div.hero-container
           [:input {:type "radio"
                    :disabled (and disabled? (not checked?))
                    :checked checked?
                    :id (str "hero_" (name character))
                    :name "hero"
                    :onChange
                    #?(:cljs (fn [e]
                               (.preventDefault e)
                               (om/transact! this
                                             `[(heroes/change
                                                {:me ~(:me @state/state)
                                                 :selected ~character})]))
                       :clj nil)}]
           [:div.hero
            [:label.image
             {:htmlFor (str "hero_" (name character))
              :style {:background (str "url(/images/characters/"
                                       (name character) "/"
                                       "base.png)")}}]]]
          util/dom))))

(def hero (om/factory Hero))

(defui Lobby
  static om/IQuery
  (query [this]
    [:heroes/available :heroes/current])
  Object
  (render [this]
    (let [{:keys [heroes/available heroes/current]} (om/props this)]
      (-> [:div.lobby
           [:div.heroes
            (mapv (fn [h]
                    (hero (merge (val h)
                                 {:character (key h)
                                  :disabled? (not (contains? (set available)
                                                             (key h)))
                                  :checked? (= (key h) current)})))
                  (->> characters/greendale-seven
                       (select-keys characters/characters)))]
           #_[:select
              {:name "character"}
              (map (fn [[char details]]
                     [:optgroup
                      {:label (string/capitalize (name char))}
                      (map (fn [costume]
                             [:option
                              {:value (str (name char) "/"
                                           (name costume))}
                              (str (string/capitalize (name char))
                                   " ("
                                   (string/capitalize (name costume))
                                   ")")])
                           (sort (keys (:costumes details))))])
                   (sort characters))]]
          util/dom))))

(defui Game
  static om/IQuery
  (query [this]
    [:game/debugging? :game/fps])
  Object
  (render [this]
    (let [{:keys [game/debugging? game/fps]} (om/props this)]
      (-> [:div#overlay
           [:div#hud
            [:label
             [:input {:type "checkbox"
                      :checked debugging?
                      :onChange
                      #?(:clj nil
                         :cljs #(om/transact! this '[(game/debugging?)]))}]
             [:span "Debugging"]]
            (when debugging?
              [:label
               [:span (str "Framerate: " fps)]])]]
          util/dom))))
