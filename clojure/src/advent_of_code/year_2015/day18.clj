(ns year-2015.day18
  (:require [clojure.string :as s]
            [utils]))


(def start-grid (->> (slurp "resources/2015/day18.txt")
                     (s/split-lines)
                     (utils/index2d)))

(defn neighbors-8 [g x y]
  (->> (for [i [-1 0 1] j [-1 0 1]]
         [(+ x i) (+ y j)])
       (remove #{[x y]})))

(defn update-cell [lights pos]
  (let [nbs (apply neighbors-8 lights pos)
        lgt (map #(get lights % ".") nbs)
        c (count (filter #{"#"} lgt))]
    (if (= (lights pos) "#")
      (if (or (= c 2) (= c 3))
        [pos "#"]
        [pos "."])
      (if (= c 3)
        [pos "#"]
        [pos "."]))))

(defn stuck [lights]
  (reduce #(assoc %1 %2 "#") lights [[0 0] [0 99] [99 0] [99 99]]))

(defn step [lights]
  (->> (map (partial update-cell lights) (keys lights))
       (into {})))

(defn part-1 []
  (loop [x 0 lights (stuck start-grid)]
    (if (= x 100)
      (count (filter #{"#"} (vals lights)))
      (recur (inc x) (stuck (step lights))))))
