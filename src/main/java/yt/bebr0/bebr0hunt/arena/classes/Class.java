package yt.bebr0.bebr0hunt.arena.classes;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yt.bebr0.bebr0hunt.util.ItemUtil;

import java.util.ArrayList;
import java.util.List;

public enum Class implements Listener {

    ARCHER("&4Лучник", List.of(
            ItemUtil.createItemStack("&4Лук", new ArrayList<>(), Material.BOW),
            ItemUtil.createItemStack("&4Шлем лучника", new ArrayList<>(), Material.LEATHER_HELMET),
            ItemUtil.createItemStack("&4Нагрудник лучника", new ArrayList<>(), Material.LEATHER_CHESTPLATE),
            ItemUtil.createItemStack("&4Штаны лучника", new ArrayList<>(), Material.LEATHER_LEGGINGS),
            ItemUtil.createItemStack("&4Ботинки лучника", new ArrayList<>(), Material.LEATHER_BOOTS),
            ItemUtil.createItemStack("&4Меч лучника", new ArrayList<>(), Material.STONE_SWORD)
    ), List.of(
            new PotionEffect(PotionEffectType.SPEED, 10000, 1)
    ));

    private final List<ItemStack> classItems;
    private final List<PotionEffect> effects;

    private final String name;

    Class(String name, List<ItemStack> classItems, List<PotionEffect> effects){
        this.name = name;
        this.classItems = classItems;
        this.effects = effects;
    }

    public void grantAbilities(Player player){
        for (ItemStack itemStack: classItems){
            player.getInventory().addItem(itemStack);
        }

        for (PotionEffect potionEffect: effects){
            player.addPotionEffect(potionEffect);
        }

        player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "Ваш класс - " + name)));
    }

    public List<ItemStack> getClassItems() {
        return classItems;
    }

    public String getName() {
        return name;
    }
}
