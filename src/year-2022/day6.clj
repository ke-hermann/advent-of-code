(ns year-2022.day6
  (:require [clojure.string :as s]))

(def datastream (slurp "resources/2022/day6.txt"))

(defn find-marker [i]
  (->> (partition i 1 datastream)
       (take-while #(not (apply distinct? %)))
       (count)
       (+ i)))

(find-marker 4)
(find-marker 14)
