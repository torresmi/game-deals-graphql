(ns app.main
  (:require
   ["apollo-server" :as apollo]
   [shadow.resource :as rc]))

(def type-defs (rc/inline "./schema.graphql"))

(def server (new apollo/ApolloServer #js {"typeDefs" type-defs}))

(defn start! []
  (-> (.listen server)
   (.then #(println "Server ready at" (.-url %)))))

(defn main! []
  (println "App loaded!")
  (start!))
