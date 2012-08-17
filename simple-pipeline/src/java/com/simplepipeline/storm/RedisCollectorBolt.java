package com.simplepipeline.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class RedisCollectorBolt extends BaseRichBolt {

    private OutputCollector _collector;
    
	final String host;
	final int port;
	final String pattern; 
	final Jedis jedis;

    public RedisCollectorBolt(String host, int port, String pattern) {
    	this.host = host; //"127.0.0.1";
    	this.port = port; // 6379;
    	this.pattern = pattern; // "outQ";
        this.jedis = new Jedis(host);
    }

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }

    public void execute(Tuple tuple) {

    	Object obj = tuple.getValue(0);
    	String pubString = "{ obj: " + obj +  "}";
    	jedis.publish(pattern, pubString);
    	_collector.ack(tuple);

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("obj", "count"));
    }
    

}
