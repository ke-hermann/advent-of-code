(ns year-2023.day1
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2023/day1.txt")
                (s/split-lines)))

(def digits  ["1" "2" "3" "4" "5" "6" "7" "8" "9" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])

(defn lookup [d]
  ({"1" 1 "2" 2 "3" 3 "4" 4 "5" 5 "6" 6 "7" 7 "8" 8 "9" 9 "one" 1 "two" 2 "three" 3  "four" 4  "five" 5 "six" 6 "seven" 7 "eight" 8 "nine" 9} d))

(defn leftmost [s]
  (->> (filter (partial s/includes? s) digits)
       (apply min-key (partial s/index-of s))))

(defn rightmost [s]
  (->> (filter (partial s/includes? s) digits)
       (apply max-key (partial s/last-index-of s))))

(defn calibration-value [s]
  (let [l (leftmost s)
        r (rightmost s)]
    (Integer/parseInt (str (lookup l) (lookup r)))))

(defn solve-day1 []
  (reduce + (map calibration-value input)))
