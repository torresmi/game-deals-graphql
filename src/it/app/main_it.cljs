(ns app.main-it
  (:require
   [app.main :as sut]
   [cljs.test :refer-macros [deftest is use-fixtures]]))

(defn with-server [f]
  (sut/main!)
  (f))

(use-fixtures :once with-server)

(deftest schema-loaded-test
  (is (some? sut/type-defs)))
