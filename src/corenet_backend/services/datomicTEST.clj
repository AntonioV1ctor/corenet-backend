(ns corenet_backend.services.datomicTEST
  (:require [datomic.api :as d]))


;;(def db-uri "datomic:dev://localhost:4334/hello")

;;(d/create-database db-uri)

;;(def conn (d/connect db-uri))

;;(def schema
;;  [{:db/ident       :pessoa/nome
;;    :db/valueType   :db.type/string
;;    :db/cardinality :db.cardinality/one
;;    :db/doc         "Nome de uma pessoa"}])

;;@(d/transact conn schema)

;;@(d/transact conn [{:pessoa/nome "Ada LovelaceE"}])

;;(defn listar-nomes-simples []
;;  (map first
;;       (d/q '[:find ?nome
;;              :where [?e :pessoa/nome ?nome]]
;;            (d/db conn))))

;;(println "Nomes no banco:" (listar-nomes-simples))
