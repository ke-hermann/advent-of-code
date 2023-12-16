(ns year-2016.day13
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [pprint-map]]))

(def favorite 1364) 

(def goal [39 31])

(def limit 500)

(defn checksum [[x y]]
  (+ (* y y)
     (* 3 y)
     (* 2 y x)
     (* x x)
     x
     favorite))

(defn space? [[x y]]
  (and (>= x 0) (>= y 0) (<= x limit) (<= y limit)
       (->> (Integer/toBinaryString (checksum [x y]))
            (filter #{\1})
            (count)
            (even?))))

(defn neighbors [[x y]]
  (let [xs [[(dec x) y] [(inc x) y] [x (inc y)] [x (dec y)]]
        ys (filter space? xs)]
    ys))

(defn part-1 []
  (loop [queue [[1 1]] visited #{} i -1]
    (cond
        (empty? queue) (throw (Exception. "error, target not in range"))
        (contains? visited goal) i
        :else (recur (mapcat neighbors (remove visited queue))
                     (apply conj visited queue)
                     (inc i)))))

(defn part-2 []
  (loop [queue [[1 1]] visited #{} i 0]
    (cond
        (= i 51) (count visited)
        :else (recur (mapcat neighbors (remove visited queue))
                     (apply conj visited queue)
                     (inc i)))))
