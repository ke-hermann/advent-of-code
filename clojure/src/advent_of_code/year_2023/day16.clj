(ns advent-of-code.year-2023.day16
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [index2d pprint-map]]
            [clojure.core.match :refer [match]]
            [clojure.set :as set]))


(def mirrors (->> (slurp "resources/2023/day16.txt")
                 (str/split-lines)
                 (index2d)))

(def start {:x 0 :y 0 :dir :right })

(def energized (atom #{}))

(defn flatten-one-level [coll]  
  (mapcat  #(if (sequential? %) % [%]) coll))

(defn right [{:keys [dir x y] :as beam}]
  (update beam :y inc))

(defn left [{:keys [dir x y] :as beam}]
  (update beam :y dec))

(defn down [{:keys [dir x y] :as beam}]
  (update beam :x inc))

(defn up [{:keys [dir x y] :as beam}]
  (update beam :x dec))

(defn propagate [{:keys [dir x y] :as beam}]
  (let [pos (mirrors [x y])]
    (if (or (nil? pos) (contains? @energized [[x y] dir]))
      nil
      (do (swap! energized #(conj % [[x y] dir]))
          (match [pos dir] 
                 ["." :right] (right beam)
                 ["." :left] (left  beam)
                 ["." :up] (up  beam)
                 ["." :down] (down  beam)
                 ["|" :up] (up beam)
                 ["|" :down] (down beam)
                 ["|" :right] [(up (assoc beam :dir :up)) (down (assoc beam :dir :down))]
                 ["|" :left] [(up (assoc beam :dir :up)) (down (assoc beam :dir :down))]
                 ["-" :left] (left beam)
                 ["-" :right] (right beam)
                 ["-" :up] [(left (assoc beam :dir :left)) (right (assoc beam :dir :right))]
                 ["-" :down] [(left (assoc beam :dir :left)) (right (assoc beam :dir :right))]
                 ["/" :up] (right (assoc beam :dir :right))
                 ["/" :down] (left (assoc beam :dir :left))
                 ["/" :left] (down (assoc beam :dir :down))
                 ["/" :right] (up (assoc beam :dir :up))
                 ["\\" :up] (left (assoc beam :dir :left))
                 ["\\" :down] (right (assoc beam :dir :right))
                 ["\\" :left] (up (assoc beam :dir :up))
                 ["\\" :right] (down (assoc beam :dir :down)))))))

(defn trace [[x y] dir]
  (reset! energized #{})
  (loop [beams [{:x x :y y :dir dir}]]
    (let [active (filter map? beams)]
      (if (empty? active)
        (count (set (map first @energized)))
        (recur (flatten-one-level (map propagate active)))))))

(defn edge? [[x y]]
  (or (zero? x) (zero? y) (= x rows) (= y cols)))

(defn part-2 []
  (let [edge-tiles (filter edge? (keys mirrors))]
    (->> (for [tile edge-tiles]
           (match tile
                  [0 0] [(trace tile :down) (trace tile :right)]
                  [0 109] [(trace tile :down) (trace tile :left)]
                  [109 0] [(trace tile :up) (trace tile :right)]
                  [109 109] [(trace tile :up) (trace tile :left)]
                  [0 y] (trace tile :down)
                  [109 y] (trace tile :up)
                  [x 0] (trace tile :right)
                  [x 109] (trace tile :left)))
         (flatten)
         (apply max))))
