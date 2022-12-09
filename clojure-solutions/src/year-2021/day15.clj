(ns advent-of-code.2021.day15
  (:require [clojure.string :as s]
            [advent-of-code.utils :as utils]))


(def grid (->> (slurp "resources/2021/day15.txt")
               (s/split-lines)
               (utils/index2d)))

(defn wrap [x]
  (if (<= x 9) x (mod x 9)))

(def grid-2
  (let [m 100]
    (->> (for [x (range m)
               y (range m)
               i (range 5)
               j (range 5)]
           (let [v (grid [x y])]
             [[(+ x (* i m)) (+ y (* j m))] (wrap (+ v i j))]))
         (into {}))))

(defn neighbors [[x y] unvisited]
  (filter unvisited [[(inc x) y] [x (inc y)] [(dec x) y] [x (dec y)]]))

(defn tentative [g candidate current]
  (let [dist (+ current (grid candidate))]
    (if (> (g candidate) dist)
      (assoc g candidate dist)
      g)))

(defn dijkstra [start target]
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
