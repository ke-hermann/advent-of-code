(ns year-2022.day13
  (:require [clojure.string :as s]))

(def input (-> (slurp "resources/2022/day13.txt")
               (s/split #"\n\n")
               (as-> xs (map #(map read-string (s/split % #"\n")) xs))))

(defn comp-packets [l, r]
  (cond
    (and (number? l) (number? r)) (compare l r)
    (and (number? l) (sequential? r)) (comp-packets [l] r)
    (and (number? r) (sequential? l)) (comp-packets l [r])
    
    (and (sequential? r) (sequential? l))
    (loop [xs (map comp-packets l r)]
      (condp = (first xs)
        nil (comp-packets (count l) (count r))
        -1 -1
        1 1
        0 (recur (rest xs))))))

(defn part-1 []
  (->> (map vector (range 1 (inc (count input))) input)
       (filter (fn [[k v]] (= -1 (apply comp-packets v))))
       (map first)
       (reduce +)))

(defn part-2 []
  (let [new-input (conj (apply concat input) [[2]] [[6]])
        sorted (sort comp-packets new-input)]
    (* (inc (.indexOf sorted [[2]]))
       (inc (.indexOf sorted [[6]])))))
