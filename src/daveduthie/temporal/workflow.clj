(ns daveduthie.temporal.workflow
  (:import daveduthie.temporal.activity.AccountActivity
           io.temporal.activity.ActivityOptions
           io.temporal.common.RetryOptions
           io.temporal.workflow.Workflow
           java.time.Duration))

;; -----------------------------------------------------------------------------
;; Workflow

;; TODO(daveduthie): Not sure how to specify @WorkflowMethod on transfer
(definterface ^{io.temporal.workflow.WorkflowInterface true} MoneyTransferWorkflow
  (^{io.temporal.workflow.WorkflowMethod true} transfer [tx-details]))

(deftype MoneyTransferWorkflowImpl []
  MoneyTransferWorkflow
  (transfer [_this tx-details]
    (let [retry-options (.. (RetryOptions/newBuilder)
                            (setInitialInterval (Duration/ofSeconds 1))
                            (setMaximumInterval (Duration/ofSeconds 100))
                            (setBackoffCoefficient 2)
                            (setMaximumAttempts 500)
                            build)
          activity-options (.. (ActivityOptions/newBuilder)
                               (setStartToCloseTimeout (Duration/ofSeconds 5))
                               (setRetryOptions retry-options)
                               build)
          account (Workflow/newActivityStub AccountActivity activity-options)]
      (.withdraw account tx-details)
      (.deposit account tx-details))))
