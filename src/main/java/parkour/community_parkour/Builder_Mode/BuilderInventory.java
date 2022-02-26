package parkour.community_parkour.Builder_Mode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BuilderInventory implements InventoryHolder {

    Inventory inv;
    ItemStack blackglass;
    ItemStack stonebrick;
    ItemStack spruceslab;
    ItemStack spruceplank;
    ItemStack slimeblock;
    ItemStack stone;
    ItemStack andesite;
    ItemStack ladder;
    ItemStack vine;
    ItemStack leaves;
    ItemStack wall;
    ItemStack fence;
    ItemStack checkpoint;

    public BuilderInventory(){
        inv = Bukkit.createInventory(this, 45, "Building Inventory");
        init();
    }

    public void init(){
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        List<String> lore2 = new ArrayList<>();
        lore2.add("Checkpoints");
        blackglass = createItem(" ", Material.BLACK_STAINED_GLASS_PANE, lore);
        stonebrick = createItem("Stone Bricks", Material.STONE_BRICKS, lore);
        spruceslab = createItem("Spruce Slabs", Material.SPRUCE_SLAB, lore);
        spruceplank = createItem("Spruce Planks", Material.SPRUCE_PLANKS, lore);
        slimeblock = createItem("Slime Block", Material.SLIME_BLOCK, lore);
        stone = createItem("Stone", Material.STONE, lore);
        andesite = createItem("Andesite", Material.ANDESITE, lore);
        ladder = createItem("Ladder", Material.LADDER, lore);
        vine = createItem("Vines", Material.VINE, lore);
        leaves = createItem("Leaves", Material.SPRUCE_LEAVES, lore);
        wall = createItem("Cobblestone Wall", Material.COBBLESTONE_WALL, lore);
        fence = createItem("Spruce Fence", Material.SPRUCE_FENCE, lore);
        checkpoint = createItem("Checkpoint", Material.DISPENSER, lore2);

        for(int i = 0; i < 10; i++){

            inv.setItem(i, blackglass);

        }

        inv.setItem(17, blackglass);
        inv.setItem(26, blackglass);
        inv.setItem(35, blackglass);

        inv.setItem(18, blackglass);
        inv.setItem(27, blackglass);
        inv.setItem(36, blackglass);

        inv.setItem(10, stonebrick);
        inv.setItem(11, spruceslab);
        inv.setItem(12, spruceplank);
        inv.setItem(13, slimeblock);
        inv.setItem(14, stone);
        inv.setItem(15, andesite);
        inv.setItem(16, ladder);
        inv.setItem(19, vine);
        inv.setItem(20, leaves);
        inv.setItem(21, wall);
        inv.setItem(22, fence);
        inv.setItem(23, checkpoint);

        for (int i = 35; i < 45; i++){

            inv.setItem(i, blackglass);

        }

    }

    private ItemStack createItem(String name, Material mat, List<String> lore){
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
