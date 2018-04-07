package me.talondev.lobby.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

public class EntityNPCPlayer extends EntityPlayer implements NPCHolder {

  private NPC npc;

  public EntityNPCPlayer(Location spawn, NPC npc) {
    super(((CraftServer) Bukkit.getServer()).getServer(),
        ((CraftWorld) spawn.getWorld()).getHandle(), makeProfile(npc),
        new PlayerInteractManager(((CraftWorld) spawn.getWorld()).getHandle()));
    this.npc = npc;
    this.playerInteractManager.setGameMode(EnumGamemode.SURVIVAL);
    this.playerConnection = new NPCConnection(this);
    this.playerConnection.networkManager.a(playerConnection);
    this.datawatcher.watch(10, (byte) -1);
    
    this.setPosition(spawn.getX(), spawn.getY(), spawn.getZ());
  }
  
  public NPC getNPC() {
    return npc;
  }
  
  @Override
  public void t_() {
    super.t_();
  }

  @Override
  public boolean damageEntity(DamageSource damagesource, float f) {
    if (npc == null) {
      return super.damageEntity(damagesource, f);
    }

    return false;
  }
  
  @Override
  public CraftPlayer getBukkitEntity() {
    if (bukkitEntity == null && npc != null) {
      bukkitEntity = new PlayerNPC(this);
    }
    
    return super.getBukkitEntity();
  }
  
  static GameProfile makeProfile(NPC npc) {
    GameProfile profile = new GameProfile(npc.getUniqueId(), npc.getName());
    if (npc.getSignature() != null && npc.getValue() != null) {
      profile.getProperties().clear();
      profile.getProperties().put("textures", new Property("textures", npc.getValue(), npc.getSignature()));
    }
    
    return profile;
  }
  
  static class PlayerNPC extends CraftPlayer implements NPCHolder {
    
    public PlayerNPC(EntityNPCPlayer ep) {
      super(ep.server.server, ep);
    }
    
    @Override
    public NPC getNPC() {
      return ((EntityNPCPlayer) entity).getNPC();
    }
  }
}
