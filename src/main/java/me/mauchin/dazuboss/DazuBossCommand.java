package me.mauchin.dazuboss;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class DazuBossCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Location location = null;
        World world = null;
        boolean isConsole = false;
        if (args[0].equalsIgnoreCase("set_vanilla")||args[0].equalsIgnoreCase("set_magic")) {
            try {
                location = ((Player) sender).getLocation();
                world = ((Player) sender).getWorld();
            } catch (Exception ignored) {
                try {
                    location = ((BlockCommandSender) sender).getBlock().getLocation();
                    world = ((BlockCommandSender) sender).getBlock().getWorld();
                } catch (Exception ignored1) {
                    isConsole = true;
                }
            }
        }
        if (args[0].equalsIgnoreCase("set_vanilla") && args.length >= 4 && !isConsole){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            String name = args[1];
            String type = args[2];
            String nbt = String.join(" ",Arrays.copyOfRange(args, 3, args.length));
            String cmd = "execute in "+world.getKey().asString()+" run summon "+type+" "+location.getBlockX()
                    + " " + location.getBlockY()+" "+location.getBlockZ()+" "+nbt;
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            placements.put(name,new DazuBossPlacement("","",cmd,false));
            plugin.savePlacements(placements);
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor+" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("set_magic") && args.length >= 3 && !isConsole){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            String name = args[1];
            String type = args[2];
            String cmd = "mmob spawn " + type + " "+location.getBlockX()
                    + " " + location.getBlockY()+" "+location.getBlockZ()+" "+world.getName();
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            placements.put(name,new DazuBossPlacement("","",cmd,true));
            plugin.savePlacements(placements);
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor+" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("spawn") && args.length >= 2){
            DazuBossPlacement boss = DazuBoss.getPlugin(DazuBoss.class).getPlacements().get(args[1]);
            if (boss != null) {
                boss.run();
                sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor +" Successfully summoned "+ ChatColor.RESET +boss.bossName);
                return true;
            }
            return false;
        }
        else if (args[0].equalsIgnoreCase("name") && args.length >= 3){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            DazuBossPlacement placement = placements.get(args[1]);
            placement.setBossName(String.join(" ",Arrays.copyOfRange(args, 2, args.length)));
            placements.put(args[1],placement);
            plugin.savePlacements(placements);
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor +" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("location") && args.length >= 3){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            DazuBossPlacement placement = placements.get(args[1]);
            placement.setLocation(String.join(" ",Arrays.copyOfRange(args, 2, args.length)));
            placements.put(args[1],placement);
            plugin.savePlacements(placements);
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor +" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("alivecheck") && args.length >= 3){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            DazuBossPlacement placement = placements.get(args[1]);
            placement.aliveCheck = args[2].equalsIgnoreCase("true");
            placements.put(args[1],placement);
            plugin.savePlacements(placements);
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor +" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("list")){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor +" Boss List (alive="+plugin.getConfig().getBoolean("is_alive")+")");
            for(String key:placements.keySet()){
                sender.sendMessage(DazuBoss.chatColor +key+": "+placements.get(key).toString());
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("remove") && args.length >= 2){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            Map<String,DazuBossPlacement> placements = plugin.getPlacements();
            placements.remove(args[1]);
            plugin.savePlacements(placements);
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor+" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("config") && args.length >= 3){
            DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
            plugin.getConfig().set(args[1],Integer.valueOf(args[2]));
            plugin.saveConfig();
            sender.sendMessage(DazuBoss.prefix+DazuBoss.chatColor +" Success!");
            return true;
        }
        else if (args[0].equalsIgnoreCase("defeat")){
            DazuBoss.getPlugin(DazuBoss.class).getConfig().set("is_alive",false);
            Bukkit.broadcastMessage(DazuBoss.prefix +
                    DazuBoss.chatColor + " The boss has been defeated!");
            return true;
        }
        return false;
    }
}
