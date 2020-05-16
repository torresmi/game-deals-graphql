(ns app.main-it
  (:require
   [app.main :as sut]
   [cljs.test :refer [deftest is use-fixtures]]))

(defn with-server [f]
  (sut/main!)
  (f)
  (sut/stop!))

(use-fixtures :once with-server)

(deftest schema-loaded-test
  (is (not (nil? sut/type-defs))))
