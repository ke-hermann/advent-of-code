(ns advent-of-code.2021.day11
  (:require [clojure.string :as s]
            [advent-of-code.utils :as utils]))

(def grid (->> (slurp "resources/2021/day11.txt")
               (s/split-lines)
               (utils/index2d)))

(defn neighbors [[x y]]
  (->> (for [[i j] [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]]
         [(+ x i) (+ y j)])
       (filter #(contains? grid %))))

(defn inc-keys [m ks]
  (reduce (fn [acc k] (update acc k inc)) m ks))

(defn set-zero [m ks]
  (reduce (fn [acc k] (assoc acc k 0)) m ks))

(defn step [m f]
  (let [inced (inc-keys m (keys m))]
    (loop [g inced seen #{} total f]
      (let [ready (remove seen (map first (filter (fn [[_ v]] (> v 9)) g)))
            nbs (apply concat (map neighbors ready))
            g* (inc-keys g nbs)
            total* (+ total (count ready))]
        (if (empty? ready)
          [(set-zero g* seen) total*]
          (recur g* (apply conj seen ready) total*))))))

(defn solve []
  (loop [x 0 m grid t 0]
    (let [[m* t*] (step m t)]
      (if (every? zero? (vals m))
        x
        (recur (inc x) m* t*)))))

(solve)