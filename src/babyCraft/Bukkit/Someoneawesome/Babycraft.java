package babyCraft.Bukkit.Someoneawesome;

import java.util.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.plugin.java.*;

import babyCraft.Bukkit.Someoneawesome.java.GUIMenus.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers.*;
import babyCraft.Bukkit.Someoneawesome.java.tabCompleter.TabCompleter;

public class Babycraft extends JavaPlugin {
	
	private static Babycraft instance;

	private ConfigManager configManager;
	public ConfigIO configIO;
	public CommandManger cmdManager;
	public TabCompleter tabcompleter;
	public ConsoleCommandSender console;

	@Override
	public void onEnable() {
		//setup console
		console = Bukkit.getServer().getConsoleSender();
		
		//load config files
		loadConfig();
		
		//register event class
		getServer().getPluginManager().registerEvents(new EventHandlerClass(), this);
		
		//setup variables for creator meu
		Creator.pending = new ArrayList<>();
		Creator.pendingRequests = new HashMap<>();
		Creator.runningCreators = new HashMap<>();
		
		//setup variables for child variables
		Child.children = new HashMap<>();
		Child.ids = new HashMap<>();
		
		//menu variables
		Menu.menu = new HashMap<>();
		
		//cmd manager and cmds
		cmdManager = new CommandManger();
		
		//setting tabcompleter
		tabcompleter = new TabCompleter();
		
		getCommand("Babycraft").setExecutor(cmdManager);
		getCommand("Babycraft").setTabCompleter(tabcompleter);
		
		console.sendMessage("[Babycraft] Babycraft enabled");
	}
	
	@Override
	public void onDisable() {
		Child.despawnAll();
		
		configManager.saveChildren();
		configManager.savePlayer();
		
		console.sendMessage("Babycraft disabled");
	}
	
	private void loadConfig() {
		//load defualt config
		this.saveDefaultConfig();
			
		configManager = new ConfigManager();
		
		//setup children.yml
		configManager.setupChildren();
		configManager.saveChildren();
		configManager.reloadChildren();
		
		//setup players.yml
		configManager.setupPlayer();
		configManager.savePlayer();
		configManager.reloadPlayer();
		
		configIO = new ConfigIO(configManager);
		
	}
	
	public void reloadPluginConfig() {
		console.sendMessage("Reloading config data");
		this.reloadConfig();
		configManager.reloadChildren();
		configManager.reloadPlayer();
		console.sendMessage("config reloading complete");
	}
	
	
	//getters and setters
	public static Babycraft getInstance() {
		return instance;
	}

	public static void setInstance(Babycraft instance) {
		Babycraft.instance = instance;
	}

}
