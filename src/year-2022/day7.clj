(ns year-2022.day7
  (:require [clojure.string :as s]))

(defn list-dirs [coll]
  (take-while #(not= \$ (first %)) coll))

(defn execute-commands [instructions]
  (loop [state {} dir [] xs instructions]
    (if (empty? xs)
      state
      (let [[hd & tl] xs
            [x y z] (s/split hd  #"\s+")]
        (cond
          (= hd "$ cd /")
          (recur state ["/"] tl)
          (= hd "$ ls")
          (recur (assoc state dir (list-dirs tl))
                 dir
                 (drop-while #(not= (first %) \$) tl))
          (= hd "$ cd ..")
          (recur state
                 (vec (butlast dir))
                 tl)
          (= [x y] ["$" "cd"]) (recur state (conj dir z) tl))))))

(def filesystem (->> (slurp "resources/2022/day7.txt")
                     (s/split-lines)
                     (execute-commands)))

(def all-folders (map first filesystem))

(defn total-size [dir]
  (let [l (count dir)
        dirs (filter (fn [[k v]] (= dir (take l k))) filesystem)
        contents (mapcat second dirs)
        files (remove #(.contains % "dir") contents)
        weights (map #(first (s/split % #"\s+")) files)]
    (->> (map #(Integer/parseInt %) weights)
         (reduce +))))

(defn part-1 []
  (->> (map total-size all-folders)
       (filter #(<= % 100000))
       (reduce +)))

(defn part-2 []
  (let [total-space 70000000
        unused-space  (- total-space (total-size ["/"]))
        required (- 30000000 unused-space)]
    (->> (map total-size all-folders)
         (filter #(>= % required))
         (apply min))))
