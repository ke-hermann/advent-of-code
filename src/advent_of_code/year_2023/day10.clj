(ns advent-of-code.year-2023.day10
  (:require [clojure.string :as str]
            [advent-of-code.utils]))


(def grid (->> (slurp "resources/2023/day10.txt")
               (str/split-lines)
               (utils/index2d)))

(def pipes (into {} (remove (fn [[k v]] (= v ".")) grid)))

(def start [79 85])

(defn adjacent [[x y] pipe]
  (case pipe
    "|" #{[(dec x) y] [(inc x) y]}
    "-" #{[x (dec y)] [x  (inc y)]}
    "L" #{[(dec x) y] [x  (inc y)]}
    "J" #{[(dec x) y] [x  (dec y)]}
    "7" #{[(inc x) y] [x  (dec y)]}
    "F" #{[(inc x) y] [x  (inc y)]}))

(defn move [pos seen]
  (let [xs (adjacent pos (pipes pos))]
    (first (remove seen xs))))

(defn walk-pipes []
  (loop [pos [79 84] seen #{start}]
    (let [pos* (move pos seen)
          seen* (conj seen pos)]
      (if (nil? pos*) seen*
          (recur pos* (conj seen pos))))))

(defn enclosed? [max-y loop [x y]]
  (let [points (map vector (repeat x) (range (inc y) (inc max-y)))
        segments (-> (map pipes (sort-by second (filter (set points) loop)))
                     (str/join)
                     (str/replace #"L-*J|F-*7" "||")
                     (str/replace #"L-*7|F-*J" "|"))]
    (odd? (count (filter #{\| \F \7 \L \J} segments)))))

(defn part-1 [] (quot (count (walk-pipes)) 2))

(defn part-2 []
  (let [loop (conj (walk-pipes) start) 
        tiles (remove loop (keys grid))
        max-y (apply max (map second loop))]
    (count (filter (partial enclosed? max-y loop) tiles))))
