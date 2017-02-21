(ns visualise-code.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def current-ns *ns*)

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:color 0
   :angle 0})

(def circle-colour 90)

(defn draw-circle [n init-x init-y]
  (q/fill (+ circle-colour (* n 5)))
  (q/ellipse (+ init-x (* n 50)) (+ init-y (* n 8)) 30 30))

(defn draw-many-circles []
  (doseq [n (range 10)
          y [160 200 240 280 320 360 400 440 480]]
    (draw-circle n 20 y)))

(defn draw-status-bar [state]
  (let [fn-count (count (keys (ns-publics current-ns)))
       color (* (/ 1 (- fn-count 3) ) 255)]
    (q/background color)
    (q/fill 255)
    (q/rect 0 0 (q/width) 100)
    (q/fill 0)
    (q/text (str fn-count) 20 20)
    (q/text (str color) 20 40)
    (q/text (str current-ns) 20 60)))

(defn draw-state [state]
  (draw-status-bar state)
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
