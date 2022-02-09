package parkour.community_parkour.Builder_Mode;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BuilderInvItem {

    public static ItemStack supply;

    public static void init(){createItem();}

    private static void createItem(){

        ItemStack item = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dOpen Building Inventory");
        List<String> lore = new ArrayList<>();
        lore.add("Use this item to get blocks to build.");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        supply = item;

    }

}
