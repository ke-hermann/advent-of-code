(ns advent-of-code.year-2021.day25
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [pprint-map index2d]]))

(def start (->> (slurp "resources/2021/day25.txt")
                (str/split-lines)
                (index2d)))

(def rows (apply max (map first (keys start))))
(def cols (apply max (map second (keys start))))

(defn east-facing [seafloor]
  (->> (filter (fn [[k v]] (= v ">")) seafloor)
       (map first)))

(defn south-facing [seafloor]
  (->> (filter (fn [[k v]] (= v "v")) seafloor)
       (map first)))

(defn east-free? [seafloor [x y]]
  (let [y* (mod (inc y) (inc cols))]
    (= (seafloor [x y*]) ".")))

(defn south-free? [seafloor [x y]]
  (let [x* (mod (inc x) (inc rows))]
    (= (seafloor [x* y]) ".")))

(defn move-east [seafloor]
  (let [cucumbers (east-facing seafloor)
        moving (filter (partial east-free? seafloor) cucumbers)
        cucumbers* (map (fn [[x y]] [x (mod (inc y) (inc cols))]) moving)]
    (-> (reduce #(assoc %1 %2 ">") seafloor cucumbers*)
        (as-> m (reduce #(assoc %1 %2 ".") m moving)))))

(defn move-south [seafloor]
  (let [cucumbers (south-facing seafloor)
        moving (filter (partial south-free? seafloor) cucumbers)
        cucumbers* (map (fn [[x y]] [(mod (inc x) (inc rows)) y]) moving)]
    (-> (reduce #(assoc %1 %2 "v") seafloor cucumbers*)
        (as-> m (reduce #(assoc %1 %2 ".") m moving)))))

(defn move-cucumbers [seafloor]
  (->> (move-east seafloor)
       (move-south)))

(defn part-1 []
  (loop [i 1 seafloor start]
    (let [seafloor* (move-cucumbers seafloor)]
      (if (= seafloor seafloor*) i
          (recur (inc i) seafloor*)))))
