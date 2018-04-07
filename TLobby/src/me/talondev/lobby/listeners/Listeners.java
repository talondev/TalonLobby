package me.talondev.lobby.listeners;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.talondev.lobby.TalonLobby;
import me.talondev.lobby.api.ServerAPI;
import me.talondev.lobby.group.GroupManager;
import me.talondev.lobby.npc.NPC;
import me.talondev.lobby.npc.NPCLibrary;
import me.talondev.lobby.player.ScoreboardManager;
import me.talondev.lobby.utilities.PlayerUtils;

public class Listeners implements Listener {

  public static void makeListeners() {
    Bukkit.getPluginManager().registerEvents(new Listeners(), TalonLobby.getInstance());

    ScoreboardManager.setup();
  }
  
  private Map<String, Long> antiFlood = new HashMap<>();

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    evt.setJoinMessage(null);

    PlayerUtils.refreshPlayer(evt.getPlayer());
    ScoreboardManager.loadScoreboard(evt.getPlayer());
    GroupManager.onJoin(evt.getPlayer());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    evt.setQuitMessage(null);
    GroupManager.onQuit(evt.getPlayer());
    
    antiFlood.remove(evt.getPlayer().getName());
  }

  @EventHandler
  public void onPlayerKick(PlayerKickEvent evt) {
    GroupManager.onQuit(evt.getPlayer());
    
    antiFlood.remove(evt.getPlayer().getName());
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent evt) {
    evt.setCancelled(true);
    Player player = evt.getPlayer();
    ItemStack item = player.getItemInHand();

    if (item.hasItemMeta() && evt.getAction().name().contains("RIGHT")) {
      String display = item.getItemMeta().getDisplayName();
      if (display.equals("§aServidores")) {
        player.openInventory(ServerAPI.inventory);
      }
    }
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
    Player player = evt.getPlayer();

    NPC npc = NPCLibrary.getNPC(evt.getRightClicked());
    if (npc != null) {
      if (npc.getName().equals("§8[NPC] §1")) {
        player.sendMessage("§aConectando..");
        TalonLobby.sendServer(player, "skywars");
      }
    }
  }
  
  @EventHandler
  public void AsyncPlayerChat(AsyncPlayerChatEvent evt) {
    Player player = evt.getPlayer();
    
    long start = antiFlood.containsKey(player.getName()) ? antiFlood.get(player.getName()) : 0;
    if (start > System.currentTimeMillis()) {
      double value = (start - System.currentTimeMillis()) / 1000.0;
      if (value >= 0.1) {
        String valueString = String.valueOf(value);
        valueString = valueString.substring(0, Math.min(3, valueString.length()));
        if (valueString.endsWith("0")) {
          valueString = valueString.substring(0, 1);
        }
        
        player.sendMessage("§cAguarde " + valueString + "s para utilizar o chat novamente.");
        evt.setCancelled(true);
        return;
      }
    }
    
    antiFlood.put(player.getName(), System.currentTimeMillis() + 4000); // 4segundos.
    evt.setFormat(player.getDisplayName() + "§a: §7" + evt.getMessage());
  }

  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      evt.setCancelled(true);
      
      Player player = (Player) evt.getWhoClicked();
      Inventory inv = evt.getInventory();
      
      if (inv.getTitle().equals("Servidores")) {
        if (evt.getSlot() == 10) {
          player.closeInventory();
          player.sendMessage("§cVocê já está conectado à um lobby.");
        } else if (evt.getSlot() == 12) {
          player.sendMessage("§aConectando..");
          TalonLobby.sendServer(player, "skywars");
        }
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onBlockBurn(BlockBurnEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onBlockIgnite(BlockIgniteEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onLeavesDecay(LeavesDecayEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onEntityTarget(EntityTargetEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onItemSpawn(ItemSpawnEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent evt) {
    evt.setCancelled(evt.getSpawnReason() != SpawnReason.CUSTOM);
  }

  @EventHandler
  public void onWeatherChange(WeatherChangeEvent evt) {
    evt.setCancelled(evt.toWeatherState());
  }
}
