(ns app.datasource.alert
  (:require
   [app.datasource.game :as game]
   [app.domain.predicate :refer [email?]]
   [cemerick.url :as url]
   [cljs.spec.alpha :as s]))

(s/def ::action #{"set" "delete"})
(s/def ::email (s/and string? email?))
(s/def ::price (s/and pos? float?))

(defmulti action-type :action)
(defmethod action-type "set" []
  (s/keys :req-un [::action
                   ::email
                   ::game/gameID
                   ::price]))
(defmethod action-type "delete" []
  (s/keys :req-un [::action
                   ::email
                   ::game/gameID]))

(s/def ::edit-alert (s/multi-spec action-type ::edit-action))

(s/fdef edit-alert->query-string
        :args (s/cat :edit-alert ::edit-alert)
        :ret string?)

(defn edit-alert->query-string [edit-alert]
  (url/map->query edit-alert))
