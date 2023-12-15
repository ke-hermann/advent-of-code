(ns year-2016.day13
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [pprint-map]]))

(def favorite 1364)

(def goal [31 39])

(defn checksum [[y x]]
  (+ (* x x)
     (* 3 x)
     (* 2 x y)
     (* y y)
     y
     favorite))

(defn space? [[y x]]
  (and (>= x 0) (>= y 0)
       (->> (Integer/toBinaryString (checksum [y x]))
            (filter #{\1})
            (count)
            (even?))))

(def space?-mem (memoize space?))

(defn neighbors [[y x]]
  (let [xs [[(dec x) y] [(inc x) y] [x (inc y)] [x (dec y)]]
        ys (filter space?-mem xs)]
    ys))

(def neighbors-mem (memoize neighbors))

(defn part-1 []
  (loop [queue [[1 1]] visited #{} i 1]
    (if (contains? visited goal)
      i
      (recur (mapcat neighbors-mem (remove visited queue))
             (apply conj visited queue)
             (inc i)))))
