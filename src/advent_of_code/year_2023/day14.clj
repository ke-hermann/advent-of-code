(ns advent-of-code.year-2023.day14
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [index2d pprint-map]]))


(def start (->> (slurp "resources/2023/day14.txt")
                   (str/split-lines)
                   (index2d)))

(defn find-rocks [platform]
  (->> (filter (fn [[k v]] (= v "O")) platform)
       (map first)))

(def south-edge (inc (apply max (map first (keys start)))))

(def east-edge (inc (apply max (map second (keys start)))))

(defn update-rock-map [platform rocks]
  (let [old (find-rocks platform)
        cleared (reduce #(assoc %1 %2 ".") platform old)]
    (reduce #(assoc %1 %2 "O") cleared rocks)))

(defn north [platform [x y]]
  (let [path (map (fn [i] (platform [i y])) (range x))
        points (take-while #(not= % "#") (reverse path))
        offset (count (filter #{"."} points))]
    [(- x offset) y]))

(defn west [platform [x y]]
  (let [path (map (fn [i] (platform [x i])) (range y))
        points (take-while #(not= % "#") (reverse path))
        offset (count (filter #{"."} points))]
    [x (- y offset)]))

(defn south [platform [x y]]
  (let [path (map (fn [i] (platform [i y])) (range (inc x) south-edge))
        points (take-while #(not= % "#") path)
        offset (count (filter #{"."} points))]
    [(+ x offset) y]))

(defn east [platform [x y]]
  (let [path (map (fn [i] (platform [x i])) (range (inc y) east-edge))
        points (take-while #(not= % "#") path)
        offset (count (filter #{"."} points))]
    [x (+ y offset)]))

(defn move [platform direction]
  (let [rocks (find-rocks platform)
        rocks* (map (partial direction platform) rocks)]
    (update-rock-map platform rocks*)))

(defn simulate-cycle [platform]
  (-> (move platform north)
      (move west)
      (move south)
      (move east)))

(defn rock-weight [platform]
  (->> (map (fn [[x y]] (- south-edge x)) (find-rocks platform))
       (reduce +)))

(defn part-1 []
  (->> (move start north)
       (rock-weight)))

(defn part-2 []
  (loop [i 0 platform start]
    (println [i (rock-weight platform)])
    (if (= i 200)
      (rock-weight platform)
      (recur (inc i) (simulate-cycle platform)))))

(mod (- 1000000000 167) 21)

