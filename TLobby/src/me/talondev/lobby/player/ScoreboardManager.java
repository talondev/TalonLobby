package me.talondev.lobby.player;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.talondev.lobby.TalonLobby;
import me.talondev.lobby.group.Group;

public class ScoreboardManager {
  
  private static Map<UUID, Scoreboard> scoreboards = new ConcurrentHashMap<>();
  
  public static void loadScoreboard(Player player) {
    Scoreboard scoreboard = scoreboards.get(player.getUniqueId());
    if (scoreboard != null) {
      return;
    }
    
    scoreboard = new Scoreboard(player) {
      @Override
      public Scoreboard update() {
        Group g = Group.getGroup(player);
        
        set(7).
        set(6, "Grupo: " + ChatColor.getLastColors(g.getPrefix()) + g.getName()).
        set(5).
        set(4, "Lobby: &a#1").
        set(3, "Jogadores: &a" + TalonLobby.all).
        set(2).
        set(1, "&etalonnetwork.com.br");
        return this;
      }
    }.update();
    scoreboards.put(player.getUniqueId(), scoreboard);
  }
  
  public static void setup() {
    Bukkit.getScheduler().scheduleSyncRepeatingTask(TalonLobby.getInstance(), () -> {
      for (Iterator<Entry<UUID, Scoreboard>> itr = scoreboards.entrySet().iterator(); itr.hasNext();) {
        Entry<UUID, Scoreboard> entry = itr.next();
        
        UUID uuid = entry.getKey();
        Scoreboard scoreboard = entry.getValue();
        if (Bukkit.getPlayer(uuid) == null) {
          itr.remove();
          continue;
        }
        
        scoreboard.update();
      }
    }, 0, 20);
  }
}
