package com.leontg77.silentnight.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.HandlerList;

import com.leontg77.silentnight.Main;
import com.leontg77.silentnight.listeners.ChatListener;
import com.leontg77.silentnight.listeners.DeathListener;

/**
 * SilentNight command class.
 * 
 * @author LeonTG77
 */
public class SilentNightCommand implements CommandExecutor, TabCompleter {
	private static final String PERMISSION = "silentnight.manage";

	private final Main plugin;

	private final DeathListener death;
	private final ChatListener chat;
	
	/**
	 * SilentNight command class constructor.
	 * 
	 * @param plugin The main class.
	 * @param death The death listener.
	 * @param chat The chat listener.
	 */
	public SilentNightCommand(Main plugin, ChatListener chat, DeathListener death) {
		this.plugin = plugin;

		this.death = death;
		this.chat = chat;
	}
	
	public boolean enabled = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.PREFIX + "Usage: /silentnight <info|enable|disable>");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(Main.PREFIX + "Plugin creator: §aLeonTG77");
			sender.sendMessage(Main.PREFIX + "Version: §av" + plugin.getDescription().getVersion());
			sender.sendMessage(Main.PREFIX + "Description:");
			sender.sendMessage("§8» §f" + plugin.getDescription().getDescription());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("enable")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (enabled) {
				sender.sendMessage(Main.PREFIX + "Silent Night is already enabled.");
				return true;
			}
			
			plugin.broadcast(Main.PREFIX + "Silent Night has been enabled.");
			enabled = true;
			
			Bukkit.getPluginManager().registerEvents(chat, plugin);
			Bukkit.getPluginManager().registerEvents(death, plugin);
			
			for (World world : Bukkit.getWorlds()) {
				world.setGameRuleValue("doDaylightCycle", "false");
				world.setTime(6000);
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("disable")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (!enabled) {
				sender.sendMessage(Main.PREFIX + "Silent Night is not enabled.");
				return true;
			}

			plugin.broadcast(Main.PREFIX + "Silent Night has been disabled.");
			enabled = false;
			
			HandlerList.unregisterAll(chat);
			HandlerList.unregisterAll(death);
			
			for (World world : Bukkit.getWorlds()) {
				world.setGameRuleValue("doDaylightCycle", "true");
			}
			return true;
		}
		
		sender.sendMessage(Main.PREFIX + "Usage: /silentnight <info|enable|disable>");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> toReturn = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		
		if (args.length != 1) {
			return toReturn;
		}
		
		list.add("info");
		
		if (sender.hasPermission(PERMISSION)) {
			list.add("enable");
			list.add("disable");
		}

		// make sure to only tab complete what starts with what they 
		// typed or everything if they didn't type anything
		for (String str : list) {
			if (args[0].isEmpty() || str.startsWith(args[0].toLowerCase())) {
				toReturn.add(str);
			}
		}
		
		return toReturn;
	}
}