package de.timelimit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimeLimit extends JavaPlugin implements Listener{
	
	public static int begin = 6;
	public static int end = 20;
	public static boolean isOn  = false;
	private final Thread thread = new Thread(new Limiting(this));
	
	@Override
	public void onEnable() {
		reloadConfig();
		begin = getConfig().getInt("begin");
		end = getConfig().getInt("end");
		isOn = getConfig().getBoolean("isOn");
		thread.start();
		getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("timelimit")&&args.length >= 0){
			boolean isReturn = false;
			if(args.length == 0){
				isOn = !isOn;
				save();
				isReturn = true;
			}
			if(args.length == 1&&args[0].equals("check")){
				if(!isOn)sender.sendMessage("Die Begrenzte Zeit ist von " + begin + " bis " + end + " Uhr.");
				isReturn = true;
			}
			else if(args.length == 2){
				isOn = true;
				begin = Integer.parseInt(args[0]);
				end = Integer.parseInt(args[1]);
				save();
				isReturn = true;
			}
			if(isOn){
				sender.sendMessage("Timelimit ist an mit Anfang um " + begin + " Uhr und Ende um " + end + " Uhr.");
			}else{
				sender.sendMessage("Timelimit ist aus.");
			}
			return isReturn;
		}
		return false;
	}
	
	private void save(){
		getConfig().set("begin", begin);
		getConfig().set("end", end);
		getConfig().set("isOn", isOn);
		saveConfig();
	}

}
