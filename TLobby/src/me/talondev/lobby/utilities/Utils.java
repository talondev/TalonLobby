package me.talondev.lobby.utilities;

public class Utils {
  
  // vamo precisar:
  // Retirado do Site do SpigotMC.
  public static float clampYaw(float yaw) {
    while (yaw < -180.0F) {
      yaw += 360.0F;
    }
    while (yaw >= 180.0F) {
      yaw -= 360.0F;
    }
    
    return yaw;
  }
}
