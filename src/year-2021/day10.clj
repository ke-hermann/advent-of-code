(ns advent-of-code.2021.day10
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2021/day10.txt")
                (s/split-lines)
                (map s/trim)))

(defn is-open? [c]
  (.contains "([{<" (str c)))

(defn is-valid? [stack c]
  (let [l (last stack)]
    (case c
      \) (= l \()
      \] (= l \[)
      \} (= l \{)
      \> (= l \<))))

(defn score [c]
  (case c
    \) 3
    \] 57
    \} 1197
    \> 25137))

(defn score-2 [c]
  (case c
    \( 1
    \[ 2
    \{ 3
    \< 4))

(defn part-2 [letters]
  (loop [total 0 xs letters] 
    (if (empty? xs)
      total 
      (recur (+ (* total 5) (score-2 (first xs)))
             (rest xs)))))

(defn verify-chunk [xs]
  (loop [stack [] chunk xs]
    (let [[y & ys] chunk]
      (cond
        (empty? chunk) (apply str (reverse stack))
        (is-open? y)   (recur (conj stack y) ys)
        :else
        (if (is-valid? stack y)
          (recur (apply vector (butlast stack)) ys)
          (score y))))))

(defn calc-p2-scores []
  (->> (map verify-chunk input)
       (filter string?)
       (map part-2)
       (sort)))

(defn solution []
  (let [result (calc-p2-scores)
        d (quot (count result) 2)]
    (first (drop d result))))

(part-2 "[({>")