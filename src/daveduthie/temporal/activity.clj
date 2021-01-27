(ns daveduthie.temporal.activity
  (:import (io.temporal.activity ActivityInterface)))

;; -----------------------------------------------------------------------------
;; Activity

(definterface ^{ActivityInterface true} AccountActivity
  (deposit [account-id reference-id amount])
  (withdraw [account-id reference-id amount]))

(comment
  (seq (.getAnnotations AccountActivity)))

(deftype AccountActivityImpl []
  AccountActivity
  (deposit [_this account-id reference-id amount]
    {:deposit [account-id reference-id amount]})
  (withdraw [_this account-id reference-id amount]
    {:withdraw [ account-id reference-id amount]}))

  (comment
    (.withdraw (->AccountActivityImpl) "x" "y" 123.45))
