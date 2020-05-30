(ns app.domain.service)

(defprotocol Service
  (stores [datasource])
  (deals [datasource options])
  (deal [datasource id])
  (games [datasource options])
  (game [datasource ids])
  (alert [datasource options]))
