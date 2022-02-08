package parkour.community_parkour;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import parkour.community_parkour.Items.HidePlayers_Item;
import parkour.community_parkour.Items.Item_Listener;

public final class Main extends JavaPlugin {

    public NamespacedKey hidden = new NamespacedKey(this, "players_hidden");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Item_Listener(this), this);
        HidePlayers_Item.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
