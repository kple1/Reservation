package com.server.reservation.Listener;

import com.server.reservation.Data.UserData;
import com.server.reservation.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.server.reservation.Main.plugin;

public class OnClick implements Listener {

    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;

        if (!itemMeta.hasDisplayName() || !itemMeta.hasLore())
            return;

        String displayName = itemMeta.getDisplayName();
        if (!displayName.equals("야생 티켓"))
            return;

        List<String> lore = itemMeta.getLore();
        if (lore.size() != 1 || !lore.get(0).equals("제한 시간 30분"))
            return;

        YamlConfiguration config = UserData.getPlayerConfig(player);
        int limitTime = config.getInt("제한시간");

        if (limitTime == 1) {
            player.sendMessage("20분이 지나지 않아 사용이 불가능합니다!");
            return;
        }

        if (limitTime == 0) {
            config.set("제한시간", 1);
            Main.getPlugin().saveYamlConfiguration();

            removeItemsFromMainHand(player, 1);

            String worldName = plugin.getConfig().getString("1.world");
            if (worldName == null) {
                worldName = "world";
            }

            double x = plugin.getConfig().getDouble("1.x");
            double y = plugin.getConfig().getDouble("1.y");
            double z = plugin.getConfig().getDouble("1.z");

            World world = Bukkit.getWorld(worldName);

            Location randomLocation = new Location(world, x, y, z);

            player.teleport(randomLocation);

            Main.getPlugin().startTimer(player);
        }
    }

    public void removeItemsFromMainHand(Player player, int amountToRemove) {
        ItemStack itemToRemove = player.getInventory().getItemInMainHand().clone();
        itemToRemove.setAmount(amountToRemove);

        player.getInventory().removeItem(itemToRemove);
    }
}
