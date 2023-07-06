package com.server.reservation.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.server.reservation.Main.plugin;

public class SetPositionRandomTP implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.isOp()) {
                plugin.getConfig().set("2.world", player.getLocation().getWorld().getName());
                plugin.getConfig().set("2.x", player.getLocation().getX());
                plugin.getConfig().set("2.y", player.getLocation().getY());
                plugin.getConfig().set("2.z", player.getLocation().getZ());
                plugin.saveConfig();
                player.sendMessage("랜덤 텔레포트 위치를 설정 하셨습니다.");
            }
        }
        return false;
    }
}
