(ns advent-of-code.2015.day7
  (:require [clojure.string :as s]))


(def input (->> (slurp "resources/2015/day7.txt")
                (s/split-lines)
                (map #(s/split % #"\s+"))))

(defn lookup [m k]
  (get m k (read-string k)))

(defn step [m instruction]
  (let [[a b c d e] instruction]
    (cond
      (= b "->") (assoc m c (read-string a))
      (= b "AND") (assoc m e (bit-and (lookup m a) (lookup m c)))
      (= b "OR") (assoc m e (bit-or (lookup m a) (lookup m c)))
      (= b "LSHIFT") (assoc m e (bit-shift-left (lookup m a) (lookup m c)))
      (= b "RSHIFT") (assoc m e (bit-shift-right (lookup m a) (lookup m c)))
      (= a "NOT") (assoc m d (bit-and-not 16rFFFF (lookup m b)))
      :else (throw (Exception. "malformed m")))))

(defn solve []
  (reduce step {} input))
