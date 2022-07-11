package yt.bebr0.bebr0hunt.util;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static ItemStack createItemStack(String name, List<String> description, Material material) {
        Component nameComponent = Component.text(ChatColor.translateAlternateColorCodes('&', name));

        List<Component> descriptionComponent = new ArrayList<>();
        for (String line : description) {
            descriptionComponent.add(Component.text(line));
        }

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(nameComponent);
        itemMeta.lore(descriptionComponent);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    /**
     * Method for creating dyed leather armor
     *
     * @throws IllegalArgumentException If material is not leather armour
     */
    public static ItemStack createItemStack(String name, List<String> description, Material material, Color color) {
        Component nameComponent = Component.text(ChatColor.translateAlternateColorCodes('&', name));

        List<Component> descriptionComponent = new ArrayList<>();
        for (String line : description) {
            descriptionComponent.add(Component.text(line));
        }

        ItemStack itemStack = new ItemStack(material);
        if (!(itemStack.getItemMeta() instanceof LeatherArmorMeta itemMeta)) {
            throw new IllegalArgumentException("Use this method only for items u want to dye!");
        }

        itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();

        itemMeta.displayName(nameComponent);
        itemMeta.lore(descriptionComponent);
        itemMeta.setColor(color);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    /**
     * Method for creating potions
     * @throws IllegalArgumentException If material is not potion
     * */
    public static ItemStack createPotion(String name, List<String> description, Material material, List<PotionEffect> potionEffects) {
        Component nameComponent = Component.text(ChatColor.translateAlternateColorCodes('&', name));

        List<Component> descriptionComponent = new ArrayList<>();
        for (String line : description) {
            descriptionComponent.add(Component.text(line));
        }

        ItemStack itemStack = new ItemStack(material);

        PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
        if (itemMeta == null)
            throw new IllegalArgumentException("Use this only for potions!");

        itemMeta.displayName(nameComponent);
        itemMeta.lore(descriptionComponent);

        for (PotionEffect potionEffect: potionEffects) {
            itemMeta.addCustomEffect(potionEffect, true);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
