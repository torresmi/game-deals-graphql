(ns app.datasource.cheapshark.pricing
  (:require
   [cljs.spec.alpha :as s]))

(s/def ::cheapest (s/nilable string?))
(s/def ::price (s/nilable string?))
(s/def ::salePrice (s/nilable string?))
(s/def ::normalPrice (s/nilable string?))
(s/def ::isOnSale (s/nilable #{"0" "1"}))
(s/def ::savings (s/nilable string?))
