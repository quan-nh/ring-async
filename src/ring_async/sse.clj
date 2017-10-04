(ns ring-async.sse
  (:require [clojure.core.async :as async :refer [chan go <! >!]]
            [clojure.data.json :as json]))

(defn- event-stream-message
  "In its basic form, the SSE response should contain a 'data:' line,
   followed by your message,
   followed by two \n characters to end the stream"
  [m]
  (str "data: " (json/write-str m) "\n\n"))

(defn sse-handler [request respond raise]
  (let [ch (chan)]
    (respond {:status 200 :headers {"Content-Type" "text/event-stream"} :body ch})
    (go
      ; send generic 'message' event
      (>! ch (event-stream-message {:msg "First message"}))

      (<! (async/timeout 1000))

      ; send 'userlogon' event
      (>! ch "event: userlogon\n")
      (>! ch (event-stream-message {:username "John123"}))

      (<! (async/timeout 1000))

      ; send 'update' event
      (>! ch "event: update\n")
      (>! ch (event-stream-message {:username "John123" :emotion "happy"}))

      (async/close! ch))))
