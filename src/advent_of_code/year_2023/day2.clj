(ns year-2023.day2
  (:require [clojure.string :as str]))

(def input (->> (slurp "resources/2023/day2.txt")
                (str/split-lines)
                (map parse)))

(defn parse-cube [s]
  (let [[i color] (str/split (str/trim s) #" ")]
    [(keyword color) (Integer/parseInt i)]))

(defn parse-set [s]
  (->> (map parse-cube (str/split s #","))
       (into {})))

(defn parse [line]
  (let [[id more] (str/split line #":")
        cube-sets (map parse-set (str/split more #";"))]
    {:id (Integer/parseInt (re-find #"\d+" id)) :cubes cube-sets}))

(defn impossible? [{:keys [red green blue] :or {red 0 blue 0 green 0}}]
  (or (> red 12) (> green 13) (> blue 14)))

(defn invalid? [game]
  (some impossible? (game :cubes)))

(defn power [{:keys [cubes]}]
  (* (apply max (map #(get % :red 0) cubes))
     (apply max (map #(get % :blue 0) cubes))
     (apply max (map #(get % :green 0) cubes))))

(defn part-1 []
  (->> input
       (remove invalid?)
       (map :id)
       (reduce +)))

(defn part-2 []
  (reduce + (map power input)))


(defn foo [{id :id name :name}]
  (println name))

