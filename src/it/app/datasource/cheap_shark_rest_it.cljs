(ns app.datasource.cheap-shark-rest-it
  (:require
   [app.datasource.cheap-shark-rest :as sut]
   ["apollo-datasource-rest" :refer [RESTDataSource]]
   [cljs.test :refer [deftest is testing]]
   [promesa.core :as p]
   [spy.core :as spy]))

(def service (sut/init))

(deftest service-created-test
  (is (instance? RESTDataSource service))
  (is (satisfies? sut/Service service)))

(def success-response (p/resolved []))
(def mock-fetch-success (spy/mock (fn [& _] success-response)))

(def stub-error (p/rejected (ex-info "error" {})))

(deftest stores-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (sut/stores service)))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (sut/stores service)))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (sut/stores service)
      (is (true? (spy/called-with? mock-fetch-success service "stores"))))))

(deftest deals-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (sut/deals service {})))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (sut/deals service {})))))

  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (sut/deals service {})
      (is (true? (spy/called-with? mock-fetch-success service "deals" {}))))))

(deftest deal-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (sut/deal service "1")))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (sut/deal service "1")))))
  
  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (sut/deal service "1")
      (is (true? (spy/called-with? mock-fetch-success service "deals?id=1"))))))

(deftest games-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (sut/games service {})))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (sut/games service {})))))
  
  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (sut/games service {})
      (is (true? (spy/called-with? mock-fetch-success service "games" {}))))))

(deftest game-test
  (testing "with no ids"
    (is (thrown? js/Error (sut/game service []))))

  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]

      (testing "with a single id"
        (is (= success-response (sut/game service ["1"]))))

      (testing "with multiple ids"
        (is (= success-response (sut/game service ["1" "2"]))))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]

      (testing "with a single id"
        (is (= stub-error (sut/game service ["1"]))))

      (testing "with multiple ids"
        (is (= stub-error (sut/game service ["1" "2"]))))))
  
  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      
      (testing "with a single id"
        (sut/game service ["1"])
        (is (true? (spy/called-with? mock-fetch-success service "games" {:id "1"}))))
      
      (testing "with multiple ids"
        (sut/game service ["1" "2"])
        (is (true? (spy/called-with? mock-fetch-success service "games" {:ids "1,2"})))))))

(deftest alert-test
  (testing "with success"
    (with-redefs [sut/fetch (spy/stub success-response)]
      (is (= success-response (sut/alert service {})))))

  (testing "with failure"
    (with-redefs [sut/fetch (spy/stub stub-error)]
      (is (= stub-error (sut/alert service {})))))
  
  (testing "arguments"
    (with-redefs [sut/fetch mock-fetch-success]
      (sut/alert service {})
      (is (true? (spy/called-with? mock-fetch-success service "alert" {}))))))
