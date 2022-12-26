(ns year-2020.day11
  (:require [clojure.string :as st]
            [utils :refer [adjacent index2d]]))



(def seats-start (->> (slurp "resources/2020/day11.txt")
                      (st/split-lines)
                      (index2d)))

(defn diag [[x y] [i j]]
  (= (Math/abs (- x i)) (Math/abs (- y j))))

(defn left [m [x y]]
  (filter (fn [[i j]] (and (= x i) (< j y))) (keys m)))

(defn right [m [x y]]
  (filter (fn [[i j]] (and (= x i) (> j y))) (keys m)))

(defn up [m [x y]]
  (filter (fn [[i j]] (and (= y j) (< i x))) (keys m)))

(defn down [m [x y]]
  (filter (fn [[i j]] (and (= y j) (> i x))) (keys m)))

(defn left-up [m [x y]]
  (filter (fn [[i j]] (and (diag [x y] [i j]) (< i x) (< j y))) (keys m)))

(defn left-down [m [x y]]
  (filter (fn [[i j]] (and (diag [x y] [i j]) (> i x) (< j y))) (keys m)))

(defn right-up [m [x y]]
  (filter (fn [[i j]] (and (diag [x y] [i j]) (< i x) (> j y))) (keys m)))

(defn right-down [m [x y]]
  (filter (fn [[i j]] (and (diag [x y] [i j]) (> i x) (> j y))) (keys m)))

(defn adjacent-p2 [m seat]
  (->> [(some #(= (m %) \#) (left m seat))
        (some #(= (m %) \#) (right m seat))
        (some #(= (m %) \#) (up m seat))
        (some #(= (m %) \#) (down m seat))
        (some #(= (m %) \#) (left-up m seat))
        (some #(= (m %) \#) (right-up m seat))
        (some #(= (m %) \#) (left-down m seat))
        (some #(= (m %) \#) (right-down m seat))
        ]
       (filter true?)
       (count)))

(defn update-seat [m seat]
  (let [i (adjacent-p2 m seat)]
    (cond
      (and (= i 0) (= (m seat) \L)) [seat \#]
      (and (>= i 5) (= (m seat) \#)) [seat \L]
      :else [seat (m seat)])))

(defn update-all-seats [m]
  (->> (map (partial update-seat m) (keys m))
       (into {})))

(defn solve []
  (loop [i 0 state seats-start old {}]
    (if (= state old)
      (count (filter #{\#} (vals state)))
      (recur (inc i) (update-all-seats state) state))))
