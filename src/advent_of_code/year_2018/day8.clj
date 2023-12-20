(ns advent-of-code.year-2018.day8
  (:require [clojure.string :as str]))

(def nodes [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])


(defn parse [node]
  (let [[children metadata] (take 2 node)
        more (subvec node 2 (- (count node) metadata))]
    (if (zero? children)
      (take (+ 2  metadata) node)
      (let [c (parse (vec more))]
        (parse (vec (drop (count c) more)))))))
