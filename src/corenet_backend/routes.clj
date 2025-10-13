(ns corenet-backend.routes
  (:require
   [cheshire.core :refer :all]
   [compojure.core :refer :all]
   [compojure.route :as route]))


(defn home-route []
  (GET "/" []
       {:status 200
        :header {"Content-Type" "application/json"}
        :body (generate-string {:msg "Welcome To CoreNet API V1"})}))


(defn create-post-route []
  (POST "/create-post" []
    (try
      ;; Alguma função que realiza a criação do POST utilizando o Datalog
      [(= 1 1)
       {:status 200
        :header {"Content-Type" "application/json"}
        :body (generate-string {:msg "Postagem realizada com sucesso!"})}]
      (catch Exception e
        {:status 500
         :header {"Content-Type " "application/json"}
         :body (generate-string {:msg "Ocorreu um erro ao tentar realizar a criação da postagem:"})}))))
  
(defn notfound-route []
  (route/not-found {:status 404
              :header {"Content-Type" "application/json"}
              :body (generate-string {:msg "Route not found!"})}))
   
