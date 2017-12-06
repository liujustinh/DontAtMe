package me.iamajiu.DontAtMe;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new AtEvent(), this);
	}
	
	
}
