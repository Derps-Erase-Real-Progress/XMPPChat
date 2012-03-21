package com.github.sashacrofter.xmppchat;


import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class XMPPchat extends JavaPlugin
{
	private Logger log;
	private XMPPsession session;
	private HashMap<String, String> helpMap;
	
	public static void main (String[] args)
	{
		
	}
	
	public void onEnable()
	{
		this.log = this.getLogger();
		this.log.info(this.getName() + " has been enabled.");
		XMPPchatCommandExecutor executor = new XMPPchatCommandExecutor(this);
		getCommand("xmpp").setExecutor(executor);
		
		this.helpMap = new HashMap<String, String>();
		this.helpMap.put("help", ChatColor.GOLD + "/xmpp help [command]: " +
				ChatColor.WHITE + "Displays general help for xmpp or command " +
						"specific help.");
		this.helpMap.put("connect", ChatColor.GOLD + "/xmpp connect serverJabberID " +
				"JabberIDPassword chatroom conferenceServer : " +
				ChatColor.WHITE + "Connects to conferenceServer with serverJabberID " +
				"and joins the chatroom.");
		this.helpMap.put("disconnect", ChatColor.GOLD + "/xmpp disconnect : " +
				ChatColor.WHITE + "Disconnects from conferenceServer");
	}
	
	public void onDisable()
	{
		log.info(this.getName() + " has been disabled.");
	}
	
	//MESSAGE BRIDGES
	
	public void sendToMinecraftChat(String string)
	{
		getServer().broadcastMessage(string);
	}
	
	//INTERNAL CONVENIENCE METHODS
	
	/**
	 * Logs the message if the importance equal to or greater than
	 * the amount specified by the relevant importance
	 * @param importance justifies logging of the message;
	 * 0 is "status message,"
	 * 1 is "a command caused something expect to happen,"
	 * 2 is "error"
	 * @param message
	 */
	public void log(Integer importance, String message)
	{
		if (importance >= 0)
		{
			this.log.info(message);
		}
	}
	
	/**
	 * log message with importance 0
	 * @param message
	 */
	public void log(String message)
	{
		log(0, message);
	}
	
	public void message(CommandSender sender, String message)
	{
		sender.sendMessage(message);
	}
	
	public void warn(CommandSender sender, String warning)
	{
		message(sender, ChatColor.RED + warning);
	}
	
	/**
	 * Displays the whole help message to the sender
	 * @param sender
	 */
	public void help(CommandSender sender)
	{
		Object[] keyList = (Object[]) this.helpMap.keySet().toArray();
		
		this.message(sender, "====  HELP  ====");
		
		for(int i=0;i<keyList.length;i++)
		{
			this.message(sender, this.helpMap.get(keyList[i]).toString());
		}	
	}
	
	/**
	 * Displays help message for a specific subcommand
	 * @param sender
	 * @param subCommand
	 */
	public void help(CommandSender sender, String subCommand)
	{
		if (this.helpMap.containsKey(subCommand))
		{
			this.message(sender, this.helpMap.get(subCommand));
		}
	}
	
	//GETTERS AND SETTERS
	
	public XMPPsession getSession()
	{
		return this.session;
	}
	
	public void setSession(XMPPsession session)
	{
		this.session = session;
	}
}
