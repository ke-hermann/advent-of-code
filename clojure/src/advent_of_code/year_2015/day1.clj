(ns advent-of-code.2015.day1)

(def input (slurp "resources/2015/day1.txt"))

(defn cmp [c]
  (case c
    \( 1
    \) -1
    0))

(defn part-1 []
  (reduce (fn [acc c] (+ acc (cmp c))) 0 input))

(defn part-2 []
  (loop [i 0 p 0 [x & xs] input]
        (if (= i -1)
          p
          (recur (+ i (cmp x)) (inc p) xs))))
