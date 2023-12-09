(ns advent-of-code.year-2023.day9
  (:require [clojure.string :as str]))

(def input (->> (slurp "resources/2023/day9.txt")
                (str/split-lines)
                (map (fn [s] (map parse-long (str/split s #"\s+"))))))

(defn diffs [[a b]]
  (- b a))

(defn histories [coll]
  (loop [xs coll result []]
    (if (every? zero? xs) result
        (recur
         (map diffs (partition 2 1 xs))
         (conj result xs)))))

(defn extrapolate [coll]
  (->> (reverse (map last coll))
       (reduce (fn [acc i] (+ i acc)) 0)))

(defn extrapolate-backwards [coll]
  (->> (reverse (map first coll))
       (reduce (fn [acc i] (- i acc)) 0)))

(defn part-1 []
  (->> (map (comp extrapolate histories) input)
       (reduce +)))

(defn part-2 []
  (->> (map (comp extrapolate-backwards histories) input)
       (reduce +)))
