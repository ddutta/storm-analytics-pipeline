package com.simplepipeline.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import redis.clients.jedis.Jedis;

public class RedisCollectorBolt extends BaseRichBolt {

    private OutputCollector _collector;
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String host;
	final int port;
	final String pattern; 
	Jedis jedis;

    public RedisCollectorBolt(String host, int port, String pattern) {
    	this.host = host; //"127.0.0.1";
    	this.port = port; // 6379;
    	this.pattern = pattern; // "outQ";
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("json", "count"));
    }

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		_collector = collector;
		this.jedis = new Jedis(host);
	}

	public void execute(Tuple tuple) {
		// TODO Auto-generated method stub
    	String jsonStr = tuple.getString(0);
    	System.out.println("Got back ------------->" + jsonStr);
    	_collector.emit(new Values(jsonStr, jsonStr.length() ));
    	_collector.ack(tuple);	
    	jedis.publish(pattern, jsonStr);
	}  

}
