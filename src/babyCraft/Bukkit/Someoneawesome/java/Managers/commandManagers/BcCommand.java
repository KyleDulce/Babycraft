package babyCraft.Bukkit.Someoneawesome.java.Managers.commandManagers;

import java.util.*;

import org.bukkit.command.*;

public interface BcCommand {
	public boolean onCommand(CommandSender arg0, String[] arg1);
	public List<String> onTabComplete(CommandSender arg0,  String[] arg1);
}
