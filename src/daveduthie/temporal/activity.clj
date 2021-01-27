(ns daveduthie.temporal.activity
  (:import (io.temporal.activity ActivityInterface)))

;; -----------------------------------------------------------------------------
;; Activity

(definterface ^{ActivityInterface true} AccountActivity
  (deposit [^String account-id ^String reference-id ^double amount])
  (withdraw [^String account-id ^String reference-id ^double amount]))

(comment
  (seq (.getAnnotations AccountActivity)))

(deftype AccountActivityImpl []
  AccountActivity
  (deposit [this account-id reference-id amount]
    (prn :deposit account-id reference-id amount))
  (withdraw [this account-id reference-id amount]
    (prn :withdraw account-id reference-id amount)))

(comment
  (.withdraw (->AccountActivityImpl) "x" "y" 123.45))
