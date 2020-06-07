(ns app.resolver
  (:require
   [app.domain.service :as network]
   [oops.core :refer [oget]]
   [promesa.core :as p]))

(defn- with-network [f]
  (fn [_ options context]
    (let [network (oget context "dataSources.cheap-shark-rest")
          parsed-options (js->clj options :keywordize-keys true)]
      (-> (f network parsed-options)
          (p/then clj->js)))))

(defn- get-stores [network {:keys [isActive]}]
  (network/stores network isActive))

(defn- email-alerts [network {:keys [email]}]
  (network/email-alerts network email))

(def resolver-map {:Query
                   {:stores (with-network get-stores)}
                   :Mutation
                   {:setAlert (with-network network/set-alert)
                    :deleteAlert (with-network network/delete-alert)
                    :emailAlerts (with-network email-alerts)}})
