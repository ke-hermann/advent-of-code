(ns year-2022.day5
  (:require [clojure.string :as s]))



(def instructions (->> (slurp "resources/2022/day5_moves.txt")
                       (s/split-lines)
                       (map #(map read-string (re-seq #"\d+" %)))))

(defn no-boxes? [coll]
  (every? #(.contains [\space \[ \]] %) coll))

(def cargo (->> (slurp "resources/2022/day5_crates.txt")
                (s/split-lines)
                (apply map vector)
                (remove no-boxes?)
                (map #(remove #{\space} %))))

(defn move-boxes [boxes [amount index dest]]
  (let [xs (take amount (boxes index))
        ys (drop amount (boxes index))
        ;; zs (apply conj (boxes dest) xs) <-- part 1
        zs (apply conj (boxes dest) (reverse xs))        ]
    (-> (assoc boxes index ys)
        (assoc dest zs))))

(defn solve []
  (let [m (into {} (map vector (range 1 10) cargo))]
    (->> (reduce move-boxes m instructions)
         (sort-by first)
         (map (comp first second)))))
