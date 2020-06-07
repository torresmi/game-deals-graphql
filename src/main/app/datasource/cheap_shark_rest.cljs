(ns app.datasource.cheap-shark-rest
  (:require
   [app.datasource.alert :as alerts]
   [app.datasource.store :as stores]
   [app.domain.service :refer [Service]]
   ["apollo-datasource-rest" :refer [RESTDataSource]]
   [clojure.spec.alpha :as s]
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

(defn- update-alert [datasource type options]
  (let [updated-options (assoc options :action type)]
    (-> (fetch datasource "alerts" (alerts/edit-alert->query-string updated-options))
        (p/then boolean))))

(extend-type RESTDataSource
  Service
  (stores [this is-active]
    (-> (fetch this "stores")
        (p/then (partial map stores/store->domain))
        (p/then (fn [stores] (if (nil? is-active) stores (stores/filter-stores-by-active is-active stores))))))
  (deals [this options]
    (fetch this "deals" options))
  (deal [this id]
    (fetch this (str "deals?id=" id)))
  (games [this options]
    (fetch this "games" options))
  (game [this ids]
    (fetch-games this ids))
  (set-alert [this options]
    (update-alert this "set" options))
  (delete-alert [this options]
    (update-alert this "delete" options)))

(defn init []
  (let [data-source (new RESTDataSource)]
    (set! (.-baseURL data-source) (str domain base-path))
    (.initialize data-source js-obj)
    data-source))
