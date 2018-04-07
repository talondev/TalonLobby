package me.talondev.lobby.npc;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class NPCConnection extends PlayerConnection {
  
  public NPCConnection(EntityNPCPlayer ep) {
    super(ep.server, new EmptyNetHandler(), ep);
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public void sendPacket(Packet packet) {
    // NAO ENVIE PACKETS A UM NPC
  }
}
