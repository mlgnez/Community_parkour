package parkour.community_parkour.Items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Boost_Item {

    public static ItemStack supply;

    public static void init(){createItem();}

    private static void createItem(){

        ItemStack item = new ItemStack(Material.BEACON, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dHide Other Players");
        List<String> lore = new ArrayList<>();
        lore.add("Use this item to hide other players to focus on parkour.");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        supply = item;

    }

}
