(defproject simple-pipeline "1.0.0-SNAPSHOT"
  :java-source-path "src/java"
  :javac-options {:debug "true" :fork "true"}
  :description "A simple pipeline for analytics using Storm"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [redis.clients/jedis "2.0.0"]]
  :dev-dependencies [[storm "0.8.0"]]
)


