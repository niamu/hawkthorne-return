(ns hawkthorne.page
  "Page assets shared by server and client."
  (:require [hawkthorne.characters :refer [characters]]
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
       [:div#game
        [:noscript "JavaScript is necessary to play this game"]]
       [:div#app (render-to-str react-root)]
       (h/include-js "/js/hawkthorne.js")])))

(defui Game
  static om/IQuery
  (query [this]
    [:current/character :game/debugging? :game/fps])
  Object
  (render [this]
    (let [{:keys [current/character game/debugging? game/fps]} (om/props this)]
      (-> [:div.hud
           [:select
            {:value (str (name (:name character)) "/"
                         (name (:costume character)))
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
            (map (fn [[char details]]
                   [:optgroup
                    {:label (string/capitalize (name char))}
                    (map (fn [costume]
                           [:option
                            {:value (str (name char) "/"
                                         (name costume))}
                            (str (string/capitalize (name char))
                                 " (" (string/capitalize (name costume)) ")")])
                         (sort (keys (:costumes details))))])
                 (sort characters))]
           [:label
            [:input {:type "checkbox"
                     :checked debugging?
                     :onChange
                     #?(:clj nil
                        :cljs #(om/transact! this '[(game/debugging?)]))}]
            [:span "Debugging"]]
           (when debugging?
             [:label
              [:span (str "Framerate: " fps)]])]
          util/dom))))
