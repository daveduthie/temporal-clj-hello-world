(ns daveduthie.temporal-clj
  (:require [daveduthie.temporal.worker :as worker]
            [daveduthie.temporal.initiate-transfer :as initiate-transfer]
            [daveduthie.temporal.common :as common])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (worker/start-worker!)
  (initiate-transfer/initiate-transfer!
   {:from "001-001", :to "002-002", :ref-id (common/random-uuid), :amt 180.74}))
