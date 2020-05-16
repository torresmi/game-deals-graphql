(ns app.main
  (:require [shadow.resource :as rc]
             ["apollo-server" :as apollo]))

(def type-defs (rc/inline "./schema.graphql"))

(def server (new apollo/ApolloServer #js {"typeDefs" type-defs}))

(defn start! []
  (->
   (.listen server)
   (.then #(println "Server ready at" (.-url %)))))

(defn stop! []
  (println "Closing server.")
  (.stop server))

(defn main! []
  (println "App loaded!")
  (start!))
