(ns year-2023.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))


(def input (->> (slurp "resources/2023/day3.txt")
                (str/split-lines)))

(defn re-seq-pos [pattern string] 
  (let [m (re-matcher pattern string)] 
    ((fn step [] 
       (when (. m find) 
         (cons {:start (. m start) :end (. m end) :string (. m group)} 
               (lazy-seq (step))))))))

(defn neighbors [[x y]]
  (for [i [-1 0 1] j [-1 0 1]] [(+ x i) (+ y j)]))

(defn is-symbol? [c]
  (contains? #{"=" "*" "%" "/" "-" "&" "#" "+" "$" "@"}  c))

(defn get-indices [row {:keys [start end string]}]
  {:indices (map (fn [x] [row x]) (range start end))
   :string string})

(defn is-valid? [{:keys [indices]}]
  (let [coords (mapcat neighbors indices)]
    (->> (remove (set indices) coords)
         (some #(is-symbol? (str (get-in input % ".")))))))

(defn find-in-row [regex row]
  (->> (re-seq-pos regex row)
       (map (partial get-indices (.indexOf input row)))))

(def all-numbers
  (mapcat (partial find-in-row #"\d+") input))

(def all-asterisks
  (->> (mapcat (partial find-in-row #"\*") input)
       (map :indices)
       (map first)))

(defn number-in-range? [coords {:keys [indices]}]
  (not-empty (set/intersection coords (set indices))))

(defn gear-score [asterisk]
  (let [coords (set (neighbors asterisk))
        numbers (filter (partial number-in-range? coords) all-numbers)]
    (if (= 2 (count numbers))
      (->> (map :string numbers)
           (map #(Integer/parseInt %))
           (reduce *))
      0)))

(defn part-1 []
  (->> all-numbers
       (filter is-valid?)
       (map #(Integer/parseInt (% :string)))
       (reduce +)))

(defn part-2 []
  (reduce + (map gear-score all-asterisks)))
