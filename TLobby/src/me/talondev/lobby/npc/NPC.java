package me.talondev.lobby.npc;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import com.google.common.base.Preconditions;
import me.talondev.lobby.utilities.Utils;
import net.minecraft.server.v1_8_R3.EntityTracker;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.WorldServer;

public class NPC {
  
  private UUID uuid;
  private String name;
  private Entity entity;
  
  private String value, signature;
  
  public NPC(UUID uuid, String name) {
    this.uuid = uuid;
    this.name = name;
  }
  
  @SuppressWarnings("unchecked")
  public void spawn(Location location) {
    Preconditions.checkState(!isSpawned(), "O npc ja foi spawnado");
    
    WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
    EntityNPCPlayer ep = new EntityNPCPlayer(location, this);
    ep.spawnIn(world);
    world.addEntity(ep, SpawnReason.CUSTOM);
    world.players.remove(ep);
    ep.server.getPlayerList().players.remove(ep);
    entity = ep.getBukkitEntity();
    
    ep.pitch = location.getPitch();
    float yaw = Utils.clampYaw(location.getYaw());
    
    ep.yaw = yaw;
    ep.aK = yaw;
    ep.aI = yaw; // Olhar para a localização
    ep.aL = yaw;
    
    try {
      Field SET_TRACKERS = EntityTracker.class.getDeclaredField("c");
      SET_TRACKERS.setAccessible(true);
      EntityTrackerEntry entry = world.getTracker().trackedEntities.get(ep.getId());
      if (entry != null) {
        PlayerlistTracker tracker = new PlayerlistTracker(entry);
        world.getTracker().trackedEntities.a(ep.getId(), tracker);
        Set<Object> set = (Set<Object>) SET_TRACKERS.get(world.getTracker());
        set.remove(entry);
        set.add(tracker);
      }
    } catch (ReflectiveOperationException ex) {
      ex.printStackTrace();
    }
  }
  
  public void despawn() {
    Preconditions.checkState(isSpawned(), "O npc nao esta spawnado");
    entity.remove();
    entity = null;
  }
  
  public void setSkin(String value, String signature) {
    this.value = value;
    this.signature = signature;
  }
  
  public String getValue() {
    return value;
  }
  
  public String getSignature() {
    return signature;
  }
  
  public boolean isSpawned() {
    return entity != null && entity.isValid();
  }
  
  public UUID getUniqueId() {
    return uuid;
  }
  
  public String getName() {
    return name;
  }
}
