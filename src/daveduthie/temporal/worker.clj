(ns daveduthie.temporal.worker
  (:import daveduthie.temporal.activity.AccountActivityImpl
           daveduthie.temporal.money_transfer.MoneyTransferWorkflowImpl
           io.temporal.client.WorkflowClient
           io.temporal.serviceclient.WorkflowServiceStubs
           io.temporal.worker.WorkerFactory))

(defn runit
  []
  (let [service (WorkflowServiceStubs/newInstance)
        client  (WorkflowClient/newInstance service)
        factory (WorkerFactory/newInstance client)
        worker  (.newWorker factory "MONEY_TRANSFER_TASK_QUEUE")]
    (.registerWorkflowImplementationTypes worker (into-array Class [MoneyTransferWorkflowImpl]))
    (.registerActivitiesImplementations worker (into-array (AccountActivityImpl.)))
    (.start factory)))

(comment
  (runit))
