(ns advent-of-code.year-2023.day17
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [index2d]]
            [clojure.core.match :refer [match]]))


(def lavamap (->> (slurp "resources/2023/day17.txt")
                  (str/split-lines)
                  (index2d)
                  (map (fn [[k v]] [k (parse-long v)]))
                  (into {})))

(def crucible-cache (atom {}))

(defn move-right )

(defn move [{:keys [x y dir i dist seen]}]
  (when (< dist (get @crucible-cache [x y] Integer/MAX_VALUE))
    (swap! crucible-cache #(assoc % [x y] dist))))

(defn remove-invalid [crucibles]
  (->> (remove #(nil? (lavamap [(% :x) (% :y)])) crucibles)
       (remove #(>= (% :dist) (get @crucible-cache [(% :x) (% :y)] Integer/MAX_VALUE)))))

(def start {:x 0 :y 0 :dir :right :i 0 :dist 0 :seen #{}})

(defn part-1 []
  (loop [crucibles [start]]
    (if (some #(= [12 12] [(% :x) (% :y)]) crucibles)
      crucibles
      (recur (remove-invalid (mapcat move crucibles))))))

