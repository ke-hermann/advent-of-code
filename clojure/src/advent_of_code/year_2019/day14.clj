(ns advent-of-code.year-2019.day14
  (:require [clojure.string :as str]))

(defn parse-pair [s]
  (let [[v k] (str/split s #"\s+")]
    [k (parse-long v)]))

(defn parse-line [s]
  (let [[l r] (str/split s #" => ")
        xs (str/split l #", ")
        k (parse-pair r)]
    [k (map parse-pair xs)]))

(def factory (->> (slurp "resources/2019/day14.txt")
                  (str/split-lines)
                  (map parse-line)))

(defn lookup  [mat]
  (let [[[m quant] more] (first (filter (fn [[[k q] v]] (= mat k)) factory))]
    (map (fn [[k v]] [k (* quant v)]) more)))
