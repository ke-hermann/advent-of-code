(ns advent-of-code.year-2019.day2
  (:require [clojure.string :as str]))

(defn step [{:keys [pos xs] :as vm} ]
  (let [op (xs pos)
        p1 (xs (inc pos))
        p2 (xs (+ 2 pos))
        p3 (xs (+ 3 pos))]
    (case op
      1 {:pos (+ 4 pos) :xs (assoc xs p3 (+ (xs p2) (xs p1)))}
      2 {:pos (+ 4 pos) :xs (assoc xs p3 (* (xs p2) (xs p1)))}
      99 vm
      (throw (Exception. "invalid op.")))))

(defn run-vm [{:keys [pos xs] :as vm}]
  (let [vm* (step vm)]
    (if (== 99 (xs pos)) (xs 0) (recur vm*))))

(def input {:pos 0 :xs [1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,9,19,23,2,23,10,27,1,27,5,31,1,31,6,35,1,6,35,39,2,39,13,43,1,9,43,47,2,9,47,51,1,51,6,55,2,55,10,59,1,59,5,63,2,10,63,67,2,9,67,71,1,71,5,75,2,10,75,79,1,79,6,83,2,10,83,87,1,5,87,91,2,9,91,95,1,95,5,99,1,99,2,103,1,103,13,0,99,2,14,0,0]})

(defn part-1 []
  (run-vm input))
