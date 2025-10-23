(ns corenet-backend.db.db
  (:require
   [datomic.api :as d]
   [cheshire.core :refer :all]))

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
    :db/doc         "conteudo da postagem"}])

@(d/transact conn schema)

(defn transactor [conect array]
  (d/transact conect array))

(def all-blogs '[:find ?title ?content
                    :where [?e :blog/titulo ?title]
                           [?e :blog/conteudo ?content]])


(defn find-all-blogs []
  (try
    (def db (d/db conn))
    (d/q all-blogs db)
    (catch Exception e
      "Ocorreu um erro ao consultar os Blogs")))


(defn find-all-blogs-json []
  (try
    (map (fn [[title content]]
           {:titulo title
              :conteudo content})
         (find-all-blogs))
    (catch Exception e
      "Ocorreu um erro ao tentar trazer os Blogs")))

;;(find-all-blogs-json)
;;(find-all-blogs)
