(ns corenet-backend.db.db
  (:require
   [datomic.api :as d]
   [cheshire.core :refer :all]))

(def db-uri "datomic:dev://localhost:4334/corenet")
;;(d/delete-database "datomic:dev://localhost:4334/corenet")
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
    :db/doc         "conteudo da postagem"}])

@(d/transact conn schema)

(defn transactor [conect array]
  (d/transact conect array))

(def all-blogs '[:find ?e ?title ?content
    :where
    [?e :blog/titulo ?title]
    [?e :blog/conteudo ?content]])



(defn find-blog-by-id [id]
  (try 
    (let [db (d/db conn)
          e  (d/entity db id)]
      {:id (:db/id e)
       :titulo (:blog/titulo e)
       :conteudo (:blog/conteudo e)})
    (catch Exception e
      "Ocorreu um erro ao consultar o Blog que corresponde ao seguinte ID:"id)))



(defn find-all-blogs []
  (try
    (def db (d/db conn))
    (d/q all-blogs db)
    (catch Exception e
      "Ocorreu um erro ao consultar os Blogs")))


(defn find-all-blogs-json []
  (try
    (mapv (fn [[id title content]]
            {:id id
             :titulo title
             :conteudo content})
          (find-all-blogs))
    (catch Exception e
      {:erro "Ocorreu um erro ao tentar trazer os Blogs"
       :detalhe (.getMessage e)})))



;;(find-blog-by-id "17592186045419")
;;(find-all-blogs-json)
;;(find-all-blogs)
