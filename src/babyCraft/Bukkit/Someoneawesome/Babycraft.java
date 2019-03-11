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
	
	//instance of Plugin for use of other classes
	private static Babycraft instance;

	//Config File management
	private ConfigManager configManager;
	//management for interaction with config files
	public ConfigIO configIO;
	//Command Listener TODO: Replace with new manager
	public CommandManger cmdManager;
	//Tab Listener TODO: replace with new completer
	public TabCompleter tabcompleter;
	//Bukkit Console sender for use of program
	public ConsoleCommandSender console;

	//called when plugin is enabled
	@Override
	public void onEnable() {
		
		//setup console
		console = Bukkit.getServer().getConsoleSender();
		
		console.sendMessage("Enabling Babycraft");
		
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
		
		//TODO replace executor and completer
		getCommand("Babycraft").setExecutor(cmdManager);
		getCommand("Babycraft").setTabCompleter(tabcompleter);
		
		//finished enabling
		console.sendMessage("[Babycraft] Babycraft enabled");
	}
	
	//called when plugin is disabled
	@Override
	public void onDisable() {
		console.sendMessage("Disabling Babycraft");
		
		//dispawn any plugin entites
		Child.despawnAll();
		
		//save config files
		configManager.saveChildren();
		configManager.savePlayer();
		
		//notify disable
		console.sendMessage("Babycraft disabled");
	}
	
	//loads current state of config
	//(unloads without saving config state in memory)
	private void loadConfig() {
		console.sendMessage("Loading config data");
		
		//load defualt config
		this.saveDefaultConfig();
			
		//reload config manager
		configManager = new ConfigManager();
		
		//setup children.yml
		configManager.setupChildren();
		configManager.saveChildren();
		configManager.reloadChildren();
		
		//setup players.yml
		configManager.setupPlayer();
		configManager.savePlayer();
		configManager.reloadPlayer();
		
		//reload config IO manager
		configIO = new ConfigIO(configManager);
		
		console.sendMessage("Reloaded Config");
	}
	
	//reloads config state (without saving config)
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
