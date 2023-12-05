(ns year-2022.day12
  (:require [clojure.string :as s]
            [utils]))

(def heightmap (->> (slurp "resources/2022/day12.txt")
                    (s/split-lines)
                    (utils/index2d)))

(defn adjacent [[x y]]
  [[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]])

(defn valid? [curr p]
  (let [a (heightmap curr)
        b (heightmap p)]
    (cond
      (= b \E) (or  (= a \z) (= a \y))
      :else (<= (- (int b) (int a)) 1))))

(defn traverse [pos seen]
  (let [nbs (adjacent pos) 
        in-map (remove #(not (contains? heightmap %)) nbs)
        valid (remove seen (filter (partial valid? pos) in-map))]
    (if (= (heightmap pos) \E) [seen]
        (mapcat #(traverse % (conj seen pos)) valid))))

(defn part-1 []
  (let [results (traverse [0 0] #{})]
    (->>  (map count results)
          (apply min))))
