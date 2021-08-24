package it.unibs.ingesw;

import java.util.ArrayList;

public class Node implements GenericNode{
	
	private int netId;
	private int nodeId;
	private String nodeName;
	
	public Node(int netId, int nodeId, String nodeName) {
		
		this.netId = netId;
		this.nodeId = nodeId;
		this.nodeName = nodeName;
	}
	
	
	
	public int getNetId() {
		return netId;
	}

	public void setNetId(int netId) {
		this.netId = netId;
	}

	public int getId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) { //rimuovibile?
		this.nodeId = nodeId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public String getName() {
		return nodeName;
	}



	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return -1;
	}



	@Override
	public void addToken(int i) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void reduceToken(int i) {
		// TODO Auto-generated method stub
		
	}



	/*@Override
	public int getValue() {
		return 0;
	}*/
	

	
	
	
}
