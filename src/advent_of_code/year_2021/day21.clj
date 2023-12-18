(ns advent-of-code.year-2021.day21
  (:require [clojure.string :as str]))


(defn simulate-game []
  (loop [dice (cycle (range 1 101))
         p1-pos 7 p1-score 0 p2-pos 3 p2-score 0 rolls 0]
    (let [p1-draw (reduce + (take 3 dice))
          p2-draw (reduce + (take 3 (drop 3 dice)))
          p1-pos* (mod (+ p1-pos p1-draw) 10)
          p2-pos* (mod (+ p2-pos p2-draw) 10)
          p1-score* (inc (+ p1-score p1-pos*))
          p2-score* (inc (+ p2-score p2-pos*))]
      (cond
        (>= p1-score* 1000) [(+ 3 rolls) p2-score]
        (>= p2-score* 1000) [(+ 6 rolls) p1-score]
        :else
        (recur (drop 6 dice)
               p1-pos*
               p1-score*
               p2-pos*
               p2-score*
               (+ rolls 6))))))
