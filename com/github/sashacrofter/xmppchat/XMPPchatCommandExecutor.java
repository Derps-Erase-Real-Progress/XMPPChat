package com.github.sashacrofter.xmppchat;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class XMPPchatCommandExecutor implements CommandExecutor
{
	private XMPPchat xmppchat;
	
	public XMPPchatCommandExecutor(XMPPchat xmppchat)
	{
		this.xmppchat = xmppchat;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		String nopermission = "No permission: "; //Pre-written permission message
		String permission; //Permission required for a command
		if(args.length == 0)
		{
			this.xmppchat.help(sender);
		}
		else if(args[0].equalsIgnoreCase("help"))
		{
			if (args.length == 1)
			{
				this.xmppchat.help(sender);
			}
			else
			{
				this.xmppchat.help(sender, args[1]);
			}
		}
		else if(args[0].equalsIgnoreCase("connect"))
		{
			permission="xmpp.administrate";
			nopermission = nopermission+permission;
			if(sender.hasPermission(permission))
			{
				if (args.length < 5) return false;
				sender.sendMessage("Joining chatroom...");
				try
				{
					this.xmppchat.setSession(new XMPPsession(xmppchat, args[1],
							args[2], args[3], args[4]));
				} catch (Exception e)
				{
					sender.sendMessage("Connection failed.");
					e.printStackTrace();
				}
			}
			else this.xmppchat.warn(sender, nopermission);
		}
		else if(args[0].equalsIgnoreCase("disconnect"))
		{
			permission="xmpp.administrate";
			nopermission=nopermission+permission;
			if(sender.hasPermission(permission))
			{
				if(this.xmppchat.getSession() != null)
				{
					sender.sendMessage("Disconnecting from chatroom...");
					this.xmppchat.getSession().disconnect();
					this.xmppchat.log(1, sender + " caused disconnect from chatroom");
					sender.sendMessage("Disconnected from chatroom.");
				}
				else this.xmppchat.warn(sender, "No session.");
			}
			else this.xmppchat.warn(sender, nopermission);
		}
		else if(args[0].equalsIgnoreCase("ls") || args[0].equalsIgnoreCase("list"))
		{
			this.xmppchat.message(sender, xmppchat.getSession().getPlayers());
		}
		else return false; //Efficient code, bad practice
		return true;
	}

}
