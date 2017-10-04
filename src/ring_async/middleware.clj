(ns ring-async.middleware)

(defn wrap-exception [handler]
  (fn
    ([request]
     (try
       (handler request)
       (catch Exception e
         (prn (.getMessage e))
         {:status 500 :body "Server error"})))
    ([request respond raise]
     (try
       (handler request respond raise)
       (catch Exception e
         (prn (.getMessage e))
         (respond {:status 500 :body "Server error"}))))))
