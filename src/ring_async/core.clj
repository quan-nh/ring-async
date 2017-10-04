(ns ring-async.core
  (:gen-class)
  (:require [clojure.core.async :as async :refer [go <!]]
            [clojure.java.io :as io]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring-async.middleware :refer [wrap-exception]]
            [ring-async.sse :refer [sse-handler]]
            [ring.core.protocols :refer [StreamableResponseBody]]
            [ring.middleware.resource :refer [wrap-resource]])
  (:import (clojure.core.async.impl.channels ManyToManyChannel)))

(extend-type ManyToManyChannel
  StreamableResponseBody
  (write-body-to-stream [channel response output-stream]
    (go (with-open [writer (io/writer output-stream)]
          (loop []
            (when-let [msg (<! channel)]
              (doto writer (.write msg) (.flush))
              (recur)))))))

(defroutes app-routes
           (GET "/sse" [] sse-handler)
           (GET "/ex" [] (throw (Exception. "bad")))
           (route/not-found ""))

(def app
  (-> app-routes
      (wrap-resource "public")
      (wrap-exception)))

(defn -main [& args]
  (run-jetty app {:port 3000 :async? true}))
