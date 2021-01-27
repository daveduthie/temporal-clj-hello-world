(ns daveduthie.temporal.money-transfer
  (:import daveduthie.temporal.activity.AccountActivity
           io.temporal.activity.ActivityOptions
           io.temporal.common.RetryOptions
           (io.temporal.workflow Workflow WorkflowInterface
                                 WorkflowMethod)
           java.time.Duration))

;; -----------------------------------------------------------------------------
;; Workflow

;; TODO(daveduthie): Not sure how to specify @WorkflowMethod on transfer
(definterface ^{WorkflowInterface true} MoneyTransferWorkflow
  (^{WorkflowMethod true}
   transfer [^String fromAccountId, ^String toAccountId, ^String referenceId, ^double amount]))

(deftype MoneyTransferWorkflowImpl []
  MoneyTransferWorkflow
  (transfer [_this fromAccountId toAccountId referenceId amount]
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
      (.withdraw account fromAccountId referenceId amount)
      (.deposit toAccountId referenceId amount))))
