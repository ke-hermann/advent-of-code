(ns advent-of-code.2021.day1
  (:require [clojure.string :as s]))

(def input (->> (slurp "./resources/2021/day1.txt")
                (s/split-lines)
                (map #(Integer/parseInt %))))

(defn part-1 []
  (->> input
       (partition 2 1)
       (filter (fn [[x y]] (> y x)))
       (count)))

(defn part-2 []
  (->> input
       (partition 3 1)
       (map (partial reduce +))
       (partition 2 1)
       (filter (fn [[x y]] (> y x)))
       (count)))
