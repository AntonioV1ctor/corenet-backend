(ns corenet-backend.services.postCreate
  (:require
   [cheshire.core :refer :all]
   [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/corenet")
(d/create-database db-uri)
(def conn (d/connect db-uri))

(def schema
  [{:db/ident       :blog/titulo
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "titulo da postagem"}
   
   {:db/ident       :blog/conteudo
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "titulo da postagem"}])



(defn create-post [titulo conteudo]
  (try
    (d/transact conn schema)
    (d/transact conn[{:blog/titulo titulo
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

(d/q '[:find ])

;;(create-post "TEST12347" "TEST42345")
;;(d/transact conn [{:blog/titulo "TEST12345" :blog/conteudo "TEST112233"}])
