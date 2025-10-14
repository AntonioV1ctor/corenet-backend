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
   (POST "/create-post" [titulo conteudo]
     (if (and titulo conteudo)
       (try
         {:status 200
          :headers {"Content-Type" "application/json"}
          :body (generate-string {:msg "Postagem realizada com sucesso!"
                                  :titulo titulo
                                  :conteudo conteudo})}
         (catch Exception e
           {:status 500
            :headers {"Content-Type" "application/json"}
            :body (generate-string {:msg "Ocorreu um erro ao tentar realizar a criação da postagem."})}))
       {:status 400
        :headers {"Content-Type" "application/json"}
        :body (generate-string {:msg "Parâmetros 'titulo' e 'conteudo' são obrigatórios."})})))
  
(defn notfound-route []
(route/not-found {:status 404
                  :headers {"Content-Type" "application/json"}
                  :body (generate-string {:msg "Not Found 404"})}))