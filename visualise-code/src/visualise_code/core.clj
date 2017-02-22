(ns visualise-code.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def current-ns *ns*)

(defn setup []
  (q/frame-rate 3)
  (q/color-mode :hsb)
  {:color 0
   :angle 0})

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

(defn draw-status-bar []
  (let [fn-count (count (keys (ns-publics current-ns)))
       color (* (/ 1 (- fn-count 3) ) 255)]
    (q/background color)
    (q/fill 255)
    (q/rect 0 0 (q/width) 80)
    (q/fill 0)
    (q/text (str fn-count ": public defs in this ns") 20 20)
    (q/text (str color ": current color") 20 40)
    (q/text (str current-ns ": current ns") 20 60)))

(defn draw-state [state]
  (draw-status-bar)
  (draw-many-circles))

(defn update-state [state]
  state)

(q/defsketch visualise-code
  :title "visualise the code"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
