(ns advent-of-code.year-2023.day21
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [index2d neighbors pprint-map]]))


(def garden (->> (slurp "resources/2023/day21.txt")
                 (str/split-lines)
                 (index2d)))

(def width (apply max (map first (keys garden))))
(def height (apply max (map second (keys garden))))

(defn garden? [p]
  (= (garden p) "."))

(defn garden-p2? [[x y]]
  (= (garden [(mod x (inc width)) (mod y (inc height))]) "."))

(defn flood-fill []
  (loop [xs [[5 5]]  i 0]
    (let [nbs (mapcat neighbors xs)
          xs* (filter garden-p2? nbs)]
      (if (= i 100)
        (count (set xs))
        (recur (set xs*) (inc i))))))
