(ns year-2022.day17)

(def input (slurp "resources/2022/day17.txt"))

(def shapes [[[0 0] [0 1] [0 2] [0 3]]
             [[0 1] [1 0] [1 1] [1 2] [2 1]]
             [[2 2] [1 2] [0 0] [0 1] [0 2]]
             [[0 0] [1 0] [2 0] [3 0]]
             [[0 0] [0 1] [1 0] [1 1]]])


(defn spawn [shape blocks]
  (let [offset (+ 4 (apply max (map first blocks)))]
    (map (fn [[x y]] [(+ offset x) (+ 2 y)]) shape)))

(defn move-left [rock]
  (map (fn [[x y]] [x (dec y)]) rock))

(defn move-right [rock]
  (map (fn [[x y]] [x (inc y)]) rock))

(defn move-down [rock]
  (map (fn [[x y]] [(dec x) y]) rock))

(defn bounds? [rock]
  (some (fn [[x y]] (or (< y 0) (> y 6))) rock))

(defn collision? [rock blocks]
  (some #(contains? blocks %) rock))

(defn pprint [blocks x y]
  (doseq [i (reverse (range x))]
    (doseq [j (range y)]
      (print (if (contains? blocks [i j]) "#" ".")))
    (println "")))

(defn simulate []
  (loop [blocks #{[-1 0] [-1 1] [-1 2] [-1 3] [-1 4] [-1 5] [-1 6]}
         [hd & tl :as xs] (rest (cycle shapes))
         cur (spawn (first shapes) blocks)
         [j & js] (cycle input)
         count 0]
    (let [lr (if (= j \>) (move-right cur) (move-left cur))
          cur* (if (or (collision? lr blocks) (bounds? lr)) cur lr)
          down (move-down cur*)
          cd (collision? down blocks)
          cur** (if cd cur* down)
          blocks* (apply conj blocks cur**)]
      (cond
        (= count 2000) (inc (apply max (map first blocks)))
        cd (recur blocks* tl (spawn hd blocks*) js (inc count))
        :else (recur blocks xs cur** js count)))))

