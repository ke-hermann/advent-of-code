(ns year-2018.day6
  (:require [clojure.string :as str]))

(defn parse-line [s]
  (let [[x y] (str/split s #", ")]
    [(parse-long y) (parse-long x)]))

(defn on-edge? [lx hx ly hy [x y]]
  (or (= lx x) (= hx x) (= ly y) (= hy y)))

(def coords (->> (slurp "resources/2018/day6.txt")
                 (str/split-lines)
                 (map parse-line)))

(def infinites 
  (let [lx (apply min (map first coords))
        hx (apply max (map first coords))
        ly (apply min (map second coords))
        hy (apply max (map second coords))]
    (filter (partial on-edge? lx hx ly hy) coords)))

(defn manhattan [[x y] [i j]]
  (+ (abs (- x i)) (abs (- y j))))

(defn closest [p1]
  (let [dists (map (fn [p2] [p2 (manhattan p1 p2)]) coords)
        xs (map second dists)
        lowest (apply min xs)]
    (if (= 1 ((frequencies xs) lowest))
      (first (apply min-key second dists))
      :none)))

(defn incr [m k]
  (if (m k) (update m k inc) (assoc m k 1)))

(defn total-dist [p]
  (reduce + (map (partial manhattan p) coords)))

(defn safe? [p]
  (< (total-dist p) 10000))

(defn part-1 []
  (let [candidates (for [x (range 47 359) y (range 46 352)] [x y])]
    (-> (reduce (fn [acc p] (incr acc (closest p))) {} candidates)
        (as-> d (apply dissoc d infinites))
        (vals)
        (apply max))))

(defn part-2 []
  (let [candidates (for [x (range 0 500) y (range 0 500)] [x y])]
    (->> (filter safe? candidates)
         (count))))
