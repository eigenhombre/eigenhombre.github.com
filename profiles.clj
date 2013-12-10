{:user {:plugins [[lein-noir "1.2.1"]
                  [lein-eclipse "1.0.0"]
                  [lein-drip "0.1.1-SNAPSHOT"]
                  [lein-midje "3.1.1"]
                  [com.jakemccrary/lein-test-refresh "0.1.2"]
                  [org.bodil/lein-noderepl "0.1.10"]
                  [lein-exec "0.3.1"]
                  [lein-oneoff "0.3.0"]
                  [lein-marginalia "0.7.1"]
                  [swank-clojure "1.4.0"]]}}
;; {:user {:dependencies [[clj-stacktrace "0.2.5"]]
;;         :injections [(let [orig (ns-resolve (doto 'clojure.stacktrace require)
;;                                             'print-cause-trace)
;;                            new (ns-resolve (doto 'clj-stacktrace.repl require)
;;                                            'pst)]
;;                        (alter-var-root orig (constantly @new)))]}}
