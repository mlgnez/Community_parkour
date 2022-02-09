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

    public BuilderInventory(){
        inv = Bukkit.createInventory(this, 45, "Building Inventory");
        init();
    }

    public void init(){
        List<String> lore = new ArrayList<>();
        lore.add(" ");
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

        for(int i = 0; i < 9; i++){

            inv.setItem(i, blackglass);

        }

        for (int i = 35; i < 44; i++){

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
