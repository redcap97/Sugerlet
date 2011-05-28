package com.akr97.sugerlet.model;

public class ImData {
	public String data;
	public int type;
	public String label;
	public int protocol;
	public String customProtocol;
	
	public ImData(String data, int type, String label, 
			int protocol, String customProtocol){
		this.data = data;
		this.type = type;
		this.label = label;
		this.protocol = protocol;
		this.customProtocol = customProtocol;
	}
	
	@Override
	public String toString(){
		return String.format("data: %s, type: %d, label: %s," 
					+ " protocol: %s, customProtocol: %s",
				data, type, label, protocol, customProtocol);
	}
}
