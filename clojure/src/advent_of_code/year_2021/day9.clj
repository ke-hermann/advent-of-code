(ns advent-of-code.2021.day9
  (:require [clojure.string :as s]))

(def heightmap (s/split-lines (slurp "resources/2021/day9.txt")))

(defn at [matrix x y]
  (get (get matrix x) y))

(defn to-int [c]
  (Integer/parseInt (str c)))

(defn is-up? [p]
  (let [v (apply at heightmap p)]
    (if (nil? v)
      false 
      (not (= 9 (to-int v))))))

(defn neighbors [[x y]]
  (for [ [a b] [[-1 0] [0 -1] [0 1] [1 0]]]
    (let [[i j] [(+ a x) (+ y b)]]
      [i j])))

(defn lowpoint? [[x y]]
  (let [v (to-int (at heightmap x y))
        adjacent (map to-int (remove nil? (map (partial apply at heightmap) (neighbors [x y]))))]
    (every? #(> % v) adjacent)))

(defn lowpoints []
  (->> (for [x (range (count heightmap))
              y (range (count (first heightmap)))]
         [x y])
       (filter lowpoint?)))

(defn risk-level []
  (->> (lowpoints)
       (map (partial apply at heightmap))
       (map to-int)
       (map inc)
       (reduce +)))

(defn traverse-basin [start]
  (loop [pos start seen #{}]
    (let [nb (apply concat (map neighbors pos))
          valid (remove seen (filter is-up? nb))
          seen* (apply conj seen pos)]
      (if (empty? valid)
        seen* 
        (recur valid seen*)))))

(defn three-largest []
  (let [lp (lowpoints)
        basins (map #(traverse-basin [%]) lp)
        sizes (map count basins)]
    (->> (sort sizes)
         (reverse)
         (take 3)
         (reduce *))))

