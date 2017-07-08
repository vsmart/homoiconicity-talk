(ns visualise-code.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def current-ns *ns*)

(defn setup []
  (q/frame-rate 3)
  (q/color-mode :hsb)
  (q/background 10)
  (let [state {:color 0 :angle 0}]
    state))

;(def rect-colour 40)

;(defn draw-rect [x y]
;  (q/rect x y 20 40))

;(defn draw-many-rects []
;  (doseq [x (range 10)] (draw-rect 0 x)))

(defn draw-background [state]
  (let [fn-count (:fn-count state)
        h (mod (* 18 fn-count) 360)
        s 160
        b 200]
    (q/background (q/color h s b))))

(defn draw-fn-circles [state]
  (doseq [n (range (:fn-count state))]
    (q/fill (- 230 (* 15 n)))
    (q/ellipse 50 (+ (* n 25) 50) 20 20)))

(defn draw-stats [state]
  (let [fn-count (:fn-count state)
       color (* (/ 1 (- fn-count 3)) 255)]
    (q/fill 0)
    (q/text-size 30)
    (q/text-align :right)
    (q/text (str "Function count: " fn-count) 450 250)
    (q/text (str "ns: " current-ns) 450 200)))

(defn clear-canvas []
  (q/background 10))

(defn every-n-frames [n]
  (= (mod (q/frame-count) n) 0))

(defn draw-state [state]
  (draw-background state)
  (draw-fn-circles state)
  (draw-stats state))

(defn update-state [state]
  (assoc state :fn-count (count (keys (ns-publics current-ns)))))

(q/defsketch visualise-code
  :title "visualise the code"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
