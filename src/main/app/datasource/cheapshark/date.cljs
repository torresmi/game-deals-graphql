(ns app.datasource.cheapshark.date
  (:require
   [app.domain.predicate :refer [pos-double?]]
   [cljs.spec.alpha :as s]))

(s/def ::releaseDate (s/nilable pos-double?))
(s/def ::lastChange (s/nilable double?))
(s/def ::date (s/nilable pos-double?))
