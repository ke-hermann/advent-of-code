(ns aoc-clojure.year-2022.day4
  (:require [clojure.string :as s]
            [clojure.set :as sets]))


(def input (->> (slurp "resources/2022/day4.txt")
                (s/split-lines)
                (map #(map read-string (re-seq #"\d+" %)))))

(defn fully-contains? [[x y i j]]
  (or (and (>= i x) (<= j y))
      (and (>= x i) (<= y j))))

(defn overlaps? [[x y i j]]
  (or (and (<= x j) (>= y i))
      (and (<= i y) (>= j x))))

(defn part-1 []
  (count (filter fully-contains? input)))

(defn part-2 []
  (count (filter overlaps? input)))
