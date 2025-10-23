(ns corenet-backend.core
  (:gen-class)
  (:require
   [corenet-backend.routes :refer :all]         
   [org.httpkit.server :as hk-server]
   [compojure.core :refer [defroutes, context]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
   [ring.middleware.cors :refer [wrap-cors]]))

(defroutes app
  (context "/api/v1" []
  (home-route)         ;; /              GET
  (create-post-route)  ;; /create-post   POST
  (all-blogs-route)    ;; /all-blogs     GET
  (notfound-route)))   ;; Not Found 404  GET/POST




(defn -main [& args]
  (let [my-server (hk-server/run-server (wrap-cors (wrap-defaults app api-defaults) 
  :access-control-allow-origin [#".*"]
  :access-control-allow-credentials "true"
  :access-control-allow-methods [:get :put :post :delete]) 
  {:port 5000})]
  (println "Starting server at port 5000")))

  ;; Start Server
  ;; (-main)
  ;; Stop Server
  ;; (my-server)
  
