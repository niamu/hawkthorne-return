(ns hawkthorne.page
  "Page assets shared by server and client."
  (:require [hawkthorne.state :as state]
            [hawkthorne.util :as util]
            [om.next :as om #?(:clj :refer :cljs :refer-macros) [defui]]
            [om.dom :as dom :refer [render-to-str]]
            [domkm.silk :as silk]
            #?@(:clj [[hiccup.page :as h]])))

(defn react-root
  "Add a React UI component as a root. Client needs to target DOM node."
  #?(:clj [root-component request]
     :cljs [root-component])
  (om/add-root! (state/reconciler #?(:clj request
                                     :cljs {}))
                root-component
                #?(:clj  nil
                   :cljs (.getElementById js/document "app"))))

#?(:clj
   (defn wrap
     "Server-side wrapping of a React Root in HTML markup"
     [react-root]
     (h/html5
      [:head
       [:title "Hawkthorne"]
       [:meta {:name "viewport"
               :content "width=device-width,initial-scale=1"}]
       (h/include-css "/css/screen.css")]
      [:body
       [:div#app (render-to-str react-root)]
       [:div#game
        ;; TODO: Some message for users with JS disabled
        ]
       (h/include-js "/js/hawkthorne.js")])))

(defui Game
  Object
  (render [this]
    (-> [:div
         [:h1 "Hello world"]]
        util/dom)))
