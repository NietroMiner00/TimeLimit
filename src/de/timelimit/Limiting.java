package de.timelimit;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Limiting implements Runnable{
	private Plugin n;
	public Limiting(Plugin n){
		super();
		this.n = n;
	}
	
	@Override
	public void run() {
		while(true){
			if(TimeLimit.isOn){
				Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		        if((Integer.parseInt(sdf.format(cal.getTime()).split(":")[0]) > TimeLimit.end||Integer.parseInt(sdf.format(cal.getTime()).split(":")[0]) < TimeLimit.begin)){
		        	for(Player p: Bukkit.getServer().getOnlinePlayers()){
		        		if(!p.isOp())Bukkit.getServer().getScheduler().runTaskLater(n, new Runnable() {
		    	            public void run() {
		    	                p.kickPlayer("Its too late...");
		    	            }
		    	          }, 10L);
		        	}
		        }
			}
        }
	}
}
