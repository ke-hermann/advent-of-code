(ns advent-of-code.year-2021.day20
  (:require [clojure.string :as str]
            [utils :refer [index2d pprint-map]]))

(def algorithm
  (str/trim (slurp "resources/2021/day20algo.txt")))

(def input (->> (slurp "resources/2021/day20input.txt")
                (str/split-lines)
                (index2d)))

(defn decimal [xs]
  (->> (map (fn [c] (if (= c "#") 1 0)) xs)
       (str/join)
       ((fn [s] (Long/parseLong s 2)))))

(defn enhance [image [x y]]
  (let [xs [(get image [(dec x) (dec y)] ".")
            (get image [(dec x) y] ".")
            (get image [(dec x) (inc y)] ".")
            (get image [x (dec y)] ".")
            (get image [x y])
            (get image [x (inc y)] ".")
            (get image [(inc x) (dec y)] ".")
            (get image [(inc x) y] ".")
            (get image [(inc x) (inc y)] ".")]]
    (str (nth algorithm (decimal xs)))))

(defn update-image [image]
  (let [min-x (apply min (map first (keys image)))
        max-x (apply max (map first (keys image)))
        min-y (apply min (map second (keys image)))
        max-y (apply max (map second (keys image)))]
    (->> (for [x (range -150 150)
               y (range -150 150)]
           [[x y] (enhance image [x y])])
         (into {}))))

(defn part-1 []
  (->> input
       (update-image)
       (update-image)
       (vals)
       (filter #(= % "#"))
       (count)))
