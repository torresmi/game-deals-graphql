(ns app.datasource.alert
  (:require
   [app.datasource.game :as game]
   [app.domain.specs :refer [email?]]
   [cljs.spec.alpha :as s]))

(s/def ::edit-action #{"set" "delete"})
(s/def ::email (s/and string? email?))
(s/def ::price (s/and pos? float?))

(s/def ::edit-alert (s/keys :req [::edit-action
                                  ::email
                                  ::game/id
                                  ::price]))

(s/def ::manage-action #(= "manage" %))

(s/def ::manage-request (s/keys :req [::manage-action
                                      ::email]))
