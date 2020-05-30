(ns app.resolvers-test
  (:require
   [app.resolvers :as sut]
   [cljs.test :refer-macros [deftest is testing]]))

(deftest resolver-map-queries-test []
  (testing "contains stores query"
    (is (fn? (get-in sut/resolver-map [:Query :stores])))))
