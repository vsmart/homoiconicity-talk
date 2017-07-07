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

(defn draw-circle [colour n init-x init-y]
  (q/no-stroke)
  (q/fill (mod (+ colour (/ init-y 4)) 255))
  (q/ellipse (+ init-x (* n 50)) (+ init-y (* n 8)) 30 30))

(defn draw-many-circles []
  (doseq [n (range (mod (q/frame-count) 12))
          y [120 160 200 240 280 320 360 400 440 480]]
    (draw-circle n n 20 y)))

(defn draw-background [state]
  (let [fn-count (:fn-count state)
        h (mod (* 20 fn-count) 360)
        s 130
        b 200]
    (q/background (q/color h s b))))

(defn draw-fn-circles [state]
  (doseq [n (range (:fn-count state))]
    (q/fill (rand-int 255))
    (q/ellipse 20 (+ (* n 25) 90) 20 20)))

(defn draw-status-bar [state]
  (let [fn-count (:fn-count state)
       color (* (/ 1 (- fn-count 3)) 255)]
    (q/fill 255)
    (q/rect 0 0 (q/width) 80)
    (q/fill 0)
    (q/text (str fn-count ": public defs in this ns") 20 20)
    (q/text (str color ": current color") 20 40)
    (q/text (str current-ns ": current ns") 20 60)
    (q/text (str (q/frame-count)) 400 20)))

(defn clear-canvas []
  (q/background 10))

(defn every-n-frames [n]
  (= (mod (q/frame-count) n) 0))

(defn draw-state [state]
  (when (every-n-frames 5)
    (draw-background state)
    (draw-fn-circles state))
  (draw-status-bar state))

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
