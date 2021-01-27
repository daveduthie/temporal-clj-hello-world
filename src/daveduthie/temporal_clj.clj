(ns daveduthie.temporal-clj
  (:require [daveduthie.temporal.worker :as worker]
            [daveduthie.temporal.initiate-transfer :as initiate-transfer])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& [from to ref-id amt]]
  (worker/start-worker!)
  (initiate-transfer/initiate-transfer!
   {:from from, :to to, :ref-id ref-id, :amt (Double/parseDouble amt)})
  (System/exit 0))

(comment
  (worker/start-worker!)
  (initiate-transfer/initiate-transfer!
   {:from "me", :to "you", :ref-id "2bfcecae-8bfd-4703-8fcc-e4b8b868ac08", :amt 12345.56}))
