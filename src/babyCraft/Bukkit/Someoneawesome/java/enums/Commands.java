package babyCraft.Bukkit.Someoneawesome.java.enums;

import java.util.*;

import babyCraft.Bukkit.Someoneawesome.java.Managers.commandManagers.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.commandManagers.bcCommands.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.commandManagers.adCommands.*;

public enum Commands {
	
	BabycraftPrefix_bc("bc"),
	BabycraftPrefx_baby("baby"),
	
	HaveChild_haveBaby("HaveBaby"),
	HaveChild_child("child"),
	HaveChild_have("have"),
	HaveChild_hc("hc"),
	HaveChild("HaveChild", new Commands[] {
			HaveChild_child,
			HaveChild_have,
			HaveChild_haveBaby,
			HaveChild_hc
	}, new BcCommandHaveChild()),	
	
	AdoptChild_adopt("Adopt"),
	AdoptChild_a("a"),
	AdoptChild("AdoptChild", new Commands[] {
			AdoptChild_a,
			AdoptChild_adopt
	}, new BcCommandAdoptChild()),	
	
	SetGender_gender("gender"),
	SetGender_set("set"),
	SetGender_g("g"),
	SetGender("setGender", new Commands[] {
			SetGender_g,
			SetGender_gender,
			SetGender_set
	}, new BcCommandSetGender()),	
	
	SpawnChild_spawn("spawn"),
	SpawnChild_s("s"),
	SpawnChild("SpawnChild", new Commands[] {
			SpawnChild_s,
			SpawnChild_spawn
	}, new BcCommandSpawnChild()),	
	
	DespawnChild_despawn("despawn"),
	DespawnChild_d("d"),
	DespawnChild("DespawnChild", new Commands[] {
			DespawnChild_d,
			DespawnChild_despawn
	}, new BcCommandDespawnChild()),
	
	WarpChild_warp("warp"),
	WarpChild_home("home"),
	WarpChild_goHome("goHome"),
	WarpChild_warpHome("warpHome"),
	WarpChild_h("h"),
	WarpChild_w("w"),
	WarpChild("WarpChild", new Commands[] {
			WarpChild_warp,
			WarpChild_w,
			WarpChild_home,
			WarpChild_h,
			WarpChild_goHome,
			WarpChild_warpHome
	}, new BcCommandWarpChild()),
	
	//side commands
	Name("Name", new BcCommandName()),	
	Accept("Accept", new BcCommandAccept()),	
	Deny("Deny", new BcCommandDeny()),
	
	//misc cmds
	List("List", new BcCommandList()),
	DespawnAll("DespawnAll", new AdCommandDespawnAll()),
	
	//admin cmds
	
	Admin_Reload("Reload", new AdCommandReload()),
	Admin_REWRITECONFIG("RestartAllConfigData", new AdCommandRewriteCfg()),
	Admin_saveConfig("saveConfig", new AdCommandSaveConfig()),
	
	AdminPrefix("BcAdmin", new Commands[] {
			Admin_Reload,
			Admin_REWRITECONFIG,
			Admin_saveConfig,
			DespawnAll
	}),
	BabycraftPrefix("Babycraft", new Commands[] {
			BabycraftPrefix_bc,
			BabycraftPrefx_baby
	}, new Commands[] {
			HaveChild,
			AdoptChild,
			SetGender,
			SpawnChild,
			DespawnChild,
			WarpChild,
			List,
			Name,
			Accept,
			Deny
	})
	;
	
	private String value;
	private boolean isSuper;
	private Commands[] alias;
	private Commands[] parentCommand;
	private boolean isParent;
	private BcCommand executor;
	
	private Commands(String value) {
		this.value = value;
		isSuper = false;
		isParent = false;
	}
	
	Commands(String value, BcCommand executor) {
		this.value = value;
		isSuper = true;
		isParent = false;
		this.executor = executor;
	}
	
	Commands(String value, Commands[] children) {
		this.value = value;
		isSuper = true;
		isParent = true;
		parentCommand = children;
	}
	
	Commands(String value, Commands[] alias, BcCommand executor) {
		this.value = value;
		this.alias = alias;
		isSuper = true;
		isParent = false;
		this.executor = executor;
	}
	
	Commands(String value, Commands[] alias, Commands[] children) {
		this.value = value;
		this.alias = alias;
		isSuper = true;
		isParent = true;
		parentCommand = children;
	}
	
	public String toString() {
		return value;
	}
	
	public BcCommand getExecutor() {
		return executor;
	}
	
	private static final List<Commands> l = Collections.unmodifiableList(Arrays.asList(values()));
	
	public static boolean isCommand(String args, Commands cmd) {
		//test against cmd value
		if(args.equalsIgnoreCase(cmd.value))
			return true;
		
		//if the cd is a super command
		if(cmd.isSuper) {
			
			//run through alias
			for(int x = 0; x < cmd.alias.length; x++) {
				
				//test against
				if(args.equalsIgnoreCase(cmd.alias[x].value))
					return true;
				else continue;
			}
			//otherwise return false
			return false;
		} 
		
		//if not then stop
		return false;
	}
	
	@Deprecated
	public static List<String> getMajorCmdList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add(HaveChild.toString());
		list.add(AdoptChild.toString());
		list.add(SetGender.toString());
		list.add(SpawnChild.toString());
		list.add(DespawnChild.toString());
		list.add(WarpChild.toString());
		list.add(List.toString());
		list.add(AdminPrefix.toString());
		
		return list;
	}
	
	@Deprecated
	public static List<String> getAdminCmdList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add(Admin_Reload.toString());
		list.add(Admin_saveConfig.toString());
		list.add(DespawnAll.toString());
		
		return list;
	}
	
	public static List<Commands> getChildren(Commands cmd) {
		if(!cmd.isParent)
			return null;
		return Arrays.asList(cmd.parentCommand);
	}
	
	public static List<Commands> getAllParents() {
		List<Commands> out = new ArrayList<>();
		for(Commands i : l) {
			if(i.isParent)
				out.add(i);
		}
		return out;
	}
	
}
