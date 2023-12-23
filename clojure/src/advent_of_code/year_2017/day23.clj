(ns year-2017.day23
  (:require [clojure.string :as str]))


(def input (->> (slurp "resources/2017/day23.txt")
                (str/split-lines)
                (map #(str/split % #"\s+"))
                (vec)))

(def start {"a" 0 "b" 0 "c" 0 "d" 0 "e" 0 "f" 0 "g" 0 "h" 0})

(def mul-count (atom 0))

(defn update-registers [r pos]
  (let [[opcode k v] (input pos)
        k* (if (str/includes? "abcdefgh" k) (r k) (Integer/parseInt k))        
        v* (if (str/includes? "abcdefgh" v) (r v) (Integer/parseInt v))]
    (case opcode
      "set" [(assoc r k v*) (inc pos)]
      "sub" [(update r k #(- % v*)) (inc pos)]
      "mul" (do (swap! mul-count inc)
              [(update r k #(* % v*)) (inc pos)])
      "jnz" (if (not (zero? k*))
              [r (+ pos v*)]
              [r (inc pos)]))))


(defn solve []
  (loop [r start pos 0]
    (if (or (< pos 0) (> pos 31))
      (println @mul-count)
      (let [[r* pos*] (update-registers r pos)]
        (recur r* pos*)))))
