(defproject corenet-backend "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :creds :gpg}}
  
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.datomic/datomic-pro "1.0.6711"]
                 [http-kit "2.8.1"]
                 [compojure "1.7.2"]
                 [cheshire "6.1.0"]]
  
  :main ^:skip-aot corenet-backend.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
