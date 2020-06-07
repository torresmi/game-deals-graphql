(ns app.domain.predicate-test
  (:require 
   [app.domain.predicate :as sut]
   [clojure.test :refer [is] :refer-macros [deftest testing]]))

(deftest not-blank?-test []
  (testing "is blank"
    (is (false? (sut/not-blank? ""))))
  
  (testing "is not blank"
    (is (true? (sut/not-blank? "a")))))

(deftest email?-test []
  (testing "valiid email"
    (for [email ["simp@le@example.com"
                 "very.common@example.com"
                 "dispsable.style.email.with+symbol@example.com"
                 "other.email-with-hyphen@example.com"
                 "fully-qualified-domain@example"
                 "user.name+tag+sorting@example.com"
                 "x@example.com"]]
      (is (sut/email? email))))
  
  (testing "invalid email"
    (for [email ["Abc.example.com"
                 "A@b@c@example.com"
                 "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"]]
      (is (false? (sut/email? email))))))
