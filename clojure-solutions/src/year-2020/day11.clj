(ns year-2020.day11
  (:require [clojure.string :as st]
            [utils :refer [adjacent index2d]]))



(def seats-start (->> (slurp "resources/2020/day11.txt")
                      (st/split-lines)
                      (index2d)))

(defn update-seat [m seat]
  (let [nbs (map m (adjacent m seat))
        i (count (filter #{\#} nbs))]
    (cond
      (and (= i 0) (= (m seat) \L)) [seat \#]
      (and (>= i 4) (= (m seat) \#)) [seat \L]
      :else [seat (m seat)])))

(defn update-all-seats [m]
  (->> (map (partial update-seat m) (keys m))
       (into {})))

(defn part-1 []
  (loop [i 0 state seats-start old {}]
    (if (= state old)
      (count (filter #{\#} (vals state)))
      (recur (inc i) (update-all-seats state) state))))
