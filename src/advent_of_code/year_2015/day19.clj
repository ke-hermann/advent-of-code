(ns year-2015.day19
  (:require [clojure.string :as s]))

(def molecule "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF")

(def replacements (->> (slurp "resources/2015/day19_reps.txt")
                       (s/split-lines)
                       (map #(s/split % #" => "))
                       (group-by first)
                       (map (fn [[k v]] [k (map second v)]))
                       (into {})))

(defn index-seq [s match]
  (let [l (count match)
        xs (map #(apply str %) (partition l 1 s))]
    (->> (map-indexed vector xs)
         (filter (fn [[_ v]] (= v match))))))

(defn replace-mol [s [i k]]
  (let [hd (apply str(take i s))
        rpm (replacements k)
        tl (apply str (drop (+ i (count k)) s))]
    (map #(s/join [hd % tl]) rpm)))

(defn distinct-perms [m]
  (let [xs (->> (map #(index-seq m  %) (keys replacements))
                (apply concat))]
    (->> (map (partial replace-mol m) xs)
         (apply concat))))

(defn build-medicine []
  (loop [cur ["e"] step 1]
    (let [coll (set (mapcat distinct-perms cur))]
      (if (or (> step 10) (contains? coll molecule))
        step
        (recur coll (inc step))))))


