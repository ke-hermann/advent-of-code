(ns advent-of-code.2021.day4
  (:require [clojure.string :as s])
  (:require [advent-of-code.utils :as utils]))


(def numbers [68,30,65,69,5,78,41,73,55,0,76,98,79,42,37,21,9,34,56,33,64,54,24,43,15,58,61,38,12,20,4,26,87,95,94,89,83,74,97,77,67,40,63,88,19,31,81,80,60,14,18,47,93,57,17,90,84,85,48,6,91,7,86,13,51,53,8,16,23,66,36,39,32,82,72,11,52,28,62,70,59,50,1,46,96,71,35,10,25,22,27,99,29,45,44,3,75,92,49,2])

(def input (-> (slurp "resources/2021/day4.txt")
               (s/split #"\n\n")
               (as-> xs (map s/split-lines xs))
               (as-> xs (map #(map utils/str-to-ints %) xs))))

(defn full-row? [candidates row]
  (every? #(.contains candidates %) row))

(defn winning? [candidates xs]
  (let [xs* (apply map vector xs)]
    (or (some (partial full-row? candidates) xs)
        (some (partial full-row? candidates) xs*))))

(defn score [board seen]
  (let [xs (flatten board)
        sum (reduce + (remove (set seen) xs))
        result     (* (first seen) sum)]
    result))

(defn play []
  (loop [candidates (take 5 numbers)
         tail (drop 5 numbers)
         unsolved input
         last-winner nil]
    (let [winners (filter (partial winning? candidates) unsolved)]
      (if (empty? unsolved)
        (score last-winner (rest candidates))
        (recur (conj candidates (first tail))
               (rest tail)
               (remove (set winners) unsolved)
               (first winners))))))
