package parkour.community_parkour;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import parkour.community_parkour.Commands.CommandAutofill;
import parkour.community_parkour.Commands.CommandExecutor;
import parkour.community_parkour.Items.Boost_Item;
import parkour.community_parkour.Items.HiddenPlayers_Item;
import parkour.community_parkour.Items.HidePlayers_Item;
import parkour.community_parkour.Items.Item_Listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Main extends JavaPlugin {

    public NamespacedKey hidden = new NamespacedKey(this, "players_hidden");
    public NamespacedKey PlotID = new NamespacedKey(this, "plot_id");

    public Boolean plotAvaliability[] = {
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
    };
    public NamespacedKey boost_cooldown = new NamespacedKey(this, "boost_cooldown");

    public void SavePlot(int plot_num, Player player){ //plot id for plot id | player for uuid

        int z1 = (plot_num * 25) + 24;
        int z2 = (plot_num * 25) - 24;
        BlockVector3 vector1 = BlockVector3.at(15,63,z1);
        BlockVector3 vector2 = BlockVector3.at(-15,199,z2);

        CuboidRegion cube = new CuboidRegion(vector1, vector2);
        cube.setWorld((World) Bukkit.getWorld("world"));

        BlockArrayClipboard clipboard = new BlockArrayClipboard(cube);

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(cube.getWorld())){
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                    editSession, cube, clipboard, cube.getMinimumPoint()
            );
            Operations.complete(forwardExtentCopy);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }

        File file = new File("./schematics/" + player.getUniqueId());
        try(ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))){
            writer.write(clipboard);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public Clipboard LoadPlot(Player player){

        File file = new File("./schematics/" + player.getUniqueId());

        Clipboard clipboard = null;

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try(ClipboardReader reader = format.getReader(new FileInputStream(file))){
            clipboard = reader.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return clipboard;

    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Item_Listener(this), this);
        HidePlayers_Item.init();
        HiddenPlayers_Item.init();
        Boost_Item.init();
        instance = this;
        getCommand("parkour").setExecutor(new CommandExecutor());
        getCommand("parkour").setTabCompleter(new CommandAutofill());
    }

    public static Main instance;

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
