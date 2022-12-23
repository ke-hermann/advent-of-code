(ns year-2022.day23
  (:require [clojure.string :as st]
            [utils :refer [index2d]]))

(def start-pos (->> (slurp "resources/2022/day23.txt")
                    (st/split-lines)
                    (index2d)
                    (filter (fn [[k v]] (= v \#)))
                    (map first)))


(defn north? [elves [x y]]
  (not-any? #(.contains elves %) [[(dec x) (dec y)] [(dec x) y] [(dec x) (inc y)]]))

(defn south? [elves [x y]]
  (not-any? #(.contains elves %) [[(inc x) (inc y)] [(inc x) y] [(inc x) (dec y)]]))

(defn west? [elves [x y]]
  (not-any? #(.contains elves %) [[(inc x) (dec y)] [x (dec y)] [(dec x) (dec y)]]))

(defn east? [elves [x y]]
  (not-any? #(.contains elves %) [[(inc x) (inc y)] [x (inc y)] [(dec x) (inc y)]]))

(defn isolated? [elves elf]
  (every? true? (map #(% elves elf) [north? east? south? west?])))

(defn step [f [x y]]
  (cond
    (= f north?) [[x y] [(dec x) y]]
    (= f south?) [[x y] [(inc x) y]]
    (= f east?)  [[x y] [x (inc y)]]
    (= f west?)  [[x y] [x (dec y)]]))

(defn proposition [elves [f1 f2 f3 f4] elf]
  (let [[x y] elf]
    (cond
      (isolated? elves elf) [[x y] [x y]]
      (f1 elves elf) (step f1 elf)
      (f2 elves elf) (step f2 elf)
      (f3 elves elf) (step f3 elf)
      (f4 elves elf) (step f4 elf)
      :else [[x y] [x y]])))

(defn move [elves order]
  (let [ps (map (partial proposition elves order) elves)
        fq (frequencies (map second ps))]
    (map (fn [[s d]] (if (= 1 (fq d)) d s)) ps )))

(defn count-empty [tiles]
  (let [x (apply min (map first tiles))
        y (apply min (map second tiles))
        i (apply max (map first tiles))
        j (apply max (map second tiles))]
    (->> (for [a (range x (inc i))
               b (range y (inc j))]
           (if (.contains tiles [a b]) 0 1))
         (reduce +))))

(defn part-1 []
  (loop [elves start-pos
         order (cycle [north? south? west? east?])
         i 0]
    (if (= i 10) (count-empty elves)
        (recur (move elves (take 4 order))
               (rest order)
               (inc i)))))

(defn part-2 []
  (loop [elves start-pos
         order (cycle [north? south? west? east?])
         i 0
         old []]
    (if (= old elves) i
        (recur (move elves (take 4 order))
               (rest order)
               (inc i)
               elves))))
