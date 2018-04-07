package me.talondev.lobby.api;

import java.util.Arrays;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.talondev.lobby.TalonLobby;

public class ServerAPI {

  private static boolean set;
  public static Inventory inventory;

  public static void makeMenu() {
    inventory = Bukkit.createInventory(null, 3 * 9, "Servidores");

    ItemStack lobby = new ItemStack(Material.BOOKSHELF);
    ItemMeta lmeta = lobby.getItemMeta();
    lmeta.setDisplayName("§6Lobby Principal");
    lobby.setItemMeta(lmeta);
    inventory.setItem(10, lobby);

    ItemStack item = new ItemStack(Material.GRASS);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName("§aSkyWars §lATUALIZADO");
    meta.setLore(Arrays.asList("§7Clique para se conectar ao", "§7skywars da nossa rede.", "",
        (set ? "§7*" : "§7 ") + " 0 jogando.", (set ? "§e*" : "§e ") + " Clique para conectar"));
    item.setItemMeta(meta);

    inventory.setItem(12, item);

    Bukkit.getScheduler().scheduleSyncRepeatingTask(TalonLobby.getInstance(), () -> {
      writeCount("ALL");
      writeCount("skywars");

      set = !set;
      ItemStack sw = inventory.getItem(12);
      ItemMeta swmeta = sw.getItemMeta();
      swmeta.setLore(Arrays.asList("§7Clique para se conectar ao", "§7skywars da nossa rede.", "",
          (set ? "§7*" : "§7 ") + " " + TalonLobby.skywars + " jogando.",
          "§e* Clique para conectar"));
      sw.setItemMeta(swmeta);
    }, 0, 20);
  }

  @SuppressWarnings("unchecked")
  private static void writeCount(String server) {
    Iterator<Player> itr = (Iterator<Player>) Bukkit.getOnlinePlayers().iterator();
    if (!itr.hasNext()) {
      return;
    }

    Player fake = itr.next();
    if (fake == null) {
      return;
    }

    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("PlayerCount");
    out.writeUTF(server);
    fake.sendPluginMessage(TalonLobby.getInstance(), "BungeeCord", out.toByteArray());
  }
}
