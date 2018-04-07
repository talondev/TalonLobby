
package me.talondev.lobby.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import me.talondev.lobby.TalonLobby;
import me.talondev.lobby.holograms.Hologram;
import me.talondev.lobby.holograms.HologramLibrary;
import me.talondev.lobby.npc.NPC;
import me.talondev.lobby.npc.NPCLibrary;

public class HumanAPI {

  /**
   * APARTIR DA 1.8 VOCE PODE ESCONDER O NOME DOS NPCS APARTIR DOS TEAMS DE SCOREBOARD IREI USAR
   * ISTO PARA ESCONDER OS NOMES DELES.
   * 
   * NPCS NÃO PODEM SER SPAWNADOS EM LOCALIZAÇÕES ONDE A CHUNK NÃO FOI CARREGA, TEM COMO "DRIBAR"
   * ESSE PROBLEMA COM o ChunkLoadEvent e ChunkUnloadEvent MAS PARA NAO FICAR MUITO GRANDE, PARA
   * PREVINIR ISTO SPAWNE NPCS PERTO DO WORLDSPAWN "setworlspawn" NA LOCALIZAÇÃO POR PERTO DO NPC
   * QUE FICA SUSSA;
   */

  public static void makeNPCs() {
    NPC npc = NPCLibrary.createNPC("§8[NPC] §1"); // SkyWars
    // Código vem pronto :)
    npc.setSkin(
        "eyJ0aW1lc3RhbXAiOjE1MjI3MTMzMTc4MDYsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWFiMThhY2Y1Y2M5YzFlMzhmNTgyYjE0YjY4ZGE5MjU5ODcyNjRkNzc5YmM1ZTgzYzM1MGQ2YTM0OGI2In19fQ==",
        "On3ZZh/EjyapuwH8+s5dIbLdN8K9SIVQ1bizh0EC2clFKSFa7t/upo/uwxCorv3wA0+c4viLo4nMxkfuXQVBPS6qRnIjFVa8cqCMl89bfER/jY/Gw9+d8Bcz+PRqc0GVjTYzA7Pk7CalltbI7hS48gqgF/Jl4FmW/o9OxgXLpYneo5eKTuHgPLT71/Gm7hTPOH0cvRH+7Pc7gWAKvQC2cP5d3DMuva2LznD+RoWJicgkpTQkgwBiGO9DzKsTg3jRapqjhW0vXW4McNtQwAv3rShSNEuYNouIL1bc42kM/aw8zBat7oc0o+iz/6sQtoOlKRYqEPcIUC9T6jeqBfiIzYBt38cB0LuJnFAuq4EW6bdB/UqNvbqM9YXxXsF5wXtaKHTCAkTOnebzYYakRwq1TB0gQhn8e/jHdXuAfiRSjX0UkxguJDHDvFZMo3+zuTrDK4IMcASTAarpfnldk7N4RCy4rGABqdQY39blFHQIhhlFjx1WEWBNJddGDeFV+bg1z5URcTlfjZNI1zOSTTnsO3Mnhv0hjuHNJglzxpQGeBhvmgqK03MM0396+lahCa33YUOsoWFY6k2CSnBwIAaA4N7BN4lUDsgVLcpMddWk8B8PVECd3MHO6bBzgj7QQhwnXy1up3VE5DbarNbFpKP+DhYLa9JB0sNz+eQeX6raY3M=");

    Location location = Bukkit.getWorlds().get(0).getSpawnLocation().clone();
    npc.spawn(location);

    location.add(0, 0.5, 0);
    final Hologram hologram = HologramLibrary.createHologram(location,
        "§a" + TalonLobby.skywars + " jogando", "§bSkyWars");
    hologram.spawn();

    new BukkitRunnable() {
      @Override
      public void run() {
        hologram.updateLine(1, "§a" + TalonLobby.skywars + " jogando");
      }
    }.runTaskTimer(TalonLobby.getInstance(), 0, 20);
  }
}
