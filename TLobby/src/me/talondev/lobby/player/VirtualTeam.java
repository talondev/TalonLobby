package me.talondev.lobby.player;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;
import com.google.common.base.Preconditions;

public class VirtualTeam {
  
  private Scoreboard instance;
  
  private String name;
  private String prefix;
  private String entry;
  private String suffix;
  
  private int line;
  
  public VirtualTeam(Scoreboard instance, String name, int line) {
    this.instance = instance;
    this.name = name;
    this.line = line;
  }
  
  public void update() {
    Team team = this.instance.getScoreboard().getTeam(name);
    this.instance.getObjective().getScore(entry).setScore(line);
    if (team == null) {
      team = this.instance.getScoreboard().registerNewTeam(name);
      team.addEntry(entry);
    }
    
    team.setPrefix(prefix);
    team.setSuffix(suffix);
  }
  
  public void destroy() {
    this.instance.getScoreboard().resetScores(entry);
    Team team = this.instance.getScoreboard().getTeam(name);
    if (team != null) {
      team.unregister();
      team = null;
    }
    
    this.name = null;
    this.entry = null;
    this.prefix = null;
    this.suffix = null;
    this.line = -1;
  }
  
  public void setValue(String text) {
    Preconditions.checkState(text.length() <= 32, "O valor nao pode ser maior que 32! ele possui " + text.length() + "!");
    
    text = ChatColor.translateAlternateColorCodes('&', text);
    this.prefix = text.substring(0, Math.min(text.length(), 16));
    this.entry = ChatColor.values()[line - 1].toString() + "§r";
    if (this.prefix.endsWith("§") && this.prefix.length() == 16) {
      this.prefix = this.prefix.substring(0, this.prefix.length() - 1);
      text = text.substring(prefix.length());
    } else {
      text = text.substring(Math.min(text.length(), prefix.length()));
    }
    
    this.suffix = ChatColor.getLastColors(prefix) + text;
    this.suffix = this.suffix.substring(0, Math.min(16, this.suffix.length()));
    if (this.suffix.endsWith("§")) {
      this.suffix = this.suffix.substring(0, this.suffix.length() - 1);
    }
  }
}
