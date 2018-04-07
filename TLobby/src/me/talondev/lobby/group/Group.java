package me.talondev.lobby.group;

import org.bukkit.entity.Player;

public enum Group {
  MASTER("Master", "§6[Master] ", "group.master"),
  MODERADOR("Moderador", "§2[Moderador] ", "group.moderator"),
  MEMBRO("Membro", "§7", "");
  
  private String name, prefix, permission;
  
  Group(String name, String prefix, String permission) {
    this.name = name;
    this.prefix = prefix;
    this.permission = permission;
  }
  
  public String getName() {
    return name;
  }
  
  public String getPrefix() {
    return prefix;
  }
  
  public boolean has(Player player) {
    return permission.isEmpty() || player.hasPermission(permission);
  }
  
  public static Group getGroup(Player player) {
    for (Group group : values()) {
      if (group.has(player)) {
        return group;
      }
    }
    
    return MEMBRO;
  }
}
