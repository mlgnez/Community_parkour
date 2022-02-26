package parkour.community_parkour;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import parkour.community_parkour.Builder_Mode.BuilderInvItem;
import parkour.community_parkour.Builder_Mode.BuilderListener;
import parkour.community_parkour.Commands.CommandAutofill;
import parkour.community_parkour.Commands.CommandExecutor;
import parkour.community_parkour.Items.Boost_Item;
import parkour.community_parkour.Items.HiddenPlayers_Item;
import parkour.community_parkour.Items.HidePlayers_Item;
import parkour.community_parkour.Items.Item_Listener;
import parkour.community_parkour.PlayerMode.PlayListener;

import java.io.*;
import java.net.URL;

public final class Main extends JavaPlugin {

    public NamespacedKey hidden = new NamespacedKey(this, "players_hidden");
    public NamespacedKey PlotID = new NamespacedKey(this, "plot_id");
    public NamespacedKey buildingStatus = new NamespacedKey(this, "building");
    public NamespacedKey PlayTesting = new NamespacedKey(this, "playtesting");
    public NamespacedKey lbozo = new NamespacedKey(this, "checkpointID");
    public NamespacedKey builderCheckPoint = new NamespacedKey(this, "buildercheckpoint");
    public NamespacedKey level = new NamespacedKey(this, "level");
    public NamespacedKey checkpoint = new NamespacedKey(this, "checkpoint");

    public int PlotCount = 0;

    public Boolean[] plotAvaliability = {
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

        int z1 = (plot_num * 50) + 25;
        int z2 = (plot_num * 50) - 24;
        BlockVector3 vector1 = BlockVector3.at(15,63,z1);
        BlockVector3 vector2 = BlockVector3.at(-15,199,z2);

        CuboidRegion cube = new CuboidRegion(vector1, vector2);
        cube.setWorld(BukkitAdapter.adapt(Bukkit.getWorld("world")));

        BlockArrayClipboard clipboard = new BlockArrayClipboard(cube);

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(cube.getWorld())){
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                    editSession, cube, clipboard, cube.getMinimumPoint()
            );
            Operations.complete(forwardExtentCopy);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }

        File file = new File(this.getFile().getParentFile() +"/communityParkour/" + player.getUniqueId() + ".schem");
        try(ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))){
            writer.write(clipboard);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public Clipboard GetPlayerSchematic(Player player) throws IOException {

        File file = new File(this.getFile().getParentFile() +"/communityParkour/" + player.getUniqueId() + ".schem");
        Clipboard clipboard = null;

        if(!file.exists()){

            if(file.createNewFile())
            {
                file = new File(this.getFile().getParentFile() + "/communityParkour/base.schem");
                ClipboardFormat format = ClipboardFormats.findByFile(file);
                try(ClipboardReader reader = format.getReader(new FileInputStream(file))){
                    clipboard = reader.read();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                file = new File(this.getFile().getParentFile() + "/communityParkour/" + player.getUniqueId() + ".schem");
                try(ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))){
                    writer.write(clipboard);

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try(ClipboardReader reader = format.getReader(new FileInputStream(file))){
               clipboard = reader.read();
        } catch (IOException exception) {
                exception.printStackTrace();
        }



        return clipboard;

    }

    public void PastePlot(int plot_id, Clipboard clipboard, World world)
    {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(100, 131, plot_id * 50)) //we paste the plot in the line which is 100 blocks away from the building area.
                    // configure here
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public void playTest(int plot_id, Player player){

        player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 124, (plot_id * 50) - 23.5f));
        player.setAllowFlight(false);
        player.sendMessage(ChatColor.GREEN + "You are now play testing your parkour course.");
        player.getPersistentDataContainer().set(Main.instance.buildingStatus, PersistentDataType.INTEGER, 0);
        player.getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 1);
        player.getInventory().setItem(6, new ItemStack(Material.AIR));

    }

    public void resetPlot(int plot_id){

        int PlotCount = plot_id;
        //set the line of plots to air | do this when adding plots to the line is added and

        //add the corners of the first level and add the corner of the last level using some math to make this work (remember, its cornerx, cornery, PlotCount * 50)

        int z1 = 0;
        int z2 = PlotCount * 50;

        BlockVector3 vector1 = BlockVector3.at(15,63, z1);
        BlockVector3 vector2 = BlockVector3.at(-15,199,z2);

        CuboidRegion cuboidRegion = new CuboidRegion(vector1, vector2);

        File file = new File(this.getFile().getParentFile() +"/communityParkour/base.schem");

        Clipboard clipboard = null;

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try(ClipboardReader reader = format.getReader(new FileInputStream(file))){
            clipboard = reader.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        World world = BukkitAdapter.adapt(Bukkit.getWorld("world"));

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(-15, 63, (plot_id * 50) -24))
                    // configure here
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }

        plotAvaliability[PlotCount] = false;

    }

    public void loadPlot(int plot_id, Clipboard clipboard, World world)
    {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(-15, 63, (plot_id * 50) - 24)) //we paste the plot in the line which is 100 blocks away from the building area.
                    // configure here
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public static File getFileFromResources(String path)
    {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        if(resource == null)
        {
            throw new IllegalArgumentException("file not found! " + path);
        } else {
            return new File(resource.getFile());
        }
    }

    @Override
    public void onEnable() {
        File file = new File(this.getFile().getParentFile() + "/communityParkour/base.schem");
        if(!file.exists())
        {
            try {
                if(file.createNewFile())
                {
                    File base = getFileFromResources("include/base.schem");
                    Clipboard clipboard = null;
                    ClipboardFormat format = ClipboardFormats.findByFile(file);
                    try(ClipboardReader reader = format.getReader(new FileInputStream(file))){
                        clipboard = reader.read();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    File finalbase = new File(this.getFile().getParentFile() + "/communityParkour/base.schem");
                    try(ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))){
                        writer.write(clipboard);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getPluginManager().registerEvents(new Item_Listener(this), this);
        Bukkit.getPluginManager().registerEvents(new BuilderListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayListener(this), this);
        HidePlayers_Item.init();
        HiddenPlayers_Item.init();
        Boost_Item.init();
        BuilderInvItem.init();
        instance = this;
        getCommand("parkour").setExecutor(new CommandExecutor());
        getCommand("parkour").setTabCompleter(new CommandAutofill());
        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
    }

    public static Main instance;

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
