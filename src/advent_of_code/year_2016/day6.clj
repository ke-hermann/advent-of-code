(ns year-2016.day6
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2016/day6.txt")
                (s/split-lines)))

(defn most-frequent [xs]
  (->> (frequencies xs)
       ;;(apply max-key second)
       (apply min-key second)
       (first)))

(defn part-1 []
  (->> (apply map vector input)
       (map most-frequent)
       (apply str)))
