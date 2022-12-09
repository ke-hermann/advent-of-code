(ns aoc-clojure.2018.day1
  (:require [clojure.string :as s]))


(def input (->> (slurp "resources/2018/day1.txt")
                (s/split-lines)
                (map #(Integer/parseInt %))))

(defn part-1 []
  (reduce + input))

(defn part-2 []
  (loop [cur 0 [x & xs] (cycle input) seen #{}]
    (if (contains? seen cur)
      cur
      (recur (+ cur x) xs (conj seen cur)))))
