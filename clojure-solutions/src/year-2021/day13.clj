(ns advent-of-code.2021.day13
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2021/day13.txt")
                (s/split-lines)
                (map #(s/split % #"\,"))
                (map #(map (fn [x] (Integer/parseInt x)) %))))


(defn fold-up [paper line]
  (let [south (set (filter (fn [[x y]] (> y line)) paper))]
    (->> (for [[x y] paper]
           (if (contains? south [x y])
             [x (- line (- y line))]
             [x y]))
         (set))))

(defn fold-left [paper line]
  (let [east (set (filter (fn [[x y]] (> x line)) paper))]
    (->> (for [[x y] paper]
           (if (contains? east [x y])
             [(- line (- x line)) y]
             [x y]))
         (set))))

(defn pprint [paper]
  (let [x-vals (map first paper)
        first-row (s/join (map #(if (contains? paper [% 0]) "#" " ") (range (inc (apply max x-vals)))))
        second-row (s/join (map #(if (contains? paper [% 1]) "#" " ") (range (inc (apply max x-vals)))))
        third-row (s/join (map #(if (contains? paper [% 2]) "#" " ") (range (inc (apply max x-vals)))))
        fourth-row (s/join (map #(if (contains? paper [% 3]) "#" " ") (range (inc (apply max x-vals)))))
        fifth-row (s/join (map #(if (contains? paper [% 4]) "#" " ") (range (inc (apply max x-vals)))))
        sixth-row (s/join (map #(if (contains? paper [% 5]) "#" " ") (range (inc (apply max x-vals)))))
        ]
    (println first-row)
    (println second-row)
    (println third-row)
    (println fourth-row)
    (println fifth-row)
    (println sixth-row)))

(defn solution []
  (-> input
      (fold-left 655)
      (fold-up 447)
      (fold-left 327)
      (fold-up 223)
      (fold-left 163)
      (fold-up 111)
      (fold-left 81)
      (fold-up 55)
      (fold-left 40)
      (fold-up 27)
      (fold-up 13)
      (fold-up 6)
      (pprint)))
