(ns daveduthie.temporal.initiate-transfer
  (:require [daveduthie.temporal.common :as common])
  (:import daveduthie.temporal.workflow.MoneyTransferWorkflow
           [io.temporal.client WorkflowClient WorkflowOptions]
           io.temporal.serviceclient.WorkflowServiceStubs
           io.temporal.workflow.Functions$Func1))

;; -----------------------------------------------------------------------------
;; Initiate workflow

(defn new-client
  []
  (WorkflowClient/newInstance (WorkflowServiceStubs/newInstance)))

(defn new-workflow
  [typ]
  (let [client  (new-client)
        options (.. (WorkflowOptions/newBuilder)
                    (setTaskQueue common/TASK_QUEUE)
                    (setWorkflowId (common/random-uuid))
                    build)]
    (.newWorkflowStub client typ options)))

(defn initiate-transfer!
  [tx-details]
  (let [workflow (new-workflow MoneyTransferWorkflow)
        wex      (WorkflowClient/start (reify
                                        Functions$Func1
                                          (apply [this tx-details]
                                            (.transfer workflow tx-details)))
                                       tx-details)]
    {:wf-id (.getWorkflowId wex), :run-id (.getRunId wex)}))
