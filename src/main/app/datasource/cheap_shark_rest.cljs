(ns app.datasource.cheap-shark-rest
  (:require
   ["apollo-datasource-rest" :refer [RESTDataSource]]
   [clojure.string :refer [join]]
   [promesa.core :as p]))

(def domain "http://www.cheapshark.com")

(def base-path "/api/1.0/")

(defn- fetch
  ([datasource path]
   (fetch datasource path nil))
  ([datasource path options]
   (->> (.get datasource path (clj->js options))
        (p/map #(js->clj % :keywordize-keys true)))))

(defn- fetch-games [datasource ids]
  (let [fetch-games-with-ids (partial fetch datasource "games")]
    (case (count ids)
      0 (throw (js/Error. "ids is required"))
      1 (fetch-games-with-ids {:id (first ids)})
      (fetch-games-with-ids {:ids (join "," ids)}))))

(defprotocol Service
  (stores [datasource])
  (deals [datasource options])
  (deal [datasource id])
  (games [datasource options])
  (game [datasource ids])
  (alert [datasource options]))

(extend-type RESTDataSource
  Service
  (stores [this]
    (fetch this "stores"))
  (deals [this options]
    (fetch this "deals" options))
  (deal [this id]
    (fetch this (str "deals?id=" id)))
  (games [this options]
    (fetch this "games" options))
  (game [this ids]
    (fetch-games this ids))
  (alert [this options]
    (fetch this "alert" options)))

(defn init []
  (let [data-source (new RESTDataSource)]
    (set! (.-baseURL data-source) (str domain base-path))
    (.initialize data-source js-obj)
    data-source))
