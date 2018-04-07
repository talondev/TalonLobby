package me.talondev.lobby.group;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GroupManager {

  public static void onJoin(Player player) {
    Group group = Group.getGroup(player);
    Scoreboard s = player.getScoreboard();
    player.setDisplayName(group.getPrefix() + player.getName());

    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
      Scoreboard sb = players.getScoreboard();

      Team team = sb.getTeam(group.ordinal() + group.getName());
      if (team == null) {
        team = sb.registerNewTeam(group.ordinal() + group.getName());
        team.setPrefix(group.getPrefix());
      }

      team.addEntry(player.getName());
      
      if (player.equals(players)) {
        continue;
      }

      Group g2 = Group.getGroup(players);
      team = s.getTeam(g2.ordinal() + g2.getName());
      if (team == null) {
        team = s.registerNewTeam(g2.ordinal() + g2.getName());
        team.setPrefix(g2.getPrefix());
      }

      team.addEntry(players.getName());
    }
  }

  public static void onQuit(Player player) {
    Group group = Group.getGroup(player);
    
    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
      Scoreboard sb = players.getScoreboard();

      Team team = sb.getTeam(group.ordinal() + group.getName());
      if (team != null && team.hasEntry(player.getName())) {
        team.removeEntry(player.getName());
      }
    }
  }
}
