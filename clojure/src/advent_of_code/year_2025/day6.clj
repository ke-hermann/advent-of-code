(ns advent-of-code.year-2025.day6
  (:require [clojure.string :as str]))

(defn transpose [m]
  (apply map vector m))

(def data (str/split-lines (slurp "resources/2025/day6.txt")))

(defn process [xs]
  (let [op (eval (read-string (last xs)))]
    (reduce op (map #(Integer/parseInt %) (butlast xs)))))

(defn part-1 []
  (reduce + (map process (transpose (map #(str/split (str/triml %) #"\s+") data)))))

(defn part-2 []
  (let [l (count (first data))
        cols (->> (map (fn [i] [i (map #(nth % i) data)]) (range l))
                  (filter (fn [[i xs]] (every? #(= % \space) xs)))
                  (map first))]
    (->> (for [[a b] (partition 2 1 (concat [0] cols [l]))]
           (let [xs (for [row data] (subvec (vec row) a b))
                 hd (map read-string (remove str/blank? (map #(str/join %) (transpose (butlast xs)))))
                 tl (eval (read-string (str (first (remove #{\space} (last xs))))))]
             (reduce tl hd)
             ))
         (reduce +))))
