package com.server.reservation.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.server.reservation.Main.plugin;

public class SetPositionLimitTime implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.isOp()) {
                plugin.getConfig().set("1.world", player.getLocation().getWorld().getName());
                plugin.getConfig().set("1.x", player.getLocation().getX());
                plugin.getConfig().set("1.y", player.getLocation().getY());
                plugin.getConfig().set("1.z", player.getLocation().getZ());
                plugin.saveConfig();
                player.sendMessage("메인 텔레포트 위치를 설정 하셨습니다.");
            }
        }
        return false;
    }
}
