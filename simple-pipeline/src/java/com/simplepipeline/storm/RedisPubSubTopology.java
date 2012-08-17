package com.simplepipeline.storm;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class RedisPubSubTopology {
	public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String ipattern = args[2];
        String opattern = args[3];
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("subspout", new RedisPubSubSpout(host,port, ipattern));
        builder.setBolt("collector", new RedisCollectorBolt(host, port, opattern), 3)
        	.shuffleGrouping("subspout");
                
        Config conf = new Config();
        conf.setDebug(true);
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(100000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
