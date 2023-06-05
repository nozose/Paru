package com.paru;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

public final class Paru extends JavaPlugin implements Listener {
    Map<UUID, Integer> gibon = new HashMap<>();
    Map<UUID, Integer> stat_point = new HashMap<>();
    Map<UUID, Integer> stat_mp = new HashMap<>();
    Map<UUID, BigDecimal> stat_speed = new HashMap<>();
    Map<UUID, Integer> stat_defense = new HashMap<>();
    Map<UUID, Integer> stat_hp = new HashMap<>();
    Map<UUID, Integer> stat_atk = new HashMap<>();
    Map<UUID, Integer> mp_point = new HashMap<>();
    Map<UUID, Integer> speed_point = new HashMap<>();
    Map<UUID, Integer> defense_point = new HashMap<>();
    Map<UUID, Integer> hp_point = new HashMap<>();
    Map<UUID, Integer> atk_point = new HashMap<>();


    private final Map<UUID, Inventory> playerInventories = new HashMap<>();
    int printhp;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!gibon.containsKey(playerUUID)) {
            gibon.put(playerUUID, 0);
            stat_point.put(playerUUID, 0);
            printhp=0;
        }
        if (!stat_mp.containsKey(playerUUID)) {
            stat_mp.put(playerUUID, 0);
            mp_point.put(playerUUID, 0);
        }
        if (!stat_speed.containsKey(playerUUID)) {
            stat_speed.put(playerUUID, BigDecimal.ZERO);
            speed_point.put(playerUUID, 0);
        }
        if (!stat_defense.containsKey(playerUUID)) {
            stat_defense.put(playerUUID, 0);
            defense_point.put(playerUUID, 0);
        }
        if (!stat_hp.containsKey(playerUUID)) {
            stat_hp.put(playerUUID, 20);
            hp_point.put(playerUUID, 0);
        }
        if (!stat_atk.containsKey(playerUUID)) {
            stat_atk.put(playerUUID, 0);
            atk_point.put(playerUUID, 0);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("기본템") || label.equalsIgnoreCase("rlqhsxpa")) {
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
                    p.sendMessage("기본템은 한 번만 받을 수 있습니다.");
                }
            } else {
                sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            }
        } else if (label.equalsIgnoreCase("스탯") || label.equalsIgnoreCase("stats")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                UUID playerUUID = p.getUniqueId();

                if (!playerInventories.containsKey(playerUUID)) {
                    playerInventories.put(playerUUID, Bukkit.createInventory(null, 9, "스탯"));
                }

                Inventory playerInventory = playerInventories.get(playerUUID);
                ItemStack stackmp = new ItemStack(Material.ENCHANTING_TABLE);
                ItemMeta mpmeta = stackmp.getItemMeta();
                int mp = stat_mp.getOrDefault(playerUUID, 0);
                mpmeta.setDisplayName("마력");
                mpmeta.setLore(Arrays.asList("MP : " + mp));
                stackmp.setItemMeta(mpmeta);

                ItemStack stackSpeed = new ItemStack(Material.RABBIT_FOOT);
                ItemMeta speedMeta = stackSpeed.getItemMeta();
                BigDecimal speed = stat_speed.getOrDefault(playerUUID, BigDecimal.ZERO);
                speedMeta.setDisplayName("민첩");
                speedMeta.setLore(Arrays.asList("AGI : " + speed));
                stackSpeed.setItemMeta(speedMeta);

                ItemStack stackDefense = new ItemStack(Material.SHIELD);
                ItemMeta defenseMeta = stackDefense.getItemMeta();
                int defense = stat_defense.getOrDefault(playerUUID, 0);
                defenseMeta.setDisplayName("방어력");
                defenseMeta.setLore(Arrays.asList("DEF : " + defense));
                stackDefense.setItemMeta(defenseMeta);

                ItemStack stackHP = new ItemStack(Material.APPLE);
                ItemMeta hpMeta = stackHP.getItemMeta();
                int hp = stat_hp.getOrDefault(playerUUID, 0);
                hpMeta.setDisplayName("체력");
                hpMeta.setLore(Arrays.asList("HP : " + hp));
                stackHP.setItemMeta(hpMeta);

                ItemStack stackATK = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta atkMeta = stackATK.getItemMeta();
                int atk = stat_atk.getOrDefault(playerUUID, 0);
                atkMeta.setDisplayName("공격력");
                atkMeta.setLore(Arrays.asList("ATK : " + atk));
                stackATK.setItemMeta(atkMeta);

                playerInventory.setItem(0, stackmp);
                playerInventory.setItem(2, stackSpeed);
                playerInventory.setItem(4, stackDefense);
                playerInventory.setItem(6, stackHP);
                playerInventory.setItem(8, stackATK);

                p.openInventory(playerInventory);
            } else {
                System.out.println("이 명령어는 플레이어만 사용할 수 있습니다.");
            }
        } else if (label.equalsIgnoreCase("스탯포인트") || label.equalsIgnoreCase("statpoints")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length >= 1) {
                    String playerName = args[0];  // 플레이어 닉네임

                    // 플레이어의 UUID 가져오기
                    UUID playerUUID = Bukkit.getPlayerUniqueId(playerName);

                    // UUID를 통해 플레이어의 stat_point 확인 또는 추가
                    if (playerUUID != null) {
                        if (args.length >= 2) {
                            int statPointsToAdd = Integer.parseInt(args[1]);  // 추가할 스탯 포인트 수
                            int currentStatPoints = stat_point.getOrDefault(playerUUID, 0);
                            int newStatPoints = currentStatPoints + statPointsToAdd;
                            stat_point.put(playerUUID, newStatPoints);
                            p.sendMessage(playerName + "님에게 " + statPointsToAdd + "개의 스탯 포인트가 추가되었습니다.");
                        } else {
                            int statPoints = stat_point.getOrDefault(playerUUID, 0);
                            p.sendMessage(playerName + "님의 스탯 포인트: " + statPoints);
                        }
                    } else {
                        p.sendMessage("플레이어를 찾을 수 없습니다.");
                    }
                } else {
                    p.sendMessage("사용법: /스탯포인트 <플레이어 닉네임> [추가할 스탯 포인트]");
                }
            } else {
                System.out.println("이 명령어는 플레이어만 사용할 수 있습니다.");
            }
        } else if (label.equalsIgnoreCase("스탯잔고")) {
            Player p = (Player) sender;
            UUID playerUUID = p.getUniqueId();
            p.sendMessage("당신의 스탯포인트는 " + stat_point.get(playerUUID) + "포인트입니다.");
        }
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() != null && playerInventories.containsValue(e.getClickedInventory())) {
            e.setCancelled(true);

            if (e.getCurrentItem() != null) {
                ItemStack currentItem = e.getCurrentItem();
                Material itemType = currentItem.getType();

                if (itemType == Material.ENCHANTING_TABLE) {
                    int mp = stat_mp.getOrDefault(playerUUID, 0);
                    ItemMeta itemMeta = currentItem.getItemMeta();
                    if (mp > 100000) {
                        itemMeta.setLore(Collections.singletonList("MP : 최대 수치"));
                        currentItem.setItemMeta(itemMeta);
                        return;
                    } else {
                        mp += 1;
                    }
                    stat_mp.put(playerUUID, mp);

                    itemMeta.setLore(Collections.singletonList("MP : " + mp));
                    currentItem.setItemMeta(itemMeta);
                } else if (itemType == Material.RABBIT_FOOT) {
                    BigDecimal speed = stat_speed.getOrDefault(playerUUID, BigDecimal.ZERO);
                    speed = speed.add(BigDecimal.ONE);
                    stat_speed.put(playerUUID, speed);

                    ItemMeta itemMeta = currentItem.getItemMeta();
                    itemMeta.setLore(Collections.singletonList("AGI : " + speed));
                    currentItem.setItemMeta(itemMeta);
                } else if (itemType == Material.SHIELD) {
                    int defense = stat_defense.getOrDefault(playerUUID, 0);
                    defense += 1;
                    stat_defense.put(playerUUID, defense);

                    ItemMeta itemMeta = currentItem.getItemMeta();
                    itemMeta.setLore(Collections.singletonList("DEF : " + defense));
                    currentItem.setItemMeta(itemMeta);
                } else if (itemType == Material.APPLE) {
                    int hp = stat_hp.getOrDefault(playerUUID, 0);
                    hp += 1;
                    stat_hp.put(playerUUID, hp);

                    ItemMeta itemMeta = currentItem.getItemMeta();
                    itemMeta.setLore(Collections.singletonList("HP : " + hp));
                    currentItem.setItemMeta(itemMeta);
                } else if (itemType == Material.DIAMOND_SWORD) {
                    int atk = stat_atk.getOrDefault(playerUUID, 0);
                    atk += 1;
                    stat_atk.put(playerUUID, atk);

                    ItemMeta itemMeta = currentItem.getItemMeta();
                    itemMeta.setLore(Collections.singletonList("ATK : " + atk));
                    currentItem.setItemMeta(itemMeta);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
}