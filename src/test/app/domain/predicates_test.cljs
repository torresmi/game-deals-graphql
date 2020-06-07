(ns app.domain.predicates-test
  (:require 
   [app.domain.predicates :as sut]
   [clojure.test :refer [is] :refer-macros [deftest testing]]))

(deftest not-blank?-test []
  (testing "is blank"
    (is (false? (sut/not-blank? ""))))
  
  (testing "is not blank"
    (is (true? (sut/not-blank? "a")))))

(deftest email?-test []
  (testing "valiid email"
    (for [email ["simp@le@example.com"]]
      (is (sut/email? email)))))
