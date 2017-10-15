(ns ring-async.ws
  (:require [ring.adapter.jetty9 :as jetty]))

(def ws-handler {:on-connect (fn [ws]
                               (println "on-connect" ws))
                 :on-error   (fn [ws e]
                               (println "on-error" e))
                 :on-close   (fn [ws status-code reason]
                               (println "on-close"))
                 :on-text    (fn [ws text]
                               (Thread/sleep 1000)
                               (jetty/send! ws text))})
