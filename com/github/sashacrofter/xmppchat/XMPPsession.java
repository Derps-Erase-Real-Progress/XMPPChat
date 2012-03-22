package com.github.sashacrofter.xmppchat;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPsession
{
	//Class<XMPPConnection> XMPPconnection = getClassConnection();
	
	private Chat chat;
	
	protected XMPPchat xmppchat;
	private HashMap<String, String> sessionParameters;
	private XMPPConnection connection;
	
	/**
	 * Instantiates Jabber session with the chatroom on the given conference server
	 * @param username - a valid username to connect to the conference server with
	 * @param password - the password associated with the given username (not stored)
	 * @param chatroom - the chatroom on the server to enter a session with
	 * @param conferenceServer - the server to connect to (eg. conference.jabber.org)
	 * @throws XMPPException if fails to connect to the server
	 */
	public XMPPsession (XMPPchat xmppchat, String username, String password, String chatroom,
			String conferenceServer) throws Exception
	{
		this.xmppchat = xmppchat;
		
		this.sessionParameters = new HashMap<String, String>();
		
		this.sessionParameters.put("username", username);
		this.sessionParameters.put("chatroom", chatroom);
		this.sessionParameters.put("server", conferenceServer);
		
		try
		{
			connection = new XMPPConnection("conference.jabber.org");
			connection.connect();
			connection.login(username, password);
		} catch (Exception e) { xmppchat.log(2, "error at XMPPConnection"); e.printStackTrace(); }
	}
	
	public void sendMessage(String message)
	{
		try
		{
			chat.sendMessage(message);
		} catch (Exception e)
		{
			this.xmppchat.log(2, e.getMessage());
		}
	}
	
	public void disconnect()
	{
		connection.disconnect();
	}
	
	public String getPlayers() //TODO request players from chatroom and return comma separated list
	{
		return "";
	}
}
