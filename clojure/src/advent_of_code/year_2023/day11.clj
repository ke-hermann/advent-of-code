(ns advent-of-code.year-2023.day11
  (:require [clojure.string :as str]
            [advent-of-code.utils]))

(def starmap (->> (slurp "resources/2023/day11.txt")
                  (str/split-lines)
                  (utils/index2d)))

(def galaxies (->> (filter (fn [[k v]] (= v "#")) starmap)
                   (map first)))

(def empty-rows (-> (set (map first galaxies))
                    (remove (range (count galaxies)))))

(def empty-cols (-> (set (map second galaxies))
                    (remove (range (count galaxies)))))

(def exp-factor (dec 1000000))

(defn distance [[x y] [i j]]
  (let [[lx hx] (sort [x i])
        [ly hy] (sort [y j])
        xs (count (filter #(and (> % lx) (< % hx)) empty-rows))
        ys (count (filter #(and (> % ly) (< % hy)) empty-cols))]
    (+ (* exp-factor xs) (* exp-factor ys) (abs (- x i)) (abs (- y j)))))

(defn run []
  (->> (for [g1 galaxies g2 galaxies] (distance g1 g2))
       (reduce +)
       (#(/ % 2))))
