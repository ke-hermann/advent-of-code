(ns advent-of-code.year-2019.day12
  (:require [clojure.string :as str])
  (:require [clojure.math.numeric-tower :refer [lcm]]))


(defstruct moon :x :y :z :xv :yv :zv)

(defn parse-line [s]
  (let [[x y z] (map parse-long (re-seq #"-?\d+" s))]
       (struct moon x y z 0 0 0)))

(defn vel-change [k m1 m2]
  (cond
    (> (m1 k) (m2 k)) -1
    (> (m2 k) (m1 k)) 1
    :else 0))

(defn gravity [moons moon]
  (let [xv* (reduce + (map (partial vel-change :x moon) moons))
        yv* (reduce + (map (partial vel-change :y moon) moons))
        zv* (reduce + (map (partial vel-change :z moon) moons))]
    (-> (update moon :xv #(+ % xv*))
        (update :yv #(+ % yv*))
        (update :zv #(+ % zv*)))))

(defn velocity [{:keys [xv yv zv] :as moon}]
  (-> (update moon :x #(+ % xv))
      (update :y #(+ % yv))
      (update :z #(+ % zv))))


(defn simulate-1 [moons _]
  (->> (map (partial gravity moons) moons)
       (map velocity)))

(defn simulate-2 [moons]
  (->> (map (partial gravity moons) moons)
       (map velocity)))

(defn potential [{:keys [x y z]}]
  (+ (abs x) (abs y) (abs z)))

(defn kinetic [{:keys [xv yv zv]}]
  (+ (abs xv) (abs yv) (abs zv)))

(defn total-energy [moon]
  (* (potential moon) (kinetic moon)))

(def initial (->> (slurp "resources/2019/day12.txt")
                  (str/split-lines)
                  (map parse-line)))

(defn part-1 []
  (->> (reduce simulate-1 initial (range 1000))
       (map total-energy)
       (reduce +)))

(defn part-2 []
  (->> (for [k [:x :y :z]]
         (loop [moons initial seen [] i 0]
           (if (.contains seen (map k  moons))
             [k i]
             (recur (simulate-2  moons)
                    (conj seen (map k moons))
                    (inc i)))))
       (map second)
       (reduce lcm)))
