(ns advent-of-code.2015.day6)


(def input (->> (slurp "resources/2015/day6.txt")
                (clojure.string/split-lines)
                (map #(map read-string (clojure.string/split % #"\s+")))))

(defn produce-indices [x1 x2 y1 y2]
  (for [i (range x1 (inc y1)) j (range x2 (inc y2))]
    [i j]))

(defn turn-off [x]
  (cond
    (nil? x) 0
    (<= x 0) 0
    :else (dec x)))

(defn turn-on [x]
  (cond
    (nil? x) 1
    :else (inc x)))

(defn toggle [x]
  (cond
    (nil? x) 2
    :else (+ x 2)))

(defn update-grid [g k instruction]
  (case instruction
    :on (update g k turn-on)
    :off (update g k turn-off)
    (update g k toggle )))

(defn process [grid [inst a b c d]]
  (let [xs (produce-indices a b c d)]
    (reduce (fn [acc k] (update-grid acc k inst)) grid xs)))

(defn solve []
  (->> (reduce process {} input)
       (vals)
       (reduce +)))
