package babyCraft.Bukkit.Someoneawesome.java.GUIMenus;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers.*;

public class Menu {

	private static Babycraft plugin = Babycraft.getPlugin(Babycraft.class); 
	public static HashMap<UUID, Child> menu;
	
	//menulayout
	//Child's menu
	//	Childs Armor
	//    slots
	//    back
	//	interact button
	//    chat
	//    joke
	//    hug
	//    kiss
	//    back
	//  set home function
	//  go home
	//  child settings
	//	  Despawn button
	//    kill button
	//    skin
	//      all professions
	//    back
	//  movement settings
	//    follow
	//    roam
	//    stay
	//  exit
	
	//menu types
	//childs main menu
	public static void setMainMenu(Player player) {
		Inventory inv = plugin.getServer().createInventory(null, 9, ChatColor.AQUA + "Child's Menu");
		
		ItemStack armor = getItem(Material.IRON_CHESTPLATE, "Child's Armor", (short) 0, "Change your child's armor");
		ItemStack Interact = getItem(Material.SIGN, "Interact", (short) 0, "Interact with your child");
		ItemStack setHome = getItem(Material.RED_BED, "Set Home", (short) 0, "Set Your child's home");
		ItemStack goHome = getItem(Material.OAK_DOOR, "Go Home", (short) 0, "Send Your child home");
		ItemStack movement = getItem(Material.COMPASS, "Movement", (short) 0, "The movement settings for your child");
		ItemStack settings = getItem(Material.REDSTONE, "Settings", (short) 0, "Change the settings of your child");
		ItemStack close = getItem(Material.BARRIER, "close", (short) 0, "leave the menu");
		
		inv.setItem(0, armor);
		inv.setItem(1, Interact);
		inv.setItem(2, goHome);
		inv.setItem(3, movement);
		
		inv.setItem(6, setHome);
		inv.setItem(7, settings);
		inv.setItem(8, close);
		
		player.openInventory(inv);
	}
	
	// Armor menu
	public static void setArmorMenu(Player player, Child child) {
		Inventory inv = plugin.getServer().createInventory(null, 36, ChatColor.AQUA + "Child's Armor");
		
		//get armor values
		//null value
		ItemStack emptySlotHead = getItem(Material.BARRIER, "Helmet", (short) 0, "add armor to your child");
		ItemStack emptySlotBody = getItem(Material.BARRIER, "Body", (short) 0, "add armor to your child");
		ItemStack emptySlotLegs = getItem(Material.BARRIER, "Legs", (short) 0, "add armor to your child");
		ItemStack emptySlotFeet = getItem(Material.BARRIER, "Feet", (short) 0, "add armor to your child");
		
		//0 head 3 feet
		ItemStack[] armorConfig = new ItemStack[4];
		
		ItemStack[] armorC = plugin.configIO.getArmor(child.getUUID());
		
		if(armorC[0] != null) 
			armorConfig[0] = armorC[0];
		else
			armorConfig[0] = emptySlotHead;
		
		if(armorC[1] != null)
			armorConfig[1] = armorC[1];
		else
			armorConfig[1] = emptySlotBody;
		
		if(armorC[2] != null) 
			armorConfig[2] = armorC[2];
		else
			armorConfig[2] = emptySlotLegs;
		
		if(armorC[3] != null)
			armorConfig[3] = armorC[3];
		else
			armorConfig[3] = emptySlotFeet;
		
		//set the back menu items
		ItemStack back = getItem(Material.RED_WOOL, "Back",(short) 14, "Go back to the main menu");
		
		//put into inv
		inv.setItem(4, armorConfig[0]);
		inv.setItem(13, armorConfig[1]);
		inv.setItem(22, armorConfig[2]);
		inv.setItem(31, armorConfig[3]);
		
		inv.setItem(35, back);
		
		player.openInventory(inv);
	}
	
	// Interact menu
	public static void setInteractMenu(Player player) {
		Inventory inv = Bukkit.getServer().createInventory(null, 9, ChatColor.AQUA + "Interact");
		//	  chat
		//    joke
		//    hug
		//    kiss
		//    back
		
		ItemStack chat = getItem(Material.BOOK, "Chat", (short) 0, "Chat with your child");
		ItemStack joke = getItem(Material.TROPICAL_FISH, "Joke", (short) 2, "Tell a Joke to your child");
		ItemStack hug = getItem(Material.TOTEM_OF_UNDYING, "Hug", (short) 0, "Hug Your child");
		ItemStack kiss = getItem(Material.POPPY, "Kiss", (short) 0, "Kiss your child");
		ItemStack back = getItem(Material.RED_WOOL, "Back",(short) 14, "Go back to the main menu");
		
		inv.setItem(0, chat);
		inv.setItem(1, joke);
		inv.setItem(2, hug);
		inv.setItem(3, kiss);
		
		inv.setItem(8, back);
		
		player.openInventory(inv);
	}
	
	// Settings menu
	public static void setSettingMenu(Player player) {
		    //    Despawn button
			//    kill button
			//    skin
			//    back
		
		Inventory inv = Bukkit.getServer().createInventory(null, 9, ChatColor.AQUA + "Settings");
		
		ItemStack despawn = getItem(Material.DIAMOND_SWORD, "Despawn Child", (short) 0, "Despawn your child");
		ItemStack skin = getItem(Material.LEATHER_CHESTPLATE, "Clothes", (short) 0, "Change the color of your child's clothes");
		ItemStack kill = getItem(Material.RED_STAINED_GLASS_PANE, "REMOVE FROM EXISTANCE", (short) 14, "REMOVE CHILD FROM EXISTANCE, CANNOT BE UNDONE");
		ItemStack back = getItem(Material.RED_WOOL, "Back",(short) 14, "Go back to the main menu");
		
		inv.setItem(0, despawn);
		inv.setItem(1, skin);
		inv.setItem(4, kill);
		
		inv.setItem(8, back);
		
		player.openInventory(inv);
	}
	
	public static void setVerifyMenu(Player player) {
		Inventory inv = Bukkit.getServer().createInventory(null, 9, ChatColor.RED + "Are you sure");
		
		ItemStack yes = getItem(Material.RED_WOOL, "Yes", (short) 14, ChatColor.RED + "THIS ACTION CANNOT BE UNDONE");
		ItemStack info = getItem(Material.COMPASS, "Are you sure you want to remove this child?", (short) 0, "");
		ItemStack no = getItem(Material.GREEN_WOOL, "No", (short) 5, "");
		
		inv.setItem(2, yes);
		inv.setItem(4, info);
		inv.setItem(6, no);
		
		player.openInventory(inv);
	}
	
	public static void setMovementMenu(Player player) {
		Inventory inv = Bukkit.getServer().createInventory(null, 9, ChatColor.AQUA + "Child's Movement Settings");
		
		ItemStack follow = getItem(Material.COMPASS, "Follow me", (short) 0, "Have your child follow you");
		ItemStack stay = getItem(Material.BEDROCK, "Stay here", (short) 0, "Have your child stay here");
		ItemStack roam = getItem(Material.FEATHER, "Roam Around", (short) 0, "Have your child roam around");
		ItemStack back = getItem(Material.RED_WOOL, "Back",(short) 14, "Go back to the main menu");
		
		inv.setItem(0, follow);
		inv.setItem(1, stay);
		inv.setItem(2, roam);
		
		inv.setItem(8, back);
		
		player.openInventory(inv);
	}
	
	private static ItemStack getItem(Material mat, String itemName, Short damage, String lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta item_meta = item.getItemMeta();
		item_meta.setDisplayName(itemName);
		ArrayList<String> list = new ArrayList<>();
		list.add(lore);
		item_meta.setLore(list);
		item.setItemMeta(item_meta);
		return item;
	}
	
}
