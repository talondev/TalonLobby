package me.talondev.lobby.holograms;

import org.bukkit.Location;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.talondev.lobby.TalonLobby;
import net.md_5.bungee.api.ChatColor;

public class HologramLine {
  
  private Hologram hologram;
  private Location location;
  private String text;
  
  private ArmorHologram entity;
  
  public HologramLine(Hologram hologram, Location location, String text) {
    this.hologram = hologram;
    this.location = location;
    this.text = ChatColor.translateAlternateColorCodes('&', text);
  }
  
  private ArmorHologram createEntity(Location location, String text) {
    EntityHologramStand entity = new EntityHologramStand(location, this);
    
    try {
      int chunkX = location.getBlockX() >> 4;
      int chunkZ = location.getBlockZ() >> 4;
      if (!location.getWorld().isChunkLoaded(chunkX, chunkZ)) {
        TalonLobby.LOGGER.info("Falha ao spawnar holograma, chunk nao carregada!");
        return null;
      }
      
      if (entity.world.addEntity(entity, SpawnReason.CUSTOM)) {
        entity.setText(text);
        return entity;
      } else {
        return null;
      }
    } catch (Exception e) {
      TalonLobby.LOGGER.info("Falha ao spawnar holograma: " + e.getMessage());
      return null;
    }
  }
  
  public void spawn() {
    if (entity == null) {
      entity = createEntity(location, this.text);
    }
  }
  
  public void despawn() {
    if (entity != null) {
      entity.killEntity();
      entity = null;
    }
  }
  
  public void setText(String text) {
    this.text = ChatColor.translateAlternateColorCodes('&', text);
    
    if (entity != null) {
      entity.setText(this.text);
    }
  }
  
  public String getText() {
    return text;
  }
  
  public Location getLocation() {
    return location;
  }
  
  public Hologram getHologram() {
    return hologram;
  }
  
  public ArmorHologram getEntity() {
    return entity;
  }
}
