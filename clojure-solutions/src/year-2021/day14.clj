(ns advent-of-code.2021.day14)

(def polymer-templates
  {
   "SV"  "C"
   "SF"  "P"
   "BP"  "V"
   "HC"  "B"
   "PK"  "B"
   "NF"  "C"
   "SN"  "N"
   "PF"  "S"
   "ON"  "S"
   "FC"  "C"
   "PN"  "P"
   "SC"  "B"
   "KS"  "V"
   "OS"  "S"
   "NC"  "C"
   "VH"  "N"
   "OH"  "C"
   "BB"  "H"
   "KV"  "V"
   "HP"  "S"
   "CP"  "H"
   "SO"  "F"
   "KK"  "N"
   "OO"  "C"
   "SH"  "O"
   "PB"  "S"
   "KP"  "H"
   "OC"  "K"
   "BN"  "F"
   "HH"  "S"
   "CH"  "B"
   "PC"  "V"
   "SB"  "N"
   "KO"  "H"
   "BH"  "B"
   "SK"  "K"
   "KF"  "S"
   "NH"  "O"
   "HN"  "V"
   "VN"  "F"
   "BC"  "V"
   "VP"  "C"
   "KN"  "H"
   "PV"  "S"
   "HB"  "V"
   "VV"  "O"
   "PO"  "B"
   "FN"  "H"
   "PP"  "B"
   "BF"  "S"
   "CB"  "S"
   "NK"  "F"
   "NO"  "B"
   "CC"  "S"
   "OF"  "C"
   "HS"  "H"
   "SP"  "C"
   "VB"  "V"
   "BK"  "S"
   "CO"  "O"
   "NS"  "K"
   "PH"  "O"
   "BV"  "B"
   "CK"  "F"
   "VC"  "S"
   "HK"  "B"
   "BO"  "K"
   "HV"  "F"
   "KC"  "V"
   "CN"  "H"
   "FS"  "V"
   "VS"  "N"
   "CF"  "K"
   "VO"  "F"
   "FH"  "H"
   "NB"  "N"
   "PS"  "P"
   "OK"  "N"
   "CV"  "O"
   "CS"  "K"
   "HO"  "C"
   "KB"  "P"
   "NN"  "V"
   "KH"  "C"
   "OB"  "V"
   "BS"  "O"
   "FB"  "H"
   "FF"  "K"
   "HF"  "P"
   "FO"  "F"
   "VF"  "F"
   "OP"  "S"
   "VK"  "K"
   "OV"  "N"
   "FK"  "H"
   "FP"  "H"
   "NV"  "H"
   "NP"  "N"
   "SS"  "C"
   "FV"  "N"
   })

(defn inject-element [pair]
  (let [s (apply str pair)
        e (polymer-templates s)]
    (str (first s) e (last s))))

(defn expand-polymer [polymer]
  (let [pairs (partition 2 1 polymer)
        [hd & tl] (map inject-element pairs)]
    (->> (reduce (fn [acc [x & xs]] (apply conj acc xs)) (vec hd) tl)
         (apply str))))

(defn quantity [polymer]
  (let [fq (frequencies polymer)
        lc (second (apply min-key second fq))
        mc (second (apply max-key second fq))]
    [mc lc]))
        
(defn solution [steps]
  (loop [x 0 polymer "VNVVKSNNFPBBBVSCVBBC"]
    (if (= x steps) (quantity polymer)
        (recur (inc x) (expand-polymer polymer)))))       
        
        
        
        
        
        
        
        
