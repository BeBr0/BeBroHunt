package yt.bebr0.bebr0hunt.arena.classes;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yt.bebr0.bebr0hunt.util.ItemUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Class {

    ARCHER("&aЛучник", List.of(
            ItemUtil.createItemStack("&aЛук", new ArrayList<>(), Material.BOW, Map.of(Enchantment.ARROW_INFINITE, 1)),
            ItemUtil.createItemStack("&aШлем лучника", new ArrayList<>(), Material.LEATHER_HELMET, Color.GREEN),
            ItemUtil.createItemStack("&aНагрудник лучника", new ArrayList<>(), Material.LEATHER_CHESTPLATE, Color.GREEN),
            ItemUtil.createItemStack("&aШтаны лучника", new ArrayList<>(), Material.LEATHER_LEGGINGS, Color.GREEN),
            ItemUtil.createItemStack("&aБотинки лучника", new ArrayList<>(), Material.LEATHER_BOOTS, Color.GREEN),
            ItemUtil.createItemStack("&aМеч лучника", new ArrayList<>(), Material.STONE_SWORD),
            ItemUtil.createItemStack("&aСтрела", new ArrayList<>(), Material.ARROW)
    ), List.of(
            new PotionEffect(PotionEffectType.SPEED, 10000, 0)
    )),
    SWORD("&4Мечник", List.of(
            ItemUtil.createItemStack("&4Шлем мечника", new ArrayList<>(), Material.IRON_HELMET),
            ItemUtil.createItemStack("&4Нагрудник мечника", new ArrayList<>(), Material.IRON_CHESTPLATE),
            ItemUtil.createItemStack("&4Штаны мечника", new ArrayList<>(), Material.IRON_LEGGINGS),
            ItemUtil.createItemStack("&4Ботинки мечника", new ArrayList<>(), Material.IRON_BOOTS),
            ItemUtil.createItemStack("&4Меч", new ArrayList<>(), Material.DIAMOND_SWORD),
            ItemUtil.createItemStack("&4Щит", new ArrayList<>(), Material.SHIELD)
    ), List.of(

    )),
    DEMOLISHIONEER("&6Подрывник", List.of(
            ItemUtil.createItemStack("&6Меч подрывника", new ArrayList<>(), Material.STONE_SWORD),
            ItemUtil.createItemStack("&6Шлем подрывника", new ArrayList<>(), Material.LEATHER_HELMET, Color.ORANGE),
            ItemUtil.createItemStack("&6Нагрудник подрывника", new ArrayList<>(), Material.LEATHER_CHESTPLATE, Color.ORANGE),
            ItemUtil.createItemStack("&6Штаны подрывника", new ArrayList<>(), Material.LEATHER_LEGGINGS, Color.ORANGE),
            ItemUtil.createItemStack("&6Ботинки подрывника", new ArrayList<>(), Material.LEATHER_BOOTS, Color.ORANGE),
            Grenade.getInstance().getGrenadeItem()
    ), List.of(
            new PotionEffect(PotionEffectType.JUMP, 10000, 1)
    )),
    BREWER("&dЗельевар", List.of(
            ItemUtil.createItemStack("&dМеч зельевара", new ArrayList<>(), Material.STONE_SWORD),
            ItemUtil.createItemStack("&dШлем зельевара", new ArrayList<>(), Material.LEATHER_HELMET, Color.PURPLE),
            ItemUtil.createItemStack("&dНагрудник зельевара", new ArrayList<>(), Material.LEATHER_CHESTPLATE, Color.PURPLE),
            ItemUtil.createItemStack("&dШтаны зельевара", new ArrayList<>(), Material.LEATHER_LEGGINGS, Color.PURPLE),
            ItemUtil.createItemStack("&dБотинки зельевара", new ArrayList<>(), Material.LEATHER_BOOTS, Color.PURPLE),

            ItemUtil.createPotion("&dЛечилка", new ArrayList<>(), Material.SPLASH_POTION, List.of(
                    new PotionEffect(PotionEffectType.HEAL, 20, 0),
                    new PotionEffect(PotionEffectType.REGENERATION, 30 * 20, 1),
                    new PotionEffect(PotionEffectType.ABSORPTION, 30 * 20, 1)
            )),
            ItemUtil.createPotion("&dСкорость", new ArrayList<>(), Material.SPLASH_POTION, List.of(
                    new PotionEffect(PotionEffectType.SPEED, 20 * 20, 2)
            )),
            ItemUtil.createPotion("&dУрон", new ArrayList<>(), Material.SPLASH_POTION, List.of(
                    new PotionEffect(PotionEffectType.HARM, 1, 2)
            )),
            ItemUtil.createPotion("&dПол-лава", new ArrayList<>(), Material.LINGERING_POTION, List.of(
                    new PotionEffect(PotionEffectType.HARM, 1, 2)
            )),
            ItemUtil.createPotion("&dЛечим-тиммейтов", new ArrayList<>(), Material.LINGERING_POTION, List.of(
                    new PotionEffect(PotionEffectType.HEAL, 0, 2)
            ))
    ), List.of(

    )),
    CROSSBOWMAN("&bАрбалетчик", List.of(
            ItemUtil.createItemStack("&bАрбалет", List.of("Это как лук, только без лука"), Material.CROSSBOW, Map.of(Enchantment.ARROW_INFINITE, 1)),
            ItemUtil.createItemStack("&bМеч арбалетчика", new ArrayList<>(), Material.STONE_SWORD),
            ItemUtil.createItemStack("&bШлем арбалетчика", new ArrayList<>(), Material.LEATHER_HELMET, Color.PURPLE),
            ItemUtil.createItemStack("&bНагрудник арбалетчика", new ArrayList<>(), Material.LEATHER_CHESTPLATE, Color.PURPLE),
            ItemUtil.createItemStack("&bШтаны арбалетчика", new ArrayList<>(), Material.LEATHER_LEGGINGS, Color.PURPLE),
            ItemUtil.createItemStack("&bБотинки арбалетчика", new ArrayList<>(), Material.LEATHER_BOOTS, Color.PURPLE),
            ItemUtil.createItemStack("&aБолт", new ArrayList<>(), Material.ARROW)

    ), List.of(
            new PotionEffect(PotionEffectType.SPEED, 10000, 0)
    ));

    private static final Map<Player, Class> playerClass = new HashMap<>();

    @Nullable
    public static Class getClass(Player player){
        return playerClass.get(player);
    }

    private final List<ItemStack> classItems;
    private final List<PotionEffect> effects;

    private final String name;

    Class(String name, List<ItemStack> classItems, List<PotionEffect> effects){
        this.name = name;
        this.classItems = classItems;
        this.effects = effects;
    }

    public void grantAbilities(Player player){
        playerClass.put(player, this);

        for (ItemStack itemStack: classItems){
            player.getInventory().addItem(itemStack);
        }

        for (PotionEffect potionEffect: effects){
            player.addPotionEffect(potionEffect);
        }

        player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "Ваш класс - " + name)));
    }

    public String getName() {
        return name;
    }
}
