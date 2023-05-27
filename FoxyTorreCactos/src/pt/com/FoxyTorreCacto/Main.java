package pt.com.FoxyTorreCacto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import pt.com.FoxyTorreCacto.comands.TorrecactoCMD;
import pt.com.FoxyTorreCacto.listeners.BlockPlace;
import pt.com.FoxyTorreCacto.utils.ItemBuilder;
import pt.com.FoxyTorreCacto.utils.NBTAPI;
import pt.com.FoxyTorreCacto.utils.zBUtils;

public class Main extends JavaPlugin {
    private static Main instance;

    private HashMap<Integer, ItemStack> torres = new HashMap<>();


    @Override
    public void onEnable() {
        instance = this;
        if (Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
        	zBUtils.ConsoleMsg("&7[&bFoxyTorreCacto&7] &cPlotSquared não encontrado!");
        	getPluginLoader().disablePlugin(this);
        	return;
        }
        loadListeners();
        loadCmds();
        generateStuff();
        zBUtils.ConsoleMsg("&7[&bFoxyTorreCacto&7] &aO plugin foi iniciado.");
    }

    @Override
    public void onDisable() {
        zBUtils.ConsoleMsg("&7[&bFoxyTorreCacto&7] &cO plugin foi encerrado.");
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
    }

    private void loadCmds() {
        getCommand("torrecacto").setExecutor(new TorrecactoCMD());
        getCommand("torrecacto").setPermission("foxytorrecacto.admin");
        getCommand("torrecacto").setPermissionMessage("§cVocê não tem permissão para isso!");
    }

    public static Main getInstance() {
        return instance;
    }


    private ItemStack createItemStack(int level) {
        List<String> lore = Arrays.asList("§7Use este item para gerar", "§7uma torre de cactos!", "", "§f Nivel: §7" + level);
        ItemBuilder base = new ItemBuilder("2f585b41ca5a1b4ac26f556760ed11307c94f8f8a1ade615bd12ce074f4793").setName("§aTorre de Cacto").setLore(lore);
        NBTAPI nbt = NBTAPI.getNBT(base.toItemStack());
        nbt.setInt("foxytorre_level", level);
        return nbt.getItem();
    }

    private void generateStuff() {
        for (int i = 1; i < 4; i++) {
            torres.put(i, createItemStack(i));
        }
    }

    public ItemStack provideItemStack(int level) {
        return torres.get(level);
    }
}
