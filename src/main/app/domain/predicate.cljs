(ns app.domain.predicate
  (:require
   [clojure.string :as string]))

(def not-blank? (comp not string/blank?))

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(def email? #(re-matches email-regex %));

(def pos-double? (comp pos? double?))
