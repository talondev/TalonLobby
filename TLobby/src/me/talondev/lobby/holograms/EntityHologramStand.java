package me.talondev.lobby.holograms;

import java.lang.reflect.Field;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Vec3D;

public class EntityHologramStand extends EntityArmorStand implements ArmorHologram {

  private HologramLine line;

  public EntityHologramStand(Location toSpawn, HologramLine line) {
    super(((CraftWorld) toSpawn.getWorld()).getHandle());
    setArms(false);
    setBasePlate(true);
    setInvisible(true);
    setGravity(false);
    setSmall(true);
    this.line = line;
    setPosition(toSpawn.getX(), toSpawn.getY(), toSpawn.getZ());

    try {
      Field blockedSlots = EntityArmorStand.class.getDeclaredField("bi");
      blockedSlots.setAccessible(true);
      blockedSlots.set(this, Integer.MAX_VALUE);
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
    }

    a(new NullBoundingBox());
  }

  @Override
  public void t_() {
    this.ticksLived = 0;
    if (dead) {
      super.t_();
    }
  }

  @Override
  public void a(NBTTagCompound nbttagcompound) {}

  @Override
  public void b(NBTTagCompound nbttagcompound) {}

  @Override
  public boolean c(NBTTagCompound nbttagcompound) {
    return false;
  }

  @Override
  public void e(NBTTagCompound nbttagcompound) {}

  @Override
  public void setCustomName(String s) {}

  @Override
  public void setCustomNameVisible(boolean flag) {}

  @Override
  public boolean a(EntityHuman entityhuman, Vec3D vec3d) {
    return true;
  }

  @Override
  public void setText(String text) {
    if (text != null && text.length() > 300) {
      text = text.substring(0, 300);
    }

    super.setCustomName(text == null ? "" : text);
    super.setCustomNameVisible(text != null && !text.isEmpty());
  }

  @Override
  public void killEntity() {
    super.die();
  }

  @Override
  public HologramLine getLine() {
    return line;
  }

  @Override
  public Hologram getHologram() {
    return line == null ? null : line.getHologram();
  }

  @Override
  public CraftEntity getBukkitEntity() {
    if (bukkitEntity == null) {
      bukkitEntity = new CraftHologramStand(this);
    }

    return super.getBukkitEntity();
  }

  static class CraftHologramStand extends CraftArmorStand {

    public CraftHologramStand(EntityHologramStand entity) {
      super(entity.world.getServer(), entity);
    }

    @Override
    public void remove() {}
  }
}
