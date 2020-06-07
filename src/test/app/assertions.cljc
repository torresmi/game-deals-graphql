(ns app.assertions
  (:require
   [cljs.test :refer [is]]))

(def is= (comp is =))
(def is-instance? (comp is instance?))
(def is-true? (comp is true?))
(def is-false? (comp is false?))
