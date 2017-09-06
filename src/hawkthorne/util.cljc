(ns hawkthorne.util
  "Helper functions"
  (:require [clojure.set :refer [rename-keys]]
            [clojure.string :as string]
            [om.dom :as dom]
            [om.next.protocols :as p]
            #?@(:clj  [[sablono.normalize :as normalize]
                       [sablono.util :refer [element?]]]
                :cljs [[sablono.core :as html :refer-macros [html]]])))

#?(:clj
   (defn- compile-attrs
     "Conform attributes to a format expected by Om DOM utils"
     [attrs]
     (when-not (or (nil? attrs) (empty? attrs))
       (cond-> (rename-keys attrs {:class :className
                                   :for :htmlFor})
         (:class attrs) (update-in [:className] #(string/join #" " %))))))

#?(:clj
   (defn- compile-element
     "Given Hiccup structure, recursively conform the structure to one
     that Om DOM utils expect"
     [element]
     (cond
       (element? element)
       (let [[tag attrs children] (normalize/element element)]
         (dom/element {:tag tag
                       :attrs (compile-attrs attrs)
                       :children (vector (map compile-element children))}))
       (satisfies? p/IReactComponent element) element
       :else (dom/text-node (str element)))))

(defn dom
  "Convert Hiccup syntax to Om DOM snytax."
  [markup]
  #?(:clj (compile-element markup)
     :cljs (html markup)))
