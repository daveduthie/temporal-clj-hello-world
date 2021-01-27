(ns daveduthie.temporal.initiate-transfer
  (:require [daveduthie.temporal.workflow])
  (:import daveduthie.temporal.workflow.MoneyTransferWorkflow
           [io.temporal.client WorkflowClient WorkflowOptions]
           io.temporal.serviceclient.WorkflowServiceStubs
           io.temporal.workflow.Functions$Func4
           java.util.UUID))

;; -----------------------------------------------------------------------------
;; Initiate workflow

(defn random-uuid []
  (str (UUID/randomUUID)))

(defn runit
  []
  (let [service      (WorkflowServiceStubs/newInstance)
        options      (.. (WorkflowOptions/newBuilder)
                         (setTaskQueue "MONEY_TRANSFER_TASK_QUEUE")
                         (setWorkflowId (random-uuid))
                         build)
        client       (WorkflowClient/newInstance service)
        workflow     (.newWorkflowStub client MoneyTransferWorkflow options)
        reference-id (random-uuid)
        from-account "001-001"
        to-account   "002-002"
        amount       180.74
        we           (WorkflowClient/start
                      (reify
                       Functions$Func4
                         (apply [this from to reference amt]
                           (.transfer workflow from to reference amt)))
                      from-account
                      to-account
                      reference-id
                      amount)]
    {:wf-id (.getWorkflowId we) :run-id (.getRunId we)}))

(comment
  (runit)
  )
