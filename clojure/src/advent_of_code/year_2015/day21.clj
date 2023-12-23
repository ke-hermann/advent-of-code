(ns advent-of-code.year-2015.day21
  (:require [clojure.string :as str]))

(def weapons
  [[8     4       0]
   [10     5       0]
   [25     6       0]
   [40     7       0]
   [74     8       0]])

(def armor
  [[13     0       1]
   [31     0       2]
   [53     0       3]
   [75     0       4]
   [102     0       5]
   [0 0 0]])

(def rings-1
  [[25     1       0]
   [50     2       0]
   [100     3       0]
   [20     0       1]
   [40     0       2]
   [80     0       3]
   [0 0 0]
   ])

(def rings-2
  [[25     1       0]
   [50     2       0]
   [100     3       0]
   [20     0       1]
   [40     0       2]
   [80     0       3]
   [0 0 0]])

(def gear-choices
  (->> (for [w weapons a armor r1 rings-1 r2 rings-2]
     (if (= r1 r2) nil
         (zipmap [:cost :damage :armor] (mapv + w a r1 r2))))
       (remove nil?)))

(defn deal-dmg [attack defense]
  (if (<= (- attack defense) 0) 1 (- attack defense)))

(defn simulate-battle [{:keys [cost damage armor]} ]
  (let [boss-armor 2
        boss-dmg 8]
    (loop [player-hp 100 boss-hp 109]
      (let [boss-hp* (- boss-hp (deal-dmg damage boss-armor))
            player-hp* (- player-hp (deal-dmg boss-dmg armor))]
        (cond
          (<= boss-hp* 0) :win
          (<= player-hp* 0) :loss
          :else (recur player-hp* boss-hp*))))))

(defn part-1 []
  (->> (filter #(= :win (simulate-battle %)) gear-choices)
       (apply min-key :cost)))

(defn part-2 []
  (->> (filter #(= :loss (simulate-battle %)) gear-choices)
       (apply max-key :cost)))
