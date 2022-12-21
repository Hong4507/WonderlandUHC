package org.mcwonderland.uhc.scenario.impl.special.mole;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.model.SimpleReplacer;

@RequiredArgsConstructor
public class SelectKitMenuListener implements Listener {

    private final SelectKitMenu menu;
    private final ScenarioMole mole;

    @EventHandler
    private void onClick(InventoryClickEvent e) {
        Chat.broadcast("RRRRRRRR");
        Player player = ( Player ) e.getWhoClicked();
        if (e.getClickedInventory() == menu.getMenuInventory() && e.getCurrentItem() != null) {
            e.setCancelled(true);

            // spawn the chest
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY() - 1;
            int z = player.getLocation().getBlockZ();
            Block block = player.getWorld().getBlockAt(x, y, z);
            block.setType(Material.CHEST);
            BlockState blockState = block.getState();
            Chest chest = ( Chest ) blockState;
            Inventory inventory = chest.getBlockInventory();

            switch (e.getSlot()) {
                case 0: {
                    // Scout
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
                    Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "偵查兵").getMessages());
                    break;
                }
                case 1: {
                    // Phoenix
                    ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
                    PotionMeta phoenixMeta = ( PotionMeta ) phoenix.getItemMeta();
                    phoenixMeta.setDisplayName(ChatColor.GOLD + "不死鳳凰");
                    phoenixMeta.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 90, 9), true);
                    phoenix.setItemMeta(phoenixMeta);
                    inventory.addItem(phoenix);
                    Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "不死鳳凰").getMessages());
                    break;
                }
                case 2:
                    // Teleporter
                    inventory.addItem(new ItemStack(Material.ENDER_PEARL, 6));
                    Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "傳送者").getMessages());
                    break;
                default:
                    return;
            }

            mole.markKitSelected(player);
            player.closeInventory();
        }
    }

}
