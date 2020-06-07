(ns app.resolver-test
  (:require
   [app.resolvers :as sut]
   [cljs.test :refer-macros [deftest is testing]]))

(deftest resolver-map-queries-test []
  (testing "contains stores query"
    (is (fn? (get-in sut/resolver-map [:Query :stores])))))

(deftest resolver-map-mutations-test []
  (let [mutation-operations (:Mutation sut/resolver-map)]
    (testing "contains setAlert mutation"
      (is (fn? (:setAlert mutation-operations))))

    (testing "contains deleteAlert mutation"
      (is (fn? (:deleteAlert mutation-operations))))
    
    (testing "contains emailAlerts mutation"
      (is (fn? (:emailAlerts mutation-operations))))))
