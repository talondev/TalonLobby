package me.talondev.lobby.npc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Entity;
import com.google.common.collect.ImmutableList;

public class NPCLibrary {

  private static List<NPC> npcs = new ArrayList<>();

  public static NPC createNPC(String name) {
    return createNPC(UUID.nameUUIDFromBytes(("NPC:" + name).getBytes()), name);
  }

  public static NPC createNPC(UUID uuid, String name) {
    NPC npc = new NPC(uuid, name);
    npcs.add(npc);
    return npc;
  }

  public static Collection<NPC> listNPCs() {
    return ImmutableList.copyOf(npcs);
  }

  public static NPC getNPC(Entity entity) {
    return isNPC(entity) ? ((NPCHolder) entity).getNPC() : null;
  }

  public static boolean isNPC(Entity entity) {
    return entity != null && entity instanceof NPCHolder;
  }
}
