(ns year-2017.day20)


(defstruct particle :x :y :z :xv :yv :zv :xa :ya :za)

(def input (->> (slurp "resources/2017/day20.txt")
                (clojure.string/split-lines)
                (map (fn [s] (re-seq #"-?\d+\.?\d*" s)))
                (map #(map (fn [i] (Integer/parseInt i)) %))
                (map (fn [p] (apply struct particle p)))
                (vec)))

(defn update-particle [p]
  (-> (update p :xv #(+ % (p :xa)))
      (update :yv #(+ % (p :ya)))
      (update :zv #(+ % (p :za)))
      (as-> p* (update p* :x #(+ % (p* :xv))))
      (as-> p* (update p* :y #(+ % (p* :yv))))
      (as-> p* (update p* :z #(+ % (p* :zv))))))

(defn manhattan-dist [{:keys [x y z]}]
  (+ (abs x) (abs y) (abs z)))

(defn collide [particles]
  (->> (group-by (juxt :x :y :z) particles)
       (map second)
       (remove (fn [g] (> (count g) 1)))
       (apply concat)))

(defn solve []
  (loop [particles input i 0]
    (let [uniques (collide particles)
          particles* (mapv update-particle uniques)]
      (if (> i 2000)
        (count particles*)
        (recur particles* (inc i))))))
