(defproject ring-async "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-beta2"]
                 [org.clojure/core.async "0.3.443"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-core "1.6.2"]
                 [info.sunng/ring-jetty9-adapter "0.10.2"]
                 [compojure "1.6.0"]]
  :main ^:skip-aot ring-async.core
  :profiles {:uberjar {:aot :all}})
