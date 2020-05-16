(ns app.main-it
  (:require
   [cljs.test :refer [deftest is use-fixtures]]
   [app.main :as sut]))

(defn with-server [f]
  (sut/main!)
  (f)
  (sut/stop!))

(use-fixtures :once with-server)

(deftest schema-loaded-test
  (is (not (nil? sut/type-defs))))
