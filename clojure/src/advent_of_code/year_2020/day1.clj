(ns aoc-clojure.year-2020.day1
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2020/day1.txt")
                (s/split-lines)
                (map #(Integer/parseInt %))))


(defn solution []
  (->> (for [x input j input z input]
         (when (and (not= x j z)
                    (= 2020 (+ x j z)))
           (* x j z)))
       (remove nil?)))
