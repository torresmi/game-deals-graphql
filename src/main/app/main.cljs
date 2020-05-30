(ns app.main
  (:require
   [app.datasource.cheap-shark-rest :as cheap-shark-rest]
   [app.resolvers :refer [resolver-map]]
   ["apollo-server" :as apollo]
   [shadow.resource :as rc]))

(def type-defs (rc/inline "./schema.graphql"))

(defn data-sources []
  #js {:cheap-shark-rest (cheap-shark-rest/init)})

(def resolvers (clj->js resolver-map))

(def server (new apollo/ApolloServer #js {:typeDefs type-defs
                                          :resolvers resolvers
                                          :dataSources data-sources}))

(defn start! []
  (-> (.listen server)
      (.then #(println "Server ready at" (.-url %)))))

(defn main! []
  (println "App loaded!")
  (start!))
