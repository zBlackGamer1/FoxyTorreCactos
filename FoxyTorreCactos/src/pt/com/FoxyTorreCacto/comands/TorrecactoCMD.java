package pt.com.FoxyTorreCacto.comands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pt.com.FoxyTorreCacto.Main;

public class TorrecactoCMD implements CommandExecutor {


    private boolean isInteger(String s) {
        return Integer.parseInt(s) > 0;
    }


    private void giveItemToPlayer(Player player, int level, int amount) {
        ItemStack item = Main.getInstance().provideItemStack(level);
        item.setAmount(amount);
        player.getInventory().addItem(item);
    }


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("torrecacto")) {
            if (!s.hasPermission("torrecacto.give")) {
                s.sendMessage("§cYou don't have permission to execute this command.");
                return true;
            }

            if (args.length < 2) {
                s.sendMessage("§cERRO! Use /torrecacto (Jogador) (Nivel) (Quantidade).");
                return true;
            }

            if (args.length == 2) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    s.sendMessage("§cJogador não encontrado.");
                    return true;
                }
                if (isInteger(args[1]) && Integer.parseInt(args[1]) <= 3) {
                    giveItemToPlayer(Bukkit.getPlayer(args[0]), Integer.parseInt(args[1]), 1);
                    s.sendMessage("§aSucesso! Torre enviada com sucesso para §e"+Bukkit.getPlayer(args[0]).getName());
                } else {
                    s.sendMessage("§cO nivel deve ser um numero válido (1-3)");
                    return true;
                }
            } else if (args.length == 3) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    s.sendMessage("§cJogador não encontrado.");
                    return true;
                }
                if ((isInteger(args[1]) && Integer.parseInt(args[1]) <= 3) && (isInteger(args[2]))) {
                    giveItemToPlayer(Bukkit.getPlayer(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    s.sendMessage("§aSucesso! §e"+Integer.parseInt(args[2])+"§a Torre(s) enviada(s) com sucesso para §e"+Bukkit.getPlayer(args[0]).getName());
                } else {
                    s.sendMessage("§cO nivel e a quantidade devem ser numeros válidos");
                    return true;
                }
            }
        }
        return false;
    }
}
