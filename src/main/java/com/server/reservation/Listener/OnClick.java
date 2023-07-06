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
import org.bukkit.scheduler.BukkitRunnable;

import static com.server.reservation.Main.plugin;

public class OnClick implements Listener {

    @EventHandler
    public void onClickEvent(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        YamlConfiguration config = UserData.getPlayerConfig(player);
        int limitTime = config.getInt("제한시간"); //30분이 지나면

        if (limitTime == 1) {
            player.sendMessage("20분이 지나지 않아 사용이 불가능 합니다!");
            return;
        }

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (limitTime == 0) {

            config.set("제한시간", 1); //다시 사용 금지
            Main.getPlugin().saveYamlConfiguration();

            if (player.getItemInHand().getItemMeta().displayName().equals("야생 티켓")
                    && player.getItemInHand().getItemMeta().getLore().equals("[제한 시간 30분]")) return;

            removeItemsFromMainHand(player, 1); //사용으로 인한 차감

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

            Main.getPlugin().twrTime(player); //20분 뒤 랜덤 텔레포트
            Main.getPlugin().thrTime(player); //티켓 사용 가능
        }
    }

    public void removeItemsFromMainHand(Player player, int amountToRemove) {
        ItemStack itemToRemove = player.getInventory().getItemInMainHand().clone();
        itemToRemove.setAmount(amountToRemove);

        player.getInventory().removeItem(itemToRemove);
    }
}
