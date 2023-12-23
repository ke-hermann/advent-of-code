(ns year-2022.day14
  (:require [clojure.string :as s]))

(def rocks (->> (slurp "resources/2022/day14.txt")
                (s/split-lines)
                (map #(re-seq #"\d+" %))
                (map #(map (fn [x] (Integer/parseInt x)) %))
                (map #(partition 2 %))
                (map #(partition 2 1 %))))

(defn lines [[x y] [i j]]
  (for [n (range (min x i) (inc (max x i)))
        m (range (min y j) (inc (max y j)))]
    [n m]))

(def start
  (->> (map #(map (partial apply lines) %) rocks)
       (apply concat)
       (apply concat)
       (set)))

(def limit (apply max (map second start)))

(defn sand-move [[x y] borders]
  (let [down [x (inc y)]
        dleft [(dec x) (inc y)]
        dright [(inc x) (inc y)]]
    (cond
      (= y (+ 2 limit)) [x y]
      ;;(> y limit) [] -- part 1
      (not (contains? borders down)) (sand-move down borders)
      (not (contains? borders dleft)) (sand-move dleft borders)
      (not (contains? borders dright)) (sand-move dright borders)
      :else [x y]
      )))

(defn simulate []
  (loop [i 0 borders start]
    ;; if (= i 10000) -- part 1 aribrary large number
    (if (contains? borders [500 0])
      (-  (count borders)
          (count start)
          (count (filter (fn [[x y]] (= y (+ 2 limit))) borders)))
      (recur (inc i) (conj borders (sand-move [500 0] borders))))))
