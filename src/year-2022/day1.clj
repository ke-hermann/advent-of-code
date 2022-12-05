(ns aoc-clojure.year-2022.day1
  (:require [clojure.string :as s]))


(def input (->> (s/split (slurp "./resources/2022/day1.txt") #"\n\n")
                (map #(s/split % #"\s+"))
                (map #(map (fn [x] (Integer/parseInt x)) %))))

(defn sum [xs] (reduce + xs))

(defn part-1 []
  (apply max (map sum input)))

(defn part-2 []
  (->> (map sum input)
       ((comp (partial take 3) (sort-)))
       (sum)))
