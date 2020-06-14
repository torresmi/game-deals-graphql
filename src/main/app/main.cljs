(ns app.main
  (:require
   [app.datasource.cheapshark.service :as cheapshark]
   [app.resolver :refer [resolver-map]]
   ["apollo-server" :as apollo]
   [shadow.resource :as rc]))

(def type-defs (rc/inline "./schema.graphql"))

(defn data-sources []
  #js {:cheapshark-service (cheapshark/init)})

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
