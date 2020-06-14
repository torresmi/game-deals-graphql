(ns app.datasource.date
  (:require
   [cljs.spec.alpha :as s]))

(s/def ::releaseDate (s/nilable double?))
(s/def ::lastChange (s/nilable double?))
(s/def ::date (s/nilable double?))
