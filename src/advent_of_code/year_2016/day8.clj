(ns year-2016.day8
  (:require [clojure.string :as str]
            [utils]))


(def start (->> (for [x (range 6)]
                  (for [j (range 50)]
                    0))
                (to-array-2d)))

(defn rotate-row [m i array]
  (let [row (nth array m)
        l (count row)
        xs (map vector (range) row)
        idxs (map first (filter #(= 1 (second %)) xs))
        shifted (map #(mod (+ i %) l) idxs)]
    (->> (map #(if (.contains shifted %) 1 0) (range l))
         (to-array)
         ((fn [a] (aset array m a))))))

(defn rotate-col [m i array]
  (->> (utils/transpose array)
       (rotate-row m i)
       (utils/transpose)
       (to-array-2d)))

(defn rect [m n array]
  (doseq [i (range m) j (range n)]
    (aset array j i 1)))

(defn part-1 []
  (->> (rect 3 2 start)
       (rotate-col 1 1)))
