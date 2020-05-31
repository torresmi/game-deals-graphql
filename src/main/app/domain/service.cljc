(ns app.domain.service)

(defprotocol Service
  (stores [datasource is-active])
  (deals [datasource options])
  (deal [datasource id])
  (games [datasource options])
  (game [datasource ids])
  (alert [datasource options]))
