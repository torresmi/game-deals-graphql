(ns app.domain.predicates
  (:require
   [clojure.string :as string]))

(def not-blank? (comp not string/blank?))
