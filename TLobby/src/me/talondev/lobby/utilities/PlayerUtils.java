package me.talondev.lobby.utilities;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class PlayerUtils {
  
  public static void refreshPlayer(Player player) {
    player.setMaxHealth(20.0);
    player.setHealth(20.0);
    player.setFoodLevel(20);
    player.setExhaustion(0.0f);
    player.setExp(0.0f);
    player.setLevel(0);
    player.setFireTicks(0);
    
    player.getInventory().clear();
    ItemStack server = new ItemStack(Material.COMPASS);
    ItemMeta meta = server.getItemMeta();
    meta.setDisplayName("§aServidores");
    server.setItemMeta(meta);
    
    net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(server);
    nms.getTag().set("ench", new NBTTagList());
    player.getInventory().setItem(0, CraftItemStack.asCraftMirror(nms));
    
    player.updateInventory();
    
    player.setGameMode(GameMode.ADVENTURE);
    player.setAllowFlight(player.hasPermission("lobby.voar"));
    player.setFlying(player.getAllowFlight());
  }
}
