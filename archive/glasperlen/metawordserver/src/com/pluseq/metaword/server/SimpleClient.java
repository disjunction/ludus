package com.pluseq.metaword.server;

import java.net.URL;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class SimpleClient {

	public SimpleClient() {
		try {
			System.out.println("translating word: word\n");
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL(
					"http://localhost:8080/ttt/xmlrpc"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] { "get langFrom" };
			String result = (String) client.execute("YadRpc.consoleCommand", params);
			System.out.println("The returned values is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SimpleClient();
	}
}