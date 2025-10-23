(ns corenet-backend.routes
  (:require
   [cheshire.core :refer :all]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [corenet-backend.services.postCreate :refer :all]
   [corenet-backend.db.db :refer :all]))


(defn home-route []
  (GET "/" []
       {:status 200
        :headers {"Content-Type" "application/json"}
        :body (generate-string {:msg "Welcome To CoreNet API V1"})}))


(defn create-post-route []
  (POST "/create-post" [titulo conteudo]
    (if (and titulo conteudo)
      (if (and (>= (count titulo) 6) (>= (count conteudo) 6))
        (try
          (create-post titulo conteudo)
          (catch Exception e
            {:status 500
             :headers {"Content-Type" "application/json"}
             :body (generate-string
                     {:msg "Ocorreu um erro ao tentar realizar a criação da postagem."})}))
        {:status 400
         :headers {"Content-Type" "application/json"}
         :body (generate-string
                 {:msg "Os campos 'título' e 'conteúdo' devem ter no mínimo 6 caracteres."})})
      {:status 400
       :headers {"Content-Type" "application/json"}
       :body (generate-string
               {:msg "Parâmetros 'titulo' e 'conteudo' são obrigatórios."})})))


(defn all-blogs-route []
  (GET "/all-blogs" []
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (generate-string {:blogs(find-all-blogs-json)})}))

  
(defn notfound-route []
(route/not-found {:status 404
                  :headers {"Content-Type" "application/json"}
                  :body (generate-string {:msg "Not Found 404"})}))


