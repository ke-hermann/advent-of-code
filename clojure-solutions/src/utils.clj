(ns utils
  (:require [clojure.string :as s]))

(defn str-to-ints
  [xs]
  (->> (re-seq  #"\d+" xs)
       (map #(Integer/parseInt %))))

(defn transpose
  [xs]
  (apply map vector xs))

(defn index2d 
  [matrix]
  (->> (for [x (range (count matrix))
             y (range (count (first matrix)))]
         [[x y] (get (get matrix x) y )])
       (into {})))

(defn neighbors [[x y] unvisited]
  (filter unvisited [[(inc x) y] [x (inc y)] [(dec x) y] [x (dec y)]]))

(defn tentative [g grid candidate current]
  (let [dist (+ current (grid candidate))]
    (if (> (g candidate) dist)
      (assoc g candidate dist)
      g)))

(defn dijkstra [start target grid]
  (loop [g (-> (into {} (map (fn [k] {k 999999}) (keys grid)))
               (assoc [0 0] 0))
         unvisited (set (keys grid))
         current start]
    (let [nbs (neighbors current unvisited)
          g* (reduce (fn [acc n] (tentative acc n (g current))) g nbs)
          unvisited* (set (remove #{current} unvisited))
          current*   (if (empty? unvisited*) nil
                         (apply min-key #(g %) unvisited*))]
      (if (not (contains? unvisited* target))
        (g target)
        (recur g* unvisited* current*)))))
