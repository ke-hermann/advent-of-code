(ns advent-of-code.2021.day3
  (:require [clojure.string :as s]))


(def input (->> (slurp "resources/2021/day3.txt")
                (s/split-lines)))

(def length (count (first input)))

(defn transpose [xs]
  (apply map vector xs))

(defn most-common [xs]
  (let [f (frequencies xs)]
    (if (> (f \0) (f \1)) \0
        \1)))

(defn least-common [xs]
  (let [f (frequencies xs)]
    (if (< (f \1) (f \0)) \1
        \0)))


(defn gamma-rate []
  (->> (map most-common (transpose input))
       (apply str)
       ((fn [s] (Integer/parseInt s 2)))))

(defn epsilon-rate []
  (->> (map least-common (transpose input))
       (apply str)
       ((fn [s] (Integer/parseInt s 2)))))

(defn part-1 []
  (* (gamma-rate) (epsilon-rate)))

(defn oxygen-rating []
  (loop [pos 0 candidates input]
    (let [s (map #(nth % pos) candidates)
          c (most-common s)
          candidates* (filter #(= c (nth % pos)) candidates)]
      (if (<=  (count candidates*) 1)
        (Integer/parseInt (first candidates*) 2)
        (recur (inc pos) candidates*)))))

(defn scrubber-rating []
  (loop [pos 0 candidates input]
    (let [s (map #(nth % pos) candidates)
          c (least-common s)
          candidates* (filter #(= c (nth % pos)) candidates)]
      (if (<=  (count candidates*) 1)
        (Integer/parseInt (first candidates*) 2)
        (recur (inc pos) candidates*)))))

(defn part-2 []
  (* (oxygen-rating) (scrubber-rating)))
