(ns year-2022.day9
  (:require [clojure.string :as s]))

(def motions (->> (slurp "resources/2022/day9.txt")
                  (s/split-lines)
                  (map #(s/split % #"\s+"))
                  (mapcat (fn [[x y]] (repeat (Integer/parseInt y) x)))))

(defn move-head [[x y] direction]
  (case direction
    "R" [x (inc y)]
    "U" [(dec x) y]
    "L" [x (dec y)]
    "D" [(inc x) y]))

(defn move-tail [[x y] [i j]]
  (let [xd (- x i)
        yd (- y j)]
    (if (and (<= (abs xd) 1) (<= (abs yd) 1)) [i j]
        [(+ i (Integer/signum xd)) (+ j (Integer/signum yd))])))

(defn move-rope [[head tail] direction]
  (let [head* (move-head head direction)
        tail* (move-tail head* tail)]
    [head* tail*]))

(defn move-multiple [ropes direction]
  (loop [xs ropes pos 0]
    (cond
      (= pos  (count ropes)) xs
      (= pos 0) (recur (assoc xs 0 (move-head (xs 0) direction))
                       (inc pos))
      :else (recur (assoc xs pos (move-tail (xs (dec pos)) (xs pos)))
                   (inc pos)))))

(defn solution [length]
  (loop [ropes (vec (repeat length [0 0]))
         seen #{}
         m motions]
    (if (empty? m) (count (conj seen (last ropes)))
        (recur (move-multiple ropes (first m))
               (conj seen (last ropes))
               (rest m)))))
