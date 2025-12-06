(ns advent-of-code.year-2025.day6
  (:require [clojure.string :as str]))

(defn transpose [m]
  (apply map vector m))

(def data (str/split-lines (slurp "resources/2025/day6.txt")))

(defn p2-parse []
  (let [l (count (first data))
        cols (->> (map (fn [i] [i (map #(nth % i) data)]) (range l))
                  (filter (fn [[i xs]] (every? #(= % \space) xs)))
                  (map first))]
    cols))

(defn process [xs]
  (let [op (eval (read-string (last xs)))]
    (reduce op (map #(Integer/parseInt %) (butlast xs)))))

(defn part-1 []
  (->> (map #(str/split (str/triml %) #"\s+") data)
       (transpose)
       (map process)
       (reduce +)))

