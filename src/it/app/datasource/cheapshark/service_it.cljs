(ns app.datasource.cheapshark.service-it
  (:require
   [app.datasource.cheapshark.service :as sut]
   [app.domain.service :as service]
   ["apollo-datasource-rest" :refer [RESTDataSource]]
   [cljs.test :refer-macros [deftest is testing]]
   [promesa.core :as p]
   [spy.core :as spy]
   [unbroken-promises.macro :refer-macros [is-resolved]]))

(def service (sut/init))

(deftest service-created-test
  (is (instance? RESTDataSource service))
  (is (satisfies? service/Service service)))

(def success-response (p/resolved []))
(def mock-fetch-success (spy/mock (fn [& _] success-response)))

(def stub-error (p/rejected (ex-info "error" {})))

(deftest stores-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is-resolved [result (service/stores service nil)]
                       (is (= success-response result)))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is-resolved [result (service/stores service nil)]
                   (is (= stub-error (service/stores service nil))))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/stores service nil)
      (is (true? (spy/called-with? mock-fetch-success service "stores"))))))

(deftest deals-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (service/deals service {})))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (service/deals service {})))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/deals service {})
      (is (true? (spy/called-with? mock-fetch-success service "deals" {}))))))

(deftest deal-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (service/deal service "1")))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (service/deal service "1")))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/deal service "1")
      (is (true? (spy/called-with? mock-fetch-success service "deals?id=1"))))))

(deftest games-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (service/games service {})))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (service/games service {})))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/games service {})
      (is (true? (spy/called-with? mock-fetch-success service "games" {}))))))

(deftest game-test
  (testing "with no ids"
    (is (thrown? js/Error (service/game service []))))

  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]

      (testing "with a single id"
        (is (=  success-response (service/game service ["1"]))))

      (testing "with multiple ids"
        (is (= success-response (service/game service ["1" "2"]))))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]

      (testing "with a single id"
        (is (=  stub-error (service/game service ["1"]))))

      (testing "with multiple ids"
        (is (=  stub-error (service/game service ["1" "2"]))))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]

      (testing "with a single id"
        (service/game service ["1"])
        (is true? (spy/called-with? mock-fetch-success service "games" {:id "1"})))

      (testing "with multiple ids"
        (service/game service ["1" "2"])
        (is (true? (spy/called-with? mock-fetch-success service "games" {:ids "1,2"})))))))

(def valid-set-alert {:action "set"
                  :email "test@example.com"
                  :gameID "1"
                  :price 20.50})

(deftest set-alert-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is-resolved [result (service/set-alert service valid-set-alert)]
                   (is (= success-response result)))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is-resolved [result (service/set-alert service valid-set-alert)]
                   (is (= stub-error result)))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/set-alert service valid-set-alert)
      (is (true? (spy/called-with? mock-fetch-success service "alerts" "action=set&email=test%40example.com&gameID=1&price=20.5"))))))

(def valid-delete-alert {:action "delete"
                         :email "test@example.com"
                         :gameID "1"})

(deftest delete-alert-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is-resolved [result (service/delete-alert service valid-delete-alert)]
                   (is (= success-response result)))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is-resolved [result (service/delete-alert service valid-delete-alert)]
                   (is (= stub-error result)))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/delete-alert service valid-delete-alert)
      (is (true? (spy/called-with? mock-fetch-success service "alerts" "action=delete&email=test%40example.com&gameID=1"))))))

(deftest email-alerts-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is-resolved [result (service/email-alerts service "test@example.com")]
                   (is (= success-response result)))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is-resolved [result (service/email-alerts service "test@example.com")]
                   (is (= stub-error result)))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/email-alerts service "test@example.com")
      (is (true? (spy/called-with? mock-fetch-success service "alerts" "action=manage&email=test%40example.com"))))))