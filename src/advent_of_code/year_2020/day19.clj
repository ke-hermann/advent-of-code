(ns advent-of-code.year-2020.day19
  (:require [clojure.string :as str]))

(defn parse [t]
  (if (number? (read-string t))
    (parse-long t)
    t))

(defn split-on-or [xs]
  (let [[left right] (split-with #(not= % "|") xs)]
    [left (rest right)]))

(defn parse-line [s]
  (let [[idx right] (str/split s #":")
        xs (map parse (re-seq #"[a-z0-9\|]+" right))]
    [(parse-long idx) (if (= 1 (count xs)) (first xs) xs)]))

(def rules (->> (slurp "resources/2020/day19.txt")
                (str/split-lines)
                (map parse-line)
                (into {})))

(defn expand [t]
  (cond
    (string? t) [t] 
    (number? t) (expand (rules t))
    (.contains t "|") (map expand (split-on-or t))
    :else (mapcat expand t)))

