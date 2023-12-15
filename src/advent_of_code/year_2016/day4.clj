(ns advent-of-code.year-2016.day4
  (:require [clojure.string :as str]))

(def input (->> (slurp "resources/2016/day4.txt")
                (str/split-lines)))

(defn shift-letter [offset c]
  (if (= \- c) \space
      (let [p (int c)
            i (mod offset 26)
            n (+ p i)]
        (if (<= n 122) (char  n)
            (char (+ 96 (mod n 122)))))))

(defn decode [s]
  (let [i (parse-long (first (re-seq #"\d+" s)))]
    (str/join (map (partial shift-letter i) s))))

(defn part-2 []
  (doseq [s input]
    (println [s (decode s)])))
