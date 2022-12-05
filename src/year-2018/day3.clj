(ns aoc-clojure.2018.day3
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2018/day3.txt")
                (s/split-lines)
                (map #(map read-string (re-seq #"\d+" %)))))

(defn inc-maybe [m k]
  (if (contains? m k)
    (update m k inc)
    (assoc m k 1)))

(defn coords [_ x y w h]
  (for [i (range w)
        j (range h)]
    [(+ x i) (+ y j)]))

(defn update-fabric [fb inst]
  (let [points (apply coords inst)]
    (reduce inc-maybe fb points)))

(defn no-overlap? [fabric inst]
  (let [p (apply coords inst)]
    (every? #(= 1 (fabric %)) p)))

(defn part-1 []
  (->> (reduce update-fabric {} input)
       (vals)
       (filter #(>= % 2))
       (count)))

(defn part-2 []
  (let [result (reduce update-fabric {} input)]
    (filter (partial no-overlap? result) input)))
