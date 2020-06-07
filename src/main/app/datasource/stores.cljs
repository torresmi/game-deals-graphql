(ns app.datasource.stores
  (:require
   [app.domain.predicates :refer [not-blank?]]
   [cljs.spec.alpha :as s]
   [clojure.set :as set]))

(s/def ::storeID (s/and string? not-blank?))
(s/def ::isActive (s/nilable (s/int-in 0 2)))
(s/def ::storeName (s/nilable string?))

(s/def ::banner (s/nilable string?))
(s/def ::logo (s/nilable string?))
(s/def ::icon (s/nilable string?))
(s/def ::images (s/keys :opt-un [::banner
                                 ::logo
                                 ::icon]))

(s/def ::store (s/keys :opt-un [::storeID
                                ::storeName
                                ::isActive
                                ::images]))

(defn- is-active-int->bool [is-active]
  (case is-active
    0 false
    1 true
    false))

(s/fdef store->domain
  :args (s/cat :store ::store)
  :ret map?)

(defn store->domain [store]
  (-> (update store :isActive is-active-int->bool)
      (set/rename-keys {:storeID :id
                        :storeName :name})))

(s/fdef is-active? 
        :args (s/cat :store ::store)
        :ret boolean?)

(defn is-active? [store] (true? (:isActive store)))

(s/fdef filter-stores-by-active
        :args (s/cat :is-active boolean? :stores (s/coll-of ::store))
        :ret (s/coll-of ::store))

(defn filter-stores-by-active [is-active stores]
  (filter #(= is-active (is-active? %)) stores))
