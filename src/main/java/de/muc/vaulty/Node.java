package de.muc.vaulty;

import java.io.Serializable;

public class Node extends Thread implements Serializable{
	public String nodeID;
	public String NodeClass;
	
	
	
	public Node(String nodeID) {
		this.nodeID = nodeID;
	}
}
