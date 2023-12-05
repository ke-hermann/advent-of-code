(ns year-2022.day7
  (:require [clojure.string :as s]))

(defn folder-size [folder]
  (->> (take-while #(not= \$ (first %)) folder)
       (mapcat #(re-seq #"\d+" %))
       (map #(Integer/parseInt %))
       (reduce +)))

(defn execute-commands [instructions]
  (loop [folders {} path [] command instructions]
    (if (empty? command)
      folders
      (let [[hd & tl] command
            [x y z] (s/split hd  #"\s+")]
        (cond
          ;; back to root
          (= hd "$ cd /")
          (recur folders ["/"] tl)
          ;; list contents
          (= hd "$ ls")
          (recur (assoc folders path (folder-size tl))
                 path
                 (drop-while #(not= (first %) \$) tl))
          ;; one up
          (= hd "$ cd ..")
          (recur folders
                 (vec (butlast path))
                 tl)
          (= [x y] ["$" "cd"]) (recur folders (conj path z) tl))))))

(def filesystem (->> (slurp "resources/2022/day7.txt")
                     (s/split-lines)
                     (execute-commands)))

(def all-folders (map first filesystem))

(defn subfolder? [dir [k _]]
  (= dir (take (count dir) k)))

(defn total-size [dir]
  (let [dirs (filter (partial subfolder? dir) filesystem)
        weights (map second dirs)]
    (reduce + weights)))

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
