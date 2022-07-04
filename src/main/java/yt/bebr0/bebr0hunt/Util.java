package yt.bebr0.bebr0hunt;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util {

    public static ItemStack createItemStack(String name, List<String> description, Material material){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(description);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
