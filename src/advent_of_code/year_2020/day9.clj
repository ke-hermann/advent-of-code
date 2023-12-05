(ns year-2020.day9
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2020/day9.txt")
                (s/split-lines)
                (map read-string)
                vec))

(defn pair-sums [xs]
  (->> (for [i (range (count xs))]
         (for [j (range (inc i) (count xs))]
           (+ (nth xs i) (nth xs j))))
       (flatten)))

(def mem-pair-sums (memoize pair-sums))

(def window 25)

(defn part-1 []
  (loop [i 0]
    (let [hd (nth input (+ window i))
          tl (subvec input i (+ i window))
          pairs (mem-pair-sums tl)]
      (if (.contains pairs hd)
        (recur (inc i))
        (nth input (+ window i))))))

(defn part-2 []
  (let [invalid (part-1)]
    (loop [i 2]
      (let [xs (partition i 1 input)
            sums (map (partial reduce +) xs)]
        (if (.contains sums invalid)
          (let [result (first (filter #(= invalid (reduce + %)) xs))]
            (+  (apply min result)
                (apply max result)))
          (recur (inc i)))))))
