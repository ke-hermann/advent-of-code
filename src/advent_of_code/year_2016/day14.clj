(ns advent-of-code.year-2016.day14
  (:require [clojure.string :as str])
  (:import java.security.MessageDigest)
  (:import java.math.BigInteger))


(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(def salt "yjdafjpo")

(defn stretched-md5 [s]
  (loop [i 0 h (md5 s)]
    (if (= i 2016) h
        (recur (inc i) (md5 h)))))

(def hashes (mapv #(stretched-md5 (str salt %)) (range 100000)))

(defn triplets [s]
  (->> (partition 3 1 s)
       (filter (fn [[a b c]] (= a b c)))
       (first)))

(defn is-key? [[index match]]
  (let [xs (subvec hashes (inc index) (+ 1001 index))
        quin (str/join (repeat 5 (first match)))]
    (some #(str/includes? % quin) xs)))

(defn solve []
  (let [candidates (->> (map-indexed (fn [i h] [i (triplets h)]) hashes)
                        (remove (fn [[i h]] (nil? h))))]
    (last (take 64 (filter is-key? candidates)))))
