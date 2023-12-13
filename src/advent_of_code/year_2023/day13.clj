(ns advent-of-code.year-2023.day13
  (:require [clojure.string :as str]))


(def input (-> (slurp "resources/2023/day13.txt")
               (str/split #"\r\n\r\n")
               (as-> xs (map str/split-lines xs))))


(defn transpose [xs] (mapv str/join (apply mapv vector xs)))

(defn row-diff [[xs ys]]
  (reduce (fn [acc [a b]] (+ acc (if (= a b) 0 1))) 0 (map vector xs ys)))

(defn reflection? [[left right]]
  (let [i (min (count left) (count right))
        xs (reverse (take-last i left))
        ys (take i right)]
    (= 1 (reduce + (map row-diff (map vector xs ys))))))

(defn split [mirrors i]
  [(take i mirrors) (drop i mirrors)])

(defn search [mirrors]
  (let [mirrors* (transpose mirrors)
        xs (range 1 (count mirrors))
        ys (range 1 (count mirrors*))
        horizontal (filter #(reflection? (split mirrors %)) xs)
        vertical  (filter #(reflection? (split mirrors* %)) ys)]
    (cond
      (not-empty horizontal) (* 100 (first horizontal))
      (not-empty vertical) (first vertical)
      :else 0)))

(defn perms [mirrors]
  (let [xs (mapv #(vec (seq %)) mirrors)]
    (for [x (range (count xs))
          y (range (count (nth xs 0)))]
      (->> (assoc-in xs [x y] (if (= \# (get-in xs [x y])) \. \#))
           (mapv str/join)))))

(defn new-score [mirrors]
  (let [xs (map search (perms mirrors))]
    xs))

(defn part-1 []
  (reduce + (map search input)))
