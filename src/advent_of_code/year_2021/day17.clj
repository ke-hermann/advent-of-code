(ns advent-of-code.year-2021.day17)

(defn update-pos [{:keys [xv yv] :as probe}]
  (-> (update probe :x #(+ % xv))
      (update :y #(+ % yv))))

(defn apply-drag [{:keys [xv] :as probe}]
  (cond
    (< xv 0) (update probe :xv inc)
    (> xv 0) (update probe :xv dec)
    :else probe))

(defn apply-gravity [probe]
  (update probe :yv dec))

(defn move [probe]
  (->> (update-pos probe)
       (apply-drag)
       (apply-gravity)))

(defn in-range? [{:keys [x y]}]
  (and (>= x 32) (<= x 65) (>= y -225) (<= y -177)))

(defn shoot [xv yv]
  (loop [probe {:x 0 :y 0 :xv xv :yv yv :yvals []}]
    (let [y (probe :y)
          probe* (update probe :yvals #(conj % y))]
      (cond
        (< y -225) nil
        (in-range? probe) probe
        :else (recur (move probe*))))))

(defn run []
  (let [probe {:x 0 :y 0 :xv 7 :yv 2}]
    (->> (for [xv (range 1 66) yv (range -300 400)]
           (shoot xv yv))
         (remove nil?)
         (mapcat :yvals)
         (apply max))))
