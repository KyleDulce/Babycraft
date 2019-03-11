package babyCraft.Bukkit.Someoneawesome.java.enums;

public enum BcPermissions {

	reload("babyCraft.Admin.reload"),
	children("babyCraft.children"),
	solo("babyCraft.solo"),
	sameGender("babyCraft.sameGender"),
	changeBabyGender("babyCraft,changeGender"),
	Despawn("babyCraft.Admin.DespawnAll"),
	Save("babyCraft.Admin.saveConfig");
	
	private String value;
	
	BcPermissions(String value) {
		this.value = value;
	}
	
	public String get() {
		return value;
	}
}
