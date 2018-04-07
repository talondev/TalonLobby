package me.talondev.lobby.utilities;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class ModuleLogger extends Logger {
  
  private String prefix;
  
  public ModuleLogger(String name) {
    this(Bukkit.getLogger(), "[" + name + "] ");
  }
  
  private ModuleLogger(Logger parent, String name) {
    super(name, null);
    this.setParent(parent);
    this.setLevel(Level.ALL);
    this.prefix = name;
  }
  
  public ModuleLogger getModule(String module) {
    return new ModuleLogger(getParent(), prefix + "[" + module + "] ");
  }
  
  @Override
  public void log(LogRecord record) {
    record.setMessage(prefix + record.getMessage());
    super.log(record);
  }
}
