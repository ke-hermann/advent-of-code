(ns year-2020.day24)


(def input (->> (slurp "resources/2020/day24.txt")
                (clojure.string/split-lines)))

(defn hex-move [[q r s] dir]
  (case dir
    "ne" [(inc q) (dec r) s]
    "e" [(inc q) r (dec s)]
    "se" [q (inc r) (dec s)]
    "sw" [(dec q) (inc r) s]
    "w" [(dec q) r (inc s)]
    "nw" [q (dec r) (inc s)]))

(defn split-line [xs]
  (loop [out [] ys xs]
    (if (zero? (count ys)) out
        (cond
          (= \e (first ys)) (recur (conj out "e") (rest ys))
          (= \w (first ys)) (recur (conj out "w") (rest ys))
          (= [\n \e] (take 2 ys)) (recur (conj out "ne") (drop 2 ys))
          (= [\s \e] (take 2 ys)) (recur (conj out "se") (drop 2 ys))
          (= [\s \w] (take 2 ys)) (recur (conj out "sw") (drop 2 ys))
          (= [\n \w] (take 2 ys)) (recur (conj out "nw") (drop 2 ys))))))

(defn neighbors [m q r s]
  [(m [(inc q) (dec r) s])
   (m [(inc q) r (dec s)])
   (m [q (inc r) (dec s)])
   (m [(dec q) (inc r) s])
   (m [(dec q) r (inc s)])
   (m [q (dec r) (inc s)])])

(defn walk [directions]
  (reduce hex-move [0 0 0] (split-line directions)))

(defn flip [m tile]
  (let [coll (apply neighbors m tile)
        white (count (filter #{:white} coll))
        black (count (filter #{:black} coll))]
    (cond
      (and  (= (m tile) :white)
            (= black 2))
      [tile :black]
      (and (= (m tile) :black)
           (or (zero? black) (> black 2)))
      [tile :white]
      :else [tile (m tile)])))

(defn gen-keys [m]
  (let [qmin (apply min (map first (keys m)))
        qmax (apply max (map first (keys m)))
        rmin (apply min (map second (keys m)))
        rmax (apply max (map second (keys m)))
        smin (apply min (map last  (keys m)))
        smax (apply max (map last (keys m)))]
    (for [q (range qmin (inc qmax))
          r (range rmin (inc rmax))
          s (range smin (inc smax))]
      [q r s])))

(defn part-1 []
  (let [result (->> (map walk input)
                    (frequencies)
                    (group-by (fn [[k v]] (even? v))))]
    (count (result false))))

(defn part-2 []
  (let [start (->> (frequencies (map walk input))
                   (map (fn [[k v]] [k (if (even? v) :white :black)]))
                   (into {}))
        ks (gen-keys start)]
    (filter (fn [[k v]] (= v :black)) (map #(flip start %) ks))))
