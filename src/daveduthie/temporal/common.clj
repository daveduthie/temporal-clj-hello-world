(ns daveduthie.temporal.common
  (:import java.util.UUID))

(def TASK_QUEUE "DAVEDUTHIE_TEMPORAL_TASK_QUEUE")

(defn random-uuid [] (str (UUID/randomUUID)))
