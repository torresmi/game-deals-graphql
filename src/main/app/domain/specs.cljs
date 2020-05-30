(ns app.domain.specs
  (:require
   [clojure.string :as string]))

(def not-blank? (comp not string/blank?))
