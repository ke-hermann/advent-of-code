(ns advent-of-code.2021.day5
  (:require [clojure.string :as s]
            [advent-of-code.utils :as utils]))


(def input (->> (slurp "resources/2021/day5.txt")
                (s/split-lines)
                (map utils/str-to-ints)))


(defn expand [coords]
  (let [[x1 y1 x2 y2] coords]
    (cond
      (= y1 y2) (for [i (range (min x1 x2) (inc (max x1 x2)))]
                  [i y1])
      (= x1 x2) (for [i (range (min y1 y2) (inc (max y1 y2)))]
                  [x1 i])
      :else (for [i (range (inc (- (max x1 x2) (min x1 x2))))]
              (cond
                (and (> x2 x1) (> y2 y1)) [(+ x1 i) (+ i y1)]
                (and (> x2 x1) (< y2 y1)) [(+ x1 i) (- y1 i)]
                (and (< x2 x1) (> y2 y1)) [(- x1 i) (+ i y1)]
                (and (< x2 x1) (< y2 y1)) [(- x1 i) (- y1 i)])))))

(defn solution []
  (->> (map expand input)
       (apply concat)
       (frequencies)
       (vals)
       (remove #( < % 2))
       (count)))
