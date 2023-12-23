(ns year-2022.day18
  (:require [clojure.string :as st]
            [clojure.set :refer [union]]))

(def cubes (->> (slurp "resources/2022/day18.txt")
                (st/split-lines)
                (map #(map read-string (re-seq #"\d+" %)))
                (set)))


(defn neighbors [[x y z]]
  [[(inc x) y z] [(dec x) y z] [x (inc y) z]
   [x (dec y) z] [x y (inc z)] [x y (dec z)]])

(defn open-sides []
  (->> (mapcat neighbors cubes)
       (filter #(not (contains? cubes %)))
       (count)))

(defn bounds? [p]
  (every? #(and (>= % -1) (<= % 22)) p))

(defn flood []
  (loop [q [[-1 -1 -1]] vs #{}]
    (if (empty? q)
      vs
      (let [[hd & tl] q
            steam (remove (union cubes vs) (neighbors hd))
            nxt (filter bounds? steam)
            q* (apply conj tl nxt)]
        (recur q* (conj vs hd))))))

(defn exterior []
  (->> (flood)
       (mapcat neighbors)
       (filter (partial contains? cubes))
       (count)))
