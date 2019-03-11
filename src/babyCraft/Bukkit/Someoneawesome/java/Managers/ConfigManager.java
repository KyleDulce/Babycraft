package babyCraft.Bukkit.Someoneawesome.java.Managers;

import java.io.*;
import java.util.*;

import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;

public class ConfigManager {

	private Babycraft plugin = Babycraft.getPlugin(Babycraft.class);
	
	//file config for children.yml
	public FileConfiguration childrenCfg;
	public File ChildrenFile;
	
	//starting up and checks for directory for children config
	public void setupChildren() {
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		//tests if players.yml exists, if not create it
		ChildrenFile = new File(plugin.getDataFolder(), "children.yml");
		if(!ChildrenFile.exists()) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Babycraft] 'children.yml' does not exist, creating one'");
			try {
				ChildrenFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'children.yml' is created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'children.yml' file, shutting down");
			}
		}
		
		//yaml file creation
		try{
			childrenCfg = YamlConfiguration.loadConfiguration(ChildrenFile);
		} catch (Exception e) {
			resetConfigs(true);
		}
		
		Bukkit.getServer().getConsoleSender().sendMessage("[Babycraft] 'children.yml' config had been loaded");
	}
	
	
	//getter for file config
	public FileConfiguration getChildren() {return childrenCfg;}
	
	//Save the config file
	public void saveChildren() {
		try {
			childrenCfg.save(ChildrenFile);
		}catch(IOException e) {
			plugin.getLogger().info("[Babycraft] Error: children config file cannot be saved");
		}
	}
	
	//reload the config file
	public void reloadChildren() {
		//reloads file
		ChildrenFile = new File(plugin.getDataFolder(), "children.yml");
		//tests if existant
		if(!ChildrenFile.exists()) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Babycraft] 'children.yml' does not exist, creating one'");
			try {
				ChildrenFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'children.yml' is created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'children.yml' file, shutting down");
			}
		}
		
		//yaml set
		try{
			childrenCfg = YamlConfiguration.loadConfiguration(ChildrenFile);
		} catch (Exception e) {
			resetConfigs(true);
		}
		
		Bukkit.getServer().getConsoleSender().sendMessage("[Babycraft] 'children.yml' config had been reloaded");
	}
	
	///////////////Players///////////////////////////
	
	//file config for players.yml
		public FileConfiguration playerCfg;
		public File playerFile;
		
		//starting up and checks for directory for children config
		public void setupPlayer() {
			if(!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			
			//tests if players.yml exists, if not create it
			playerFile = new File(plugin.getDataFolder(), "players.yml");
			if(!playerFile.exists()) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Babycraft] 'players.yml' does not exist, creating one'");
				try {
					playerFile.createNewFile();
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'players.yml' is created");
				} catch (IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'players.yml' file, shutting down");
				}
			}
			
			//yaml file creation
			try{
				playerCfg = YamlConfiguration.loadConfiguration(playerFile);
			} catch (Exception e) {
				resetConfigs(true);
			}
			
			Bukkit.getServer().getConsoleSender().sendMessage("[Babycraft] 'players.yml' config had been loaded");
		}
		
		
		//getter for file config
		public FileConfiguration getPlayer() {return playerCfg;}
		
		//Save the config file
		public void savePlayer() {
			try {
				playerCfg.save(playerFile);
			}catch(IOException e) {
				plugin.getLogger().info("[Babycraft] Error: Player config file cannot be saved");
			}
		}
		
		//reloag the config file
		public void reloadPlayer() {
			//reloads file
			playerFile = new File(plugin.getDataFolder(), "players.yml");
			//tests if existant
			if(!playerFile.exists()) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Babycraft] 'players.yml' does not exist, creating one'");
				try {
					playerFile.createNewFile();
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'players.yml' is created");
				} catch (IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'players.yml' file, shutting down");
				}
			}
			
			//yaml set
			try{
				playerCfg = YamlConfiguration.loadConfiguration(playerFile);
			} catch (Exception e) {
				resetConfigs(true);
			}
			
			Bukkit.getServer().getConsoleSender().sendMessage("[Babycraft] 'players.yml' config had been reloaded");
		}
		
		public void newPlayerInstance(Player player) {
			playerCfg.set(player.getUniqueId().toString() + ".username", player.getName());
			playerCfg.set(player.getUniqueId().toString() + ".gender", "None");
			final List<String> list = playerCfg.getStringList(player.getUniqueId().toString() + ".children");
			list.add("NO VALUE");
			playerCfg.set(player.getUniqueId().toString() + ".children", list);
			savePlayer();
		}
		
		//both
		public void resetConfigs(boolean broken) {
			if(broken) {
				plugin.console.sendMessage("[BabyCraft] Because of broken file, making new files and marking them 'BROKEN'");
				File newFileChild = new File(plugin.getDataFolder(), "children_BROKEN.txt");
				File newFilePlayer = new File(plugin.getDataFolder(), "players_BROKEN.txt");
				try {
					FileWriter writter = new FileWriter(newFileChild);
					FileReader reader = new FileReader(ChildrenFile);
					writter.write(reader.read());
					writter.close();
					reader.close();
					
					writter = new FileWriter(newFilePlayer);
					reader = new FileReader(playerFile);
					writter.write(reader.read());
					writter.close();
					reader.close();
				} catch (IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'BROKEN' files continuing");
				}
			} else {
				plugin.console.sendMessage("[BabyCraft] Because of outdated file, making new files and marking them 'OLD'");
				File newFileChild = new File(plugin.getDataFolder(), "children_OLD.txt");
				File newFilePlayer = new File(plugin.getDataFolder(), "players_OLD.txt");
				try {
					FileWriter writter = new FileWriter(newFileChild);
					FileReader reader = new FileReader(ChildrenFile);
					writter.write(reader.read());
					writter.close();
					reader.close();
					
					writter = new FileWriter(newFilePlayer);
					reader = new FileReader(playerFile);
					writter.write(reader.read());
					writter.close();
					reader.close();
				} catch (IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'BROKEN' files continuing");
				}
			}
			
			try {
				ChildrenFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'children.yml' is created");
				playerFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'players.yml' is created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'children.yml' file, shutting down");
			}
			
			childrenCfg = YamlConfiguration.loadConfiguration(ChildrenFile);
			playerCfg = YamlConfiguration.loadConfiguration(playerFile);
			
			plugin.console.sendMessage("[BabyCraft] reset complete");
		}

		//all
		public void replaceOld() {
			ChildrenFile.renameTo(new File(plugin.getDataFolder(), "children.txt"));
			playerFile.renameTo(new File(plugin.getDataFolder(), "players.txt"));
			
			setupPlayer();
			setupChildren();
		}
		
		///////////////////userConfig//////////////////
		public FileConfiguration MainCfg;
		public File MainFile;
		
		public void setupMain() {
			if(!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			
			//tests if main.yml exists, if not create it
			MainFile = new File(plugin.getDataFolder(), "config.yml");
			if(!MainFile.exists()) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Babycraft] 'config.yml' does not exist, creating one'");
				Babycraft.getInstance().saveResource("config.yml", true);
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Babycraft] 'config.yml' is created");
			}
			
			boolean configLoad = true;
			while(configLoad) {
			//yaml file creation
				configLoad = false;
				try {
					MainCfg = YamlConfiguration.loadConfiguration(MainFile);
					if (!verifyMain()) {
						resetMain();
						configLoad = true;
					}
				} catch (Exception e) {
					resetMain();
					configLoad = true;
				}		
			}
			
			Bukkit.getServer().getConsoleSender().sendMessage("[Babycraft] 'config.yml' config had been loaded");
		}
		
		public boolean verifyMain() {
			for(ConfigMainPath e : ConfigMainPath.l) {
				if(MainCfg.get(ConfigMainPath.toPath(e)) == null) {
					return false;
				}
			}
			return true;
		}
		
		public void resetMain() {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] A problem occured reading the config file, recreating file and marking it 'BROKEN'");
			
			File newFile = new File(plugin.getDataFolder(), "config_BROKEN.txt");
			try {
				FileWriter writter = new FileWriter(newFile);
				FileReader reader = new FileReader(MainFile);
				writter.write(reader.read());
				writter.close();
				reader.close();
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Babycraft] Could not create 'BROKEN' file continuing");
			}
			
			MainFile.delete();
			Babycraft.getInstance().saveResource("config.yml", true);
			
		}
		
}
