(ns app.domain.service)

(defprotocol Service
  (stores [datasource is-active])
  (deals [datasource options])
  (deal [datasource id])
  (games [datasource options])
  (game [datasource ids])
  (set-alert [datasource options])
  (delete-alert [datasource options])
  (email-alerts [datasource email]))
