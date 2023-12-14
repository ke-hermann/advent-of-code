(ns advent-of-code.year-2023.day14
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [index2d pprint-map]]))


(def start (->> (slurp "resources/2023/day14.txt")
                   (str/split-lines)
                   (index2d)))

(def rocks (->> (filter (fn [[k v]] (= v "O")) start)
                (map first)))

(def south-edge (inc (apply max (map first (keys start)))))

(defn move [[x y]]
  (let [path (map (fn [i] (start [i y])) (range x))
        north (take-while #(not= % "#") (reverse path))
        offset (count (filter #{"."} north))]
    [(- x offset) y]))

(defn part-1 []
  (let [tilted (map move rocks)
        weight (map (fn [[x y]] (- south-edge x)) tilted)]
    (reduce + weight)))
