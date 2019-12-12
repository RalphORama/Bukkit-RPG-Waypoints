package sh.ralph.rpgwaypoints;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGWaypoints extends JavaPlugin {
    @Override
    public void onEnable() {
        // bStats initialization
        Metrics metrics = new Metrics(this);
    }
}
