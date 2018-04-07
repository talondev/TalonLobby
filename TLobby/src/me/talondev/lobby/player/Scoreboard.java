package me.talondev.lobby.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public abstract class Scoreboard {
  
  private org.bukkit.scoreboard.Scoreboard scoreboard;
  private Objective objective;
  
  private VirtualTeam[] teams = new VirtualTeam[15];
  
  public Scoreboard(Player player) {
    this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    this.objective = this.scoreboard.registerNewObjective("ScoreboardAPI", "dummy");
    this.objective.setDisplayName("§a§lTalon§f§lMC");
    this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    
    player.setScoreboard(this.scoreboard);
    
    Team team = scoreboard.registerNewTeam("NPCS");
    team.setNameTagVisibility(NameTagVisibility.NEVER);
    team.addEntry("§8[NPC] §1");
  }
  
  public abstract Scoreboard update();
  
  public Scoreboard set(int line) {
    return set(line, "");
  }
  
  public Scoreboard set(int line, String text) {
    if (line < 0 || line > 15) {
      throw new IllegalArgumentException("As linhas so vao de 1 a 15!");
    }
    
    VirtualTeam team = getOrCreate(line);
    team.setValue(text);
    team.update();
    return this;
  }
  
  public Scoreboard remove(int line) {
    if (line < 0 || line > 15) {
      throw new IllegalArgumentException("As linhas so vao de 1 a 15!");
    }
    
    VirtualTeam team = teams[line - 1];
    if (team != null) {
      team.destroy();
      teams[line - 1] = null;
    }
    
    return this;
  }
  
  public VirtualTeam getTeam(int line) {
    if (line < 0 || line > 15) {
      throw new IllegalArgumentException("Os times so vao de 1 a 15!");
    }
    
    return teams[line - 1];
  }
  
  public VirtualTeam getOrCreate(int line) {
    VirtualTeam team = getTeam(line);
    if (team == null) {
      team = new VirtualTeam(this, "fakeTeam" + line, line);
      teams[line - 1] = team;
    }
    
    return team;
  }
  
  public org.bukkit.scoreboard.Scoreboard getScoreboard() {
    return scoreboard;
  }
  
  public Objective getObjective() {
    return objective;
  }
}
