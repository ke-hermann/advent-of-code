(ns advent-of-code.utils
  (:require [clojure.string :as s]))

(defn str-to-ints
  [xs]
  (->> (re-seq  #"\d+" xs)
       (map #(Integer/parseInt %))))

(defn transpose
  [xs]
  (apply map vector xs))

(defn index2d 
  [matrix]
  (->> (for [x (range (count matrix))
             y (range (count (first matrix)))]
         [[x y] (Integer/parseInt (str (get (get matrix x) y )))])
       (into {})))
