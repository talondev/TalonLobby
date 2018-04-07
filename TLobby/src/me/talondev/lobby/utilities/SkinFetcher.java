package me.talondev.lobby.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SkinFetcher {
  
  public static void main(String[] args) {
    String name = "TalonDev";
    
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
      String str = "";
      String line;
      while ((line = reader.readLine()) != null) {
        str += line;
      }

      String id = new JsonParser().parse(str).getAsJsonObject().get("id").getAsString();
      System.out.println("UUID >> " + id);

      reader.close();
      reader = new BufferedReader(new InputStreamReader(new URL(
          "https://sessionserver.mojang.com/session/minecraft/profile/" + id + "?unsigned=false")
              .openStream()));
      str = "";
      line = null;
      while ((line = reader.readLine()) != null) {
        str += line;
      }
      String prop = "";
      JsonObject profile = new JsonParser().parse(str).getAsJsonObject();
      JsonObject object = profile.get("properties").getAsJsonArray().get(0).getAsJsonObject();
      prop = object.get("value").getAsString() + " : " + object.get("signature").getAsString();
      System.out.println("PROPERTIES:");
      System.out.println("Name>> \"textures\"");
      System.out.println("Value>> \"" + prop.split(" : ")[0] + "\"");
      System.out.println("Signature>> \"" + prop.split(" : ")[1] + "\"");
      System.out.println(" \nCodigo de setar skin:");
      System.out.println(
          "npc.setSkin(\"" + prop.split(" : ")[0] + "\", \"" + prop.split(" : ")[1] + "\");");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
