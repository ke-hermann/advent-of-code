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

(defn merge-pairs [pairs]
  (loop [[x y] (first pairs) result [] q (rest pairs)]
    (let [[i j] (first q)]
      (cond
        (empty? q) (conj result [x y])
        (< y i)  (recur [i j] (conj result [x y]) (rest q))
        (>= y j) (recur [x y] result (rest q))
        (>= y i) (recur [x j] result (rest q))))))

(defn part-1 [row]
  (let [el (remove empty? (map (partial eliminated row) input))
        no (merge-pairs (sort-by first el))]
    no))

(defn part-2 []
  (loop [row 0 results []]
    (if (= row 4000000)
      (filter #(> (count %) 1) results)
      (recur (inc row) (conj results (part-1 row))))))
