(ns year-2017.day22
  (:require [clojure.string :as str]
            [utils]))

(def input (->> (slurp "resources/2017/day22.txt")
                (str/split-lines)))

(def infection-count (atom 0))

(defn turn-right [virus]
  (case (virus :dir)
    :up (assoc virus :dir :right)
    :right (assoc virus :dir :down)
    :down (assoc virus :dir :left)
    :left (assoc virus :dir :up)
    ))

(defn turn-left [virus]
  (case (virus :dir)
    :up (assoc virus :dir :left)
    :left (assoc virus :dir :down)
    :down (assoc virus :dir :right)
    :right (assoc virus :dir :up)))

(defn revert [virus]
  (case (virus :dir)
    :up (assoc virus :dir :down)
    :left (assoc virus :dir :right)
    :down (assoc virus :dir :up)
    :right (assoc virus :dir :left)))

(defn move [virus]
  (case (virus :dir)
    :up (update virus :x dec)
    :right (update virus :y inc)
    :down (update virus :x inc)
    :left (update virus :y dec)))

(defn flip-node [n]
  (case n
    "F" "."
    "#" "F"
    "W" (do (swap! infection-count inc) "#")
    "W"))


(defn update-virus [{:keys [x y dir] :as virus} grid]
  (let [node (get grid [x y] ".")]
    (case node
      "." (move (turn-left virus))
      "W" (move virus)
      "#" (move (turn-right virus))
      "F" (move (revert virus)))))

(defn solve []
  (let [start {:x 12 :y 12 :dir :up}
        init-grid (utils/index2d input)]
    (loop [{:keys [x y] :as virus} start grid init-grid i 0]
      (if (= i 10000000)
        (println @infection-count)
        (recur (update-virus virus grid)
               (update grid [x y] flip-node)
               (inc i))))))
