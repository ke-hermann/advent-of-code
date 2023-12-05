(ns year-2020.day20
  (:require [clojure.string :as string]))

(def input (-> (slurp "resources/2020/day20.txt")
                (string/split #"\n\n")))

(defn parse-tile [coll]
  (let [lines (string/split coll #"\n")
        id (Integer/parseInt (re-find #"\d+" (first lines)))
        tile (rest lines)]
    {:id id
     :tile tile}))

(def tiles (map parse-tile input))

(defn rotate-90 [tile]
  (->> (apply map vector tile)
       (map reverse)
       (map (partial apply str))))

(defn rotations [tile]
  (let [right (rotate-90 tile)
        bottom (rotate-90 right)
        left (rotate-90 bottom)]
    [tile right bottom left]))
