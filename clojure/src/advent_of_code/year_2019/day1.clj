(ns advent-of-code.year-2019.day1
  (:require [clojure.string :as str]))

(def input (->> (slurp "resources/2019/day1.txt")
                (str/split-lines)
                (map #(Integer/parseInt %))))

(defn fuel-cost [i]
  (- (quot i 3) 2))

(defn fuel-rec [i]
  (let [i* (fuel-cost i)]
    (if (<= i* 0) 0 (+ i* (fuel-rec i*)))))

(defn part-1 []
  (reduce + (map fuel-cost input)))

(defn part-2 []
  (reduce + (map fuel-rec input)))
