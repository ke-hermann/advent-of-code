(ns aoc-clojure.2018.day2
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2018/day2.txt")
                (s/split-lines)))

(defn has-two? [xs]
  (-> (frequencies xs)
      (vals)
      (.contains 2)))

(defn has-three? [xs]
  (-> (frequencies xs)
      (vals)
      (.contains 3)))

(defn count-by [f xs]
  (count (filter f xs)))

(defn difference [xs ys]
  (->> (map vector xs ys)
       (remove (fn [[x y]] (= x y)))
       (count)))

(defn part-1 []
  (let [i (count-by has-two? input)
        j (count-by has-three? input)]
    (* i j)))

(defn part-2 []
  (->> (for [xs input ys input]
         [xs ys (difference xs ys)])
       (filter (fn [[_ _ i]] (= i 1)))))
