package com.paru;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public final class Paru extends JavaPlugin implements Listener, CommandExecutor {

    public HashMap<UUID, Integer> gibon = new HashMap<>();
    Inventory stats = Bukkit.createInventory(null, 27, "테스트");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diameta = diamond.getItemMeta();
        diameta.setDisplayName("다이아몬드");
        diameta.setLore(Arrays.asList("클릭시 다이아 지급"));
        diamond.setItemMeta(diameta);

        stats.setItem(13, diamond);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        gibon.putIfAbsent(playerUUID, 0);
    }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("기본템")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                UUID playerUUID = p.getUniqueId();
                if (gibon.containsKey(playerUUID) && gibon.get(playerUUID) == 0) {
                    gibon.put(playerUUID, 1);
                    ItemStack ironAxe = new ItemStack(Material.IRON_AXE);
                    ItemStack ironPickaxe = new ItemStack(Material.IRON_PICKAXE);
                    ItemStack ironShovel = new ItemStack(Material.IRON_SHOVEL);
                    ItemStack ironSword = new ItemStack(Material.IRON_SWORD);

                    p.getInventory().addItem(ironSword, ironPickaxe, ironAxe, ironShovel);
                    p.sendMessage("철 도끼, 곡괭이, 삽, 칼이 지급되었습니다!");
                } else {
                    p.sendMessage("기본템은 한번만 받을수 있습니다.");
                }
            } else {
                System.out.println("이 명령어는 플레이어만 사용할수 있습니다.");
            }
        } else if (label.equalsIgnoreCase("스탯")) {
            //공격, 마력, 민첩, 방어
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.openInventory(stats);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000000, 1));
            } else {
                System.out.println("이 명령어는 플레이어만 사용할수 있습니다.");
            }
        }
        return false;
    }



    @Override
    public void onDisable() {

    }
}
