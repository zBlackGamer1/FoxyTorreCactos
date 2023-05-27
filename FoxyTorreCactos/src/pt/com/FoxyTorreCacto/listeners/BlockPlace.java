package pt.com.FoxyTorreCacto.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import pt.com.FoxyTorreCacto.utils.NBTAPI;

public class BlockPlace implements Listener {
	@EventHandler
	private void onPlace(BlockPlaceEvent e) {
		if(e.isCancelled()) return;
		ItemStack item = e.getItemInHand();
		if(!NBTAPI.getNBT(item).hasKey("foxytorre_level")) return;
		e.setCancelled(true);
		int level = NBTAPI.getNBT(item).getInt("foxytorre_level");
		Location loc = e.getBlock().getLocation();
		if (isOnRoad(loc)) {
			e.getPlayer().sendMessage("§cVocê não pode colocar no limite da plot!");
			return;
		}
		tirarItem(e.getPlayer(), item);
		makeBase(loc);
		int camadas = 0;
		if(level == 1) camadas = 5;
		if(level == 2) camadas = 8;
		if(level == 3) camadas = 12;
		int add = 4;
		for (int i = 0; i < camadas - 1; i++) {
			makeCamada(loc.clone().add(0, +add, 0));
			add = add + 4;
		}
	}
	
	private void makeCamada(Location loc) {
		loc.getWorld().getBlockAt(loc.clone().add(+0, 0, +1)).setType(Material.COBBLESTONE);
		loc.getWorld().getBlockAt(loc.clone().add(+0, 0, -1)).setType(Material.COBBLESTONE);
		loc.getWorld().getBlockAt(loc.clone().add(-1, 0, +0)).setType(Material.COBBLESTONE);
		loc.getWorld().getBlockAt(loc.clone().add(+1, 0, +0)).setType(Material.COBBLESTONE);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +1, +1)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +1, -1)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(-1, +1, +0)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(+1, +1, +0)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +2, +1)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +2, -1)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(-1, +2, +0)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(+1, +2, +0)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +3, +0)).setType(Material.COBBLESTONE);
	}
	
	private void makeBase(Location loc) {
		Block b = loc.getWorld().getBlockAt(loc.clone().add(+0, 0, +1));
		if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
		b = loc.getWorld().getBlockAt(loc.clone().add(+0, 0, -1));
		if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
		b = loc.getWorld().getBlockAt(loc.clone().add(-1, 0, +0));
		if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
		b = loc.getWorld().getBlockAt(loc.clone().add(+1, 0, +0));
		if(b.getType() == Material.AIR) b.setType(Material.COBBLESTONE);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +1, +1)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +1, -1)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(-1, +1, +0)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(+1, +1, +0)).setType(Material.SAND);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +2, +1)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +2, -1)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(-1, +2, +0)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(+1, +2, +0)).setType(Material.CACTUS);
		loc.getWorld().getBlockAt(loc.clone().add(+0, +3, +0)).setType(Material.COBBLESTONE);
	}
	
	private boolean isOnRoad(Location loc) {
		if (LocationTradutor(loc).isPlotRoad()) return true;
		if (LocationTradutor(loc.clone().add(+0, 0, +1)).isPlotRoad()) return true;
		if (LocationTradutor(loc.clone().add(+0, 0, -1)).isPlotRoad()) return true;
		if (LocationTradutor(loc.clone().add(-1, 0, +0)).isPlotRoad()) return true;
		if (LocationTradutor(loc.clone().add(+1, 0, +0)).isPlotRoad()) return true;
		return false;
	}
	
	private com.intellectualcrafters.plot.object.Location LocationTradutor(Location loc){
		return new com.intellectualcrafters.plot.object.Location(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	private void tirarItem(Player p, ItemStack i) {
		if(p.getGameMode() == GameMode.CREATIVE) return;
		if(i.getAmount() == 1) p.getInventory().removeItem(i);
		else i.setAmount(i.getAmount() - 1);
	}
}
