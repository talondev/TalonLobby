package me.talondev.lobby.holograms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import com.google.common.collect.ImmutableList;

public class Hologram {

  private boolean spawned;
  private Location location;
  private Map<Integer, HologramLine> lines = new HashMap<>();

  public Hologram(Location location, String... lines) {
    this.location = location;
    int index = 0;
    for (String line : lines) {
      this.lines.put(++index,
          new HologramLine(this, location.clone().add(0, 0.33 * index, 0), line));
    }
  }

  public void spawn() {
    if (spawned) {
      return;
    }

    spawned = true;
    lines.values().forEach(line -> line.spawn());
  }

  public void despawn() {
    if (!spawned) {
      return;
    }

    spawned = false;
    lines.values().forEach(line -> line.despawn());
  }

  public void addLine(String text) {
    int line = 1;
    while (lines.containsKey(line)) {
      line++;
    }

    HologramLine hl = new HologramLine(this, location.clone().add(0, 0.33 * line, 0), text);
    lines.put(line, hl);
    if (spawned) {
      hl.spawn();
    }
  }

  public void updateLine(int line, String text) {
    if (lines.containsKey(line)) {
      lines.get(line).setText(text);
    }
  }

  public boolean isSpawned() {
    return spawned;
  }

  public Location getInitLocation() {
    return location;
  }

  public List<HologramLine> getLines() {
    return ImmutableList.copyOf(lines.values());
  }
}
