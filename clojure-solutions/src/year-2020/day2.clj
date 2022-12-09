(ns year-2020.day2
  (:require [clojure.string :as s]))

(defn parse-input-line [line]
  (let [[low high] (re-seq #"\d+" line)
        xs (re-seq  #"\s+" line)]
    [low high xs]))

(def input (->> (slurp "resources/2020/day2.txt")
                (s/split-lines)
                (map parse-input-line)))
