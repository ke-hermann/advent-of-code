(ns year-2022.day8
  (:require [clojure.string :as s]
            [utils]))


(def trees (->> (slurp "resources/2022/day8.txt")
                (s/split-lines)
                ( utils/index2d)))

(def height (apply max (map  first (keys trees))))
(def width (apply max (map  second (keys trees))))

(defn neighbors [tree]
  (let [[x y] tree
        ks (keys trees)]
    [(filter (fn [[i j]] (and (= i x) ( < j y))) ks)
     (filter (fn [[i j]] (and (= i x) ( > j y))) ks)
     (filter (fn [[i j]] (and (< i x) (= j y))) ks)
     (filter (fn [[i j]] (and (> i x) (= j y))) ks)]))

(defn visible? [tree]
  (let [xs (neighbors tree)
        h (trees tree)]
    (some (fn [coll] (every? #(< (trees %) h) coll)) xs)))

(defn edge? [[x y]]
  (or (= 0 x) (= height x) (= 0 y) (= width y)))

(defn dist [coll]
  (let [l (count coll)]
    (if (edge? (last coll)) l (inc l))))

(defn scenic-score [[x y]]
  (let [[l r u d] (neighbors [x y])
        h (trees [x y])]
    (* (dist (take-while #(< (trees %) h) (reverse (sort-by second l))))
       (dist (take-while #(< (trees %) h) (sort-by second r)))
       (dist (take-while #(< (trees %) h) (reverse (sort-by first u))))
       (dist (take-while #(< (trees %) h) (sort-by first d))))))

(defn part-1 []
  (count (filter visible? (keys trees))))

(defn part-2 []
  (->> (remove edge? (keys trees))
       (map scenic-score)
       (apply max)))
