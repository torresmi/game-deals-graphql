(ns app.datasource.steam
  (:require
   [cljs.spec.alpha :as s]))

(s/def ::steamAppID (s/nilable string?))
(s/def ::steamRatingText (s/nilable string?))
(s/def ::steamRatingPercent (s/nilable string?))
(s/def ::steamRatingCount (s/nilable string?))
(s/def ::steamworks boolean?)
