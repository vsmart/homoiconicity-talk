(ns visualise-code.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def current-ns *ns*)

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:color 0
   :angle 0})

(defn update-state [state]
  state )

(defn mangoes [] 0)
(defn mangoses [] 0)
(defn mangosses [] 0)


(defn draw-state [state]
  (let [fn-count (count (keys (ns-publics current-ns)))
        color (* (/ 1 (- fn-count 3) ) 255)]
    (q/background color)
    (q/stroke 0)
    (q/text (str fn-count) 20 20)
    (q/text (str color) 20 40)
    (q/text (str current-ns) 20 60)))

(q/defsketch visualise-code
  :title "visualise the code"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
