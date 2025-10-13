(ns corenet-backend.core
  (:gen-class)
  (:require
   [corenet-backend.routes :refer :all]         
   [org.httpkit.server :as hk-server]
   [compojure.core :refer [defroutes, context]]))

(defroutes app
  (context "/api/v1/" []
    (home-route)         ;; /
    (create-post-route)  ;; /create-post
    (notfound-route)     ;; Not Found 404
    ))

(defn -main [& args]
  (def my-server (hk-server/run-server app {:port 5000}))
  (println
"""
Default Routes:
http://localhost:5000/api/v1/
http://localhost:5000/api/v1/create-post"""))

;; Start Server
;; (-main)
;; Stop Server
;; (my-server)
