package com.server.reservation;

import com.server.reservation.Command.MainCommand;
import com.server.reservation.Data.UserData;
import com.server.reservation.Listener.OnClick;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

import static com.server.reservation.Data.UserData.config;
import static com.server.reservation.Data.UserData.playerFile;

public final class Main extends JavaPlugin {

    public static Main plugin;
    private File uuidFolder;
    private int taskId;

    public void listener() {
        Bukkit.getPluginManager().registerEvents(new OnClick(), this);
    }

    public void command() {
        Bukkit.getPluginCommand("ticket").setExecutor(new MainCommand());
    }

    @Override
    public void onEnable() {
        plugin = this;
        listener();
        command();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static Main getPlugin() {
        return plugin;
    }

    public File getUuidFolder() {
        return uuidFolder;
    }

    public void createPlayerDefaults() {
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.addDefault("제한시간", 0);
        playerConfig.options().copyDefaults(true);
        saveYamlConfiguration();
    }

    public void saveYamlConfiguration() {
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void thrTime(Player player) {
        YamlConfiguration config = UserData.getPlayerConfig(player);
        if (taskId != -1) {
            // If there is already a running BukkitRunnable, cancel it
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        taskId = new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time == 1800) {
                    config.set("제한시간", 0);
                    saveYamlConfiguration();
                    player.sendMessage("30분이 지났습니다. 티켓 재사용이 가능합니다!");
                }
                time++;
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20).getTaskId();
    }

    public void twrTime(Player player) {

        taskId = new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time == 1200) { //일단 10초로 설정해 놓음
                    String worldName = plugin.getConfig().getString("2.world");
                    double x = plugin.getConfig().getDouble("2.x");
                    double y = plugin.getConfig().getDouble("2.y");
                    double z = plugin.getConfig().getDouble("2.z");

                    if (worldName == null) {
                        worldName = "world";
                    }

                    World world = Bukkit.getWorld(worldName);
                    Location randomLocation = new Location(world, x, y, z);

                    player.teleport(randomLocation);
                    player.sendMessage("랜덤좌표 이동에 성공했습니다.");
                }
                time++;
            }
        }.runTaskTimer(this, 0, 20).getTaskId(); // 1초마다 실행
    }
}
