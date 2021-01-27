(ns daveduthie.temporal.worker
  (:require [daveduthie.temporal.common :as common]
            [daveduthie.temporal.activity :as activity]
            [daveduthie.temporal.workflow])
  (:import daveduthie.temporal.workflow.MoneyTransferWorkflowImpl
           io.temporal.client.WorkflowClient
           io.temporal.serviceclient.WorkflowServiceStubs
           io.temporal.worker.WorkerFactory))

(defn start-worker!
  []
  (let [service (WorkflowServiceStubs/newInstance)
        client  (WorkflowClient/newInstance service)
        factory (WorkerFactory/newInstance client)
        worker  (.newWorker factory common/TASK_QUEUE)]
    (.registerWorkflowImplementationTypes worker (into-array Class [MoneyTransferWorkflowImpl]))
    (.registerActivitiesImplementations worker (into-array [(activity/->AccountActivityImpl)]))
    (.start factory)))

(comment
  (start-worker!))
