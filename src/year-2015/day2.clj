(ns advent-of-code.2015.day2
  (:require [clojure.string :as s]))

(defn wrapping [x y z]
  (+ (min (* x y) (* x z) (* y z))
     (+ (* 2 x y)
        (* 2 x z)
        (* 2 y z))))

(defn wrapping-2 [xs]
  (let [[x* y* z*] (sort xs)]
    (+ (+ x* x* y* y*)
       (reduce * xs))))

(def input (->> (s/split-lines (slurp "resources/2015/day2.txt"))
                (map #(map read-string (s/split % #"x")))))

(defn part-1 []
  (->> input
       (map (partial apply wrapping))
       (reduce +)))

(defn part-2 []
  (->> input
       (map wrapping-2)
       (reduce +)))
