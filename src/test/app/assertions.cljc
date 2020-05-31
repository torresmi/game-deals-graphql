(ns app.assertions
  (:require
   [cljs.test :refer [is]]))

(def is= (is =))
(def is-instance? (is instance?))
(def is-true? (is true?))
