(ns year-2022.day6
  (:require [clojure.string :as s]))

(def datastream (slurp "resources/2022/day6.txt"))

(defn find-marker [i]
  (->> (partition i 1 datastream)
       (take-while #(not (apply distinct? %)))
       (count)
       (+ i)))

(defn solution []
  (println (find-marker 4))
  (println (find-marker 14)))
