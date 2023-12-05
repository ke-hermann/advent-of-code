(ns advent-of-code.2017.day2
  (:require [clojure.string :as s]))

(defn vec-to-int
  [vector]
  (map #(Integer/parseInt %) vector))

(defn row-diff
  [row]
  (let [lowest  (apply min row)
        highest (apply max row)]
    (- highest lowest)))

(defn unique-pairs
  [xs]
  (->> (for [x xs y xs]
         [x y])
       (remove (fn [[x y]] (= x y)))))

(defn find-even-divisor [xs]
  (->> (unique-pairs xs)
       (filter (fn [[x y]] (zero? (mod x y))))
       (first)))

(def input (->> (slurp "./resources/2017/day2.txt")
                (s/split-lines)
                (map (fn [line] (s/split line #"\s+")))
                (map vec-to-int)))

(defn part-1 []
  (->> (map row-diff input)
       (reduce +)))

(defn part-2 []
  ( ->> input
   (map find-even-divisor)
   (map (fn [[x y]] (/ x y)))
   (reduce +)))
