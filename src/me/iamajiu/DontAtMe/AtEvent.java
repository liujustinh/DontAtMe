package me.iamajiu.DontAtMe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AtEvent implements Listener {
	
	@EventHandler
	public void playerAtEvent(AsyncPlayerChatEvent event) {
		ArrayList<String> at_playerlist = new ArrayList<String>(); 
		Player sender = event.getPlayer(); 
		if (event.getMessage() == null || event.getMessage().indexOf('@') < 0) {
			return; 
		}
		String sender_msg = event.getMessage();
		for (String s : sender_msg.split(" ")) {
			if (s.contains("@")) {
				StringBuilder sb = new StringBuilder(s); 
				sb.deleteCharAt(0); 
				String at_player = sb.toString(); 
				at_playerlist.add(at_player); 
			}
		}
		
		HashMap<String, Integer> player_list = validPlayerChecker(at_playerlist); 
		HashMap<String, String> valid_players = new HashMap<String, String>(); 
		for (Entry<String, Integer> pair : player_list.entrySet()) {
			if (pair.getValue() == 1) {
				String temp_old = "@" + pair.getKey(); 
				String temp_new = ChatColor.GREEN + temp_old + ChatColor.WHITE; 
				valid_players.put(temp_old, temp_new);
			}
		}
		String final_msg = event.getMessage(); 
		String message = new String(); 
		if (valid_players.size() == 0) {
			return; 
		}
		for (Entry<String, String> p : valid_players.entrySet()) {
			message = final_msg.replaceAll(p.getKey(), p.getValue()); 
			Bukkit.getLogger().info(message);
		}
		Bukkit.getLogger().info(message);
		event.setMessage(message);
		makeNoise(player_list, sender); 	
	}
	
	public HashMap<String, Integer> validPlayerChecker(ArrayList<String> player_list) {
		HashMap<String, Integer> players = new HashMap<String, Integer>(); 
		for (int i = 0; i < player_list.size(); ++i) {
			String player_name = player_list.get(i); 
			if (Bukkit.getPlayer(player_name) == null || !Bukkit.getPlayer(player_name).isOnline() || !Bukkit.getPlayer(player_name).hasPlayedBefore()) {
				continue; 
			}
			else {
				players.put(player_name, 1); 
			}
		}
		return players; 
	}
	
	public void makeNoise(HashMap<String, Integer> player_list, Player sender) {
		if (player_list == null || player_list.entrySet() == null) {
			return; 
		}
		for (Entry<String, Integer> pair : player_list.entrySet()) {
			if (pair.getValue() == 0) {
				sender.sendMessage(ChatColor.RED + "Player " + pair.getKey() + " is not currently online or has not played on the server before!");
			}
			else {
				Bukkit.getPlayer(pair.getKey()).playSound(Bukkit.getPlayer(pair.getKey()).getLocation(), Sound.NOTE_PLING , 2F, 1F); 
			}
		}
		return;
	}
	
	
	
}
