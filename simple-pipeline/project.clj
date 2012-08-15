(defproject simple-pipeline "1.0.0-SNAPSHOT"
  :java-source-path "src/java"
  :source-path "src/clj"
  :javac-options {:debug "true" :fork "true"}
  :aot :all
  :description "A simple pipeline for analytics using Storm"
  :dependencies [[redis.clients/jedis "2.0.0"]
                ]
  :dev-dependencies [[storm "0.8.0"]
                     [org.clojure/clojure "1.4.0"]
                    ]
)


