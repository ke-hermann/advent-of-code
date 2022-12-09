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
  (let [x-d (- x i)
        y-d (- y j)]
    (cond
      (and (zero? x-d) (= 2 (- y j))) [i (inc j)]
      (and (zero? x-d) (= -2 (- y j))) [i (dec j)]
      (and (zero? y-d) (= 2 (- x i))) [(inc i) j]
      (and (zero? y-d) (= -2 (- x i))) [(dec i) j]
      ;; diagonal and disconnected
      (and (not= x i) (not= y j) (> (+ (abs x-d) (abs y-d)) 2))
      (cond
        (and (> x i) (> y j)) [(inc i) (inc j)]
        (and (< x i) (> y j)) [(dec i) (inc j)]
        (and (> x i) (< y j)) [(inc i) (dec j)]
        (and (< x i) (< y j)) [(dec i) (dec j)])
      
      :else [i j])))

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

(defn part-1 []
  (loop [state [[0 0] [0 0]] seen #{} m motions]
    (if (empty? m)
      (count seen)
      (recur (move-rope state (first m))
             (conj seen (last state))
             (rest m)))))

(defn part-2 []
  (loop [ropes (vec (repeat 10 [0 0]))
         seen #{}
         m motions]
    (if (empty? m) (count (conj seen (last ropes)))
        (recur (move-multiple ropes (first m))
               (conj seen (last ropes))
               (rest m)))))
