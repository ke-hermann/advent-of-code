(ns advent-of-code.2017.day7
  (:require [clojure.string :as s]))

(defn foo []
  (loop [x 0]
    (if (>= x 10)
      "done"
      (recur #break (inc x)))))
