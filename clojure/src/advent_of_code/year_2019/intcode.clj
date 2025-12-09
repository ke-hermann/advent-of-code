(ns advent-of-code.year-2019.intcode
  (:gen-class))

(defn step [{:keys [pos xs] :as vm} ]
  (let [op (xs pos)
        p1 (xs (inc pos))
        p2 (xs (+ 2 pos))
        p3 (xs (+ 3 pos))]
    (case op
      1 {:pos (+ 4 pos) :xs (assoc xs p3 (+ (xs p2) (xs p1)))}
      2 {:pos (+ 4 pos) :xs (assoc xs p3 (* (xs p2) (xs p1)))}
      3 {:pos (+ 2 pos) :xs (assoc xs p1 (read-line))}
      4 {:pos (+ 2 pos) :xs (println (xs p1))}
      99 vm
      (throw (Exception. "invalid op.")))))

(defn run-vm [{:keys [pos xs] :as vm}]
  (let [vm* (step vm)]
    (if (== 99 (xs pos)) (xs 0) (recur vm*))))
