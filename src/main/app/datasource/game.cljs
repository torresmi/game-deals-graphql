(ns app.datasource.game
  (:require
   [app.datasource.date :as date]
   [app.datasource.metacritic :as metacritic]
   [app.datasource.pricing :as pricing]
   [app.datasource.steam :as steam]
   [app.datasource.store :as store]
   [app.domain.predicate :refer [not-blank?]]
   [cljs.spec.alpha :as s]))

(s/def ::dealID not-blank?)
(s/def ::gameID not-blank?)
(s/def ::name (s/nilable string?))
(s/def ::publisher (s/nilable string?))
(s/def ::thumb (s/nilable string?))
(s/def ::gameInfo (s/keys :opt-un [::store/storeID
                                   ::gameID
                                   ::name
                                   ::steam/steamAppID
                                   ::pricing/salePrice
                                   ::pricing/retailPrice
                                   ::steam/steamRatingText
                                   ::steam/steamRatingPercent
                                   ::steam/steamRatingCount
                                   ::metacritic/metacriticScore
                                   ::metacritic/metacriticLink
                                   ::date/releaseDate
                                   ::publisher
                                   ::steam/steamworks
                                   ::thumb]))

(s/def ::cheaperStores (s/keys :opt-un [::dealID
                                        ::store/storeID
                                        ::pricing/salePrice
                                        ::pricing/retailPrice]))

(s/def ::cheapestPrice (s/keys :opt-un [::pricing/price ::date/date]))

(s/def ::game-response (s/keys :opt-un [::gameInfo
                                        ::cheaperStores
                                        ::cheapestPrice]))

(s/def ::title not-blank?)
(s/def ::exact #{0 1})
(s/def ::limit pos-int?)

(defmulti search-game-options :title)
(defmethod search-game-options nil [] (s/keys :req-un [::steam/steamAppID]))
(defmethod search-game-options :default [] (s/keys :req-un [::title] :opt-un [::limit ::exact]))

(s/def ::search-game-options (s/multi-spec search-game-options ::title))

(s/def ::cheapestDealID (s/nilable string?))
(s/def ::external (s/nilable string?))
(s/def ::game-item (s/keys :opt-un [::gameID
                                    ::steam/steamAppID
                                    ::pricing/cheapest
                                    ::cheapestDealID
                                    ::external
                                    ::thumb]))

(s/def ::search-games-option (s/coll-of not-blank? :max-count 25))
(s/def ::info (s/keys :opt-un [::title
                               ::steam/steamAppID
                               ::thumb]))
(s/def ::deal-glance (s/keys :opt-un [::store/storeID
                                      ::dealID
                                      ::pricing/price
                                      ::pricing/retailPrice
                                      ::pricing/savings]))

(s/def ::cheapestPriceEver (s/keys :opt-un [::pricing/price ::date/date]))

(s/def ::deals (s/coll-of ::deal-glance))
(s/def ::search-games-response (s/keys :opt-un [::cheapestPriceEver ::deals]))
