package com.leontg77.silentnight;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.leontg77.silentnight.commands.SilentNightCommand;
import com.leontg77.silentnight.listeners.ChatListener;
import com.leontg77.silentnight.listeners.DeathListener;

/**
 * Main class of the plugin.
 * 
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
	public static final String PREFIX = "§7Silent Night §8» §7";
	
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board = manager.getMainScoreboard();

	@Override
	public void onDisable() {
		PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " has been disabled.");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " v" + file.getVersion() + " has been enabled.");
		getLogger().info("The plugin is made by LeonTG77.");
		
		ChatListener chat = new ChatListener();
		DeathListener death = new DeathListener();
		
		final SilentNightCommand command = new SilentNightCommand(this, chat, death);
		
		// register command.
		getCommand("silentnight").setExecutor(command);
		getCommand("silentnight").setTabCompleter(command);
		
		new BukkitRunnable() {
			public void run() {
				if (!command.enabled) {
					return;
				}
				
				Team team = board.getTeam("SILENTNIGHTTEAM");
				
				if (team == null) {
					team = board.registerNewTeam("SILENTNIGHTTEAM");	
				}
				
				for (Player online : Bukkit.getOnlinePlayers()) {
					team.addEntry(online.getName());
				}
			}
		}.runTaskTimer(this, 100, 100);
	}
	
	/**
	 * Broadcasts a message to everyone online.
	 * 
	 * @param message the message.
	 */
	public void broadcast(String message) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			online.sendMessage(message);
		}
		
		Bukkit.getLogger().info(message);
	}
}