(ns app.datasource.cheapshark.store-test
  (:require
   [app.datasource.cheapshark.store :as sut]
   [cljs.spec.alpha :as s]
   [clojure.test :refer [is] :refer-macros [deftest testing]]
   [clojure.test.check.clojure-test :refer-macros [defspec]]
   [clojure.test.check.generators :as gen]
   [clojure.test.check.properties :refer [for-all]]))

(def store-gen (s/gen ::sut/store))
(def stores-gen (gen/list store-gen))

(defn- set-active [store active]
  (assoc store :isActive active))

(defn- gen-with-active [active]
  (gen/fmap #(set-active % active) store-gen))

(defspec domain-mapping-test
  (for-all [store store-gen]
    (let [result (sut/store->domain store)]
      (and (= (:storeID store) (:id result))
           (= (:storeName store) (:name result))
           (= (dissoc store :storeID :storeName :isActive)
              (dissoc result :id :name :isActive))))))

(deftest is-active-mapping-test
  (let [store (gen/generate store-gen)]
    (testing "isActive is 0"
      (let [inactive-store (assoc store :isActive 0)]
        (is (false? (:isActive (sut/store->domain inactive-store))))))
    
    (testing "isActive is 1"
      (let [active-store (assoc store :isActive 1)]
        (is (true? (:isActive (sut/store->domain active-store))))))))

(defspec filter-by-active-testl
  (for-all [active-stores (gen/list (gen-with-active true))
            inactive-stores (gen/list (gen-with-active false))]
    (let [all-stores (concat active-stores inactive-stores)]
      (= active-stores (sut/filter-stores-by-active true all-stores))
      (= inactive-stores (sut/filter-stores-by-active false all-stores)))))
