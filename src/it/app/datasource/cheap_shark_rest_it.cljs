(ns app.datasource.cheap-shark-rest-it
  (:require
   [app.datasource.cheap-shark-rest :as sut]
   [app.domain.service :as service]
   ["apollo-datasource-rest" :refer [RESTDataSource]]
   [cljs.test :refer-macros [deftest is testing]]
   [promesa.core :as p]
   [spy.core :as spy]))

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
      (is (= success-response (service/stores service nil)))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (service/stores service nil)))))

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

(deftest alert-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (service/alert service {})))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (service/alert service {})))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (service/alert service {})
      (is (true? (spy/called-with? mock-fetch-success service "alert" {}))))))
