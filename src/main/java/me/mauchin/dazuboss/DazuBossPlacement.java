package me.mauchin.dazuboss;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class DazuBossPlacement {
    String cmd;
    String location;
    String bossName;
    boolean aliveCheck;
    public DazuBossPlacement(String location, String bossName, String cmd, boolean aliveCheck){
        this.cmd = cmd;
        this.location = location;
        this.bossName = bossName;
        this.aliveCheck = aliveCheck;
    }

    public void setBossName(String bossName) {
        this.bossName = ChatColor.translateAlternateColorCodes('&',bossName);
    }

    public void setLocation(String location) {
        this.location = ChatColor.translateAlternateColorCodes('&',location);
    }

    public void run(){
        DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
        if (plugin.getConfig().getBoolean("is_alive") && aliveCheck){
            Bukkit.broadcastMessage(DazuBoss.prefix+" "+DazuBoss.chatColor+"Could not summon a boss since there's already one");
        }
        else {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, cmd);
            plugin.getConfig().set("is_alive",true);
            plugin.saveConfig();
            Bukkit.broadcastMessage(DazuBoss.prefix +
                    ChatColor.RESET + " " + bossName + DazuBoss.chatColor + " has appeared at " + ChatColor.RESET + location);
        }
    }
    public String toString(){
        return DazuBoss.chatColor+"[location="+ location +DazuBoss.chatColor+", bossName="+bossName+DazuBoss.chatColor+", aliveCheck="+((Boolean)aliveCheck).toString()+", cmd="+cmd+"]";
    }

}
