package com.leontg77.silentnight.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.leontg77.silentnight.Main;

/**
 * Chat listener class.
 * 
 * @author LeonTG77
 */
public class ChatListener implements Listener {

	@EventHandler
	public void on(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if (player.getWorld().getTime() != 18000) {
			return;
		}
		
		player.sendMessage(Main.PREFIX + "You can't chat during the night.");
		event.setCancelled(true);
	}

	@EventHandler
	public void on(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		
		if (player.getWorld().getTime() != 18000) {
			return;
		}

		String command = event.getMessage().split(" ")[0].substring(1);
		
		if (!command.equalsIgnoreCase("h") && !command.equalsIgnoreCase("gethealth") && !command.equalsIgnoreCase("health")) {
			return;
		}
		
		player.sendMessage(Main.PREFIX + "You can't do that during the night.");
		event.setCancelled(true);
	}
}