(ns app.main)

(defn start! [] 
  (println "App started"))

(defn stop! []
  (println "Closing server."))

(defn main! []
  (println "App loaded!")
  (start!))
