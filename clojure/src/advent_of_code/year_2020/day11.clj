(ns year-2020.day11
  (:require [clojure.string :as st]
            [utils :refer [adjacent index2d]]))

(def seats-start (->> (slurp "resources/2020/day11.txt")
                      (st/split-lines)
                      (index2d)))

(defn south [state x y]
  (->> (filter (fn [[[i j] v]] (and (= y j) (> i x))) state)
       (sort-by #(first (first %)))))

(south seats-start 2 2)
