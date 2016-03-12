package com.leontg77.silentnight.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

/**
 * Death listener class.
 * 
 * @author LeonTG77
 */
public class DeathListener implements Listener {
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board = manager.getMainScoreboard();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void on(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		Team team = board.getTeam("SILENTNIGHTTEAM");
		
		if (player.getWorld().getTime() == 6000) {
			player.getWorld().setGameRuleValue("reducedDebugInfo", "true");
			player.getWorld().setTime(18000);
			
			board.clearSlot(DisplaySlot.PLAYER_LIST);
			
			team.setNameTagVisibility(NameTagVisibility.NEVER);
		} else {
			player.getWorld().setGameRuleValue("reducedDebugInfo", "false");
			player.getWorld().setTime(6000);
			
			for (Objective obj : board.getObjectivesByCriteria("health")) {
				obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
				break;
			}
			
			team.setNameTagVisibility(NameTagVisibility.ALWAYS);
			event.setDeathMessage(null);
		}
	}
}