(ns advent-of-code.year-2017.day17
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [index2d neighbors pprint-map]])
  (:import java.security.MessageDigest)
  (:import java.math.BigInteger))


(def input (->> (slurp "resources/2017/day17.txt")
                (str/split-lines)
                (index2d)))

(def start "gdjjyniy")

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn door? [[p _]]
  (or (= (input p) "|") (= (input p) "-")))

(defn open? [[_ c]]
  (str/includes? "bcedf" (str c)))

(defn move-up [steps x y] [(str/join [steps "U"]) [(- x 2) y]])
(defn move-down [steps x y] [(str/join [steps "D"]) [(+ x 2) y]])
(defn move-left [steps x y] [(str/join [steps "L"]) [x (- y 2)]])
(defn move-right [steps x y] [(str/join [steps "R"]) [x (+ y 2)]])

(defn move [[x y] steps direction]
  (case direction
    :up (move-up steps x y)
    :left (move-left steps x y)
    :down (move-down steps x y)
    :right (move-right steps x y)))

(def longest (atom []))

(defn step [[s pos]]
  (let [hash (md5 s)
        xs (map vector (neighbors pos) hash)
        ys (map #(and (open? %) (door? %)) xs)
        zs (zipmap [:up :down :left :right] ys)
        open (map first (filter (fn [[_ b]] (true? b)) zs))]
    (cond
      (empty? open) []
      (= pos [7 7]) (do (swap! longest #(conj % (- (count s) (count start)))) [])
      :else (map (partial move pos s) open))))

(defn solve []
  (loop [xs [[start [1 1]]] i 0]
    (cond
      (empty? xs) i
      (> i 1000) (throw (Exception. "invalid state"))
      :else (recur (mapcat step xs) (inc i)))))

;; possible answers [577, 578, 580, 581]
