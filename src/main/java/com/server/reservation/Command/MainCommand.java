package com.server.reservation.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "발급" -> {
                        Issued issued = new Issued();
                        issued.onCommand(sender, command, label, args);
                    }

                    case "2번설정" -> {
                        SetPositionRandomTP setPositionRandomTP = new SetPositionRandomTP();
                        setPositionRandomTP.onCommand(sender, command, label, args);
                    }

                    case "1번설정" -> {
                        SetPositionLimitTime setPositionLimitTime = new SetPositionLimitTime();
                        setPositionLimitTime.onCommand(sender, command, label, args);
                    }
                }
            }
        }
        return false;
    }
}
