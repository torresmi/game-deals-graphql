(ns app.datasource.cheapshark.deal
  (:require
   [app.datasource.cheapshark.date :as date]
   [app.datasource.cheapshark.game :as game]
   [app.datasource.cheapshark.metacritic :as metacritic]
   [app.datasource.cheapshark.pricing :as pricing]
   [app.datasource.cheapshark.steam :as steam]
   [app.datasource.cheapshark.store :as store]
   [app.domain.predicate :refer [not-blank?]]
   [cljs.spec.alpha :as s]))

(s/def ::pageNumber pos-int?)
(s/def ::pageSize pos-int?)
(s/def ::sortBy #{"Deal Rating"
                  "Title"
                  "Savings"
                  "Price"
                  "Metacritic"
                  "Reviews"
                  "Release"
                  "Store"
                  "recent"})
(s/def ::desc boolean?)
(s/def ::lowerPrice pos-int?)
(s/def ::metacritic pos-int?)
(s/def ::exact boolean?)
(s/def ::AAA boolean?)
(s/def ::onSale boolean?)
(s/def ::output #{"rss"})
(s/def ::deals-options (s/keys :opt-un [::store/storeID
                                        ::pageNumber
                                        ::pageSize
                                        ::sortBy
                                        ::desc
                                        ::lowerPrice
                                        ::metacritic
                                        ::steam/steamRating
                                        ::steam/steamAppRatings
                                        ::title
                                        ::exact
                                        ::AAA
                                        ::steam/steamworks
                                        ::onSale
                                        ::output]))

(s/def ::id not-blank?)
(s/def ::deal-option (s/keys :req-un [::id]))

(s/def ::internalName (s/nilable string?))
(s/def ::title (s/nilable string?))
(s/def ::dealRating (s/nilable string?))
(s/def ::deal-item (s/keys :opt-un [::internalName
                                    ::title
                                    ::metacritic/metacriticLink
                                    ::game/dealID
                                    ::store/storeID
                                    ::game/gameID
                                    ::pricing/salePrice
                                    ::pricing/normalPrice
                                    ::pricing/isOnSale
                                    ::pricing/savings
                                    ::metacritic/metacriticScore
                                    ::steam/steamRatingText
                                    ::steam/steamRatingPercent
                                    ::steam/steamRatingCount
                                    ::steam/steamAppID
                                    ::date/releaseDate
                                    ::date/lastChange
                                    ::dealRating
                                    ::game/thumb]))
