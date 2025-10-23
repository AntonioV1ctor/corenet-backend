(ns corenet-backend.services.postCreate
  (:require
   [corenet-backend.db.db :refer :all]
   [cheshire.core :refer :all]))



(defn create-post [titulo conteudo]
  (try
    (transactor conn schema)
    (transactor conn [{:blog/titulo titulo
                       :blog/conteudo conteudo}])
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (generate-string
             {:msg "Postagem realizada com sucesso!"
              :titulo titulo
              :conteudo conteudo})}
    (catch Exception e
      {:status 400
       :headers {"Content-Type" "application/json"}
       :body (generate-string {:msg "Ocorreu um erro ao salvar o título e o conteúdo no banco de dados. Tente novamente!"})})))



;;(create-post "Como fazer um jogo" "Para começar a fazer um jog...")
;;(d/transact conn [{:blog/titulo "TEST12345" :blog/conteudo "TEST112233"}])
