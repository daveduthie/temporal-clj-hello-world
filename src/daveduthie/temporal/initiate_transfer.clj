(ns daveduthie.temporal.initiate-transfer
  (:import daveduthie.temporal.money_transfer.MoneyTransferWorkflow
           [io.temporal.client WorkflowClient WorkflowOptions]
           io.temporal.serviceclient.WorkflowServiceStubs
           io.temporal.workflow.Functions$Func4
           java.util.UUID))

;; -----------------------------------------------------------------------------
;; Initiate workflow

(defn runit
  []
  (let [service      (WorkflowServiceStubs/newInstance)
        options      (.. (WorkflowOptions/newBuilder)
                         (setTaskQueue "MONEY_TRANSFER_TASK_QUEUE")
                         (setWorkflowId "money-transfer-workflow2")
                         build)
        client       (WorkflowClient/newInstance service)
        workflow     (.newWorkflowStub client MoneyTransferWorkflow options)
        reference-id (str (UUID/randomUUID))
        from-account "001-001"
        to-account   "002-002"
        amount       18.74
        we (WorkflowClient/start
            (reify Functions$Func4 (apply [this from to reference amt]
                                     (.transfer workflow from to reference amt)))
            from-account to-account reference-id amount)
        ;; ; FIXME how to translate workflow::transfer
       ]
    (prn :tf amount from-account to-account :processing)
    (prn :wf-id (.getWorkflowId we) :run-id (.getRunId we))
  ))

(comment
  (runit))
