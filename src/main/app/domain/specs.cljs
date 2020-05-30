(ns app.domain.specs
  (:require
   [cljs.spec.alpha :as s]
   [clojure.string :as string]))

(def not-blank? (comp not string/blank?))
