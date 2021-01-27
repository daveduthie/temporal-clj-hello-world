(ns daveduthie.temporal.activity
  (:import (io.temporal.activity ActivityInterface)))

;; -----------------------------------------------------------------------------
;; Activity

(definterface ^{ActivityInterface true} AccountActivity
  (deposit [tx-details])
  (withdraw [tx-details]))

(comment
  (seq (.getAnnotations AccountActivity)))

(deftype AccountActivityImpl []
  AccountActivity
  (deposit [_this tx-details]
    {:deposit tx-details})
  (withdraw [_this tx-details]
    {:withdraw tx-details}))

(comment
  (.withdraw (->AccountActivityImpl)
             {:from "x", :to "y", :ref-id "1234", :amt 123.45}))
