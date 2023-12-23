(ns aoc-clojure.2015.day9
  (:require [clojure.string :as s]))

(defn parse [line]
  (let [[start _ end _ d] (s/split line #"\s+")]
    [[[start end] (Integer/parseInt d)]
     [[end start] (Integer/parseInt d)]]))

(def distances (->> (slurp "resources/2015/day9.txt")
                    (s/split-lines)
                    (mapcat parse)
                    (into {})))

(defn permutations [s]
  (lazy-seq
   (if (next s)
     (for [head s
           tail (permutations (disj s head))]
       (cons head tail))
     [s])))

(defn route-cost [route]
  (let [ps (partition 2 1 route)]
    (->> (map #(distances (vec %)) ps)
         (reduce +))))

(defn part-1 []
  (let [cities (set (mapcat first distances))
        perms (permutations cities)]
    (->> (map route-cost perms)
         (apply max))))
