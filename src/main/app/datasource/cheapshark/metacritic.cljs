(ns app.datasource.cheapshark.metacritic
  (:require
   [cljs.spec.alpha :as s]))

(s/def ::metacriticScore (s/nilable string?))
(s/def ::metacriticLink (s/nilable string?))
