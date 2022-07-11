package yt.bebr0.bebr0hunt.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static ItemStack createItemStack(String name, List<String> description, Material material){
        Component nameComponent = Component.text(name);

        List<Component> descriptionComponent = new ArrayList<>();
        for (String line: description){
            descriptionComponent.add(Component.text(line));
        }

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(nameComponent);
        itemMeta.lore(descriptionComponent);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
