(ns year-2022.day15
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2022/day15.txt")
                (s/split-lines)
                (map  #(map (fn [x] (Long/parseLong x))
                            (re-seq #"-?\d+" %)))))

(def beacons (set (map #(vec (drop 2 %)) input)))

(defn manhattan [x y i j]
  (+ (abs (- x i)) (abs (- y j))))

(defn eliminated [row [sx sy bx by]]
  (let [m (manhattan sx sy bx by)
        rd (abs (- row sy))
        md (- m rd)]
    (cond
      (< md 0) []
      (= md 0) [sx]
      :else [(- sx md) (+ sx md)])))

(defn in-range? [i xs]
  (some (fn [[x y]] (and (>= i x) (<= i y))) xs))

(defn part-1 []
  (let [t 2000000
        el (remove empty? (map (partial eliminated t) input))
        l (apply min (map first el))
        r (apply max (map second el))
        b (map first (filter (fn [[x y]] (= y t)) beacons))]
    (loop [i l c 0]
      (if (= i r) c
          (recur (inc i) (if (in-range? i el) (inc c) c))))))
