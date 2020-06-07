(ns app.datasource.alert
  (:require
   [app.datasource.game :as game]
   [app.domain.predicate :refer [email?]]
   [cljs.spec.alpha :as s]))

(s/def ::edit-action #{"set" "delete"})
(s/def ::email (s/and string? email?))
(s/def ::price (s/and pos? float?))

(defmulti action-type :edit-action)
(defmethod action-type "set" []
  (s/keys :req-un [::edit-action
                   ::email
                   ::game/gameID
                   ::price]))
(defmethod action-type "delete" []
  (s/keys :req-un [::edit-action
                   ::email
                   ::game/gameID]))

(s/def ::edit-alert (s/multi-spec action-type ::edit-action))

(s/def ::manage-action #(= "manage" %))

(s/def ::manage-request (s/keys :req-un [::manage-action
                                         ::email]))
