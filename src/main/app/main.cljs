(ns app.main
  (:require [shadow.resource :as rc]))

(def type-defs (rc/inline "./schema.graphql"))

(defn start! [] 
  (println "App started"))

(defn stop! []
  (println "Closing server."))

(defn main! []
  (println "App loaded!")
  (start!))
