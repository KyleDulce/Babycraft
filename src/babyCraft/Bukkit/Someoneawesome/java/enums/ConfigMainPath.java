package babyCraft.Bukkit.Someoneawesome.java.enums;

import java.util.*;

public enum ConfigMainPath {
	PREGNANCY("simulatePregnancy", null)
	;
	private String path;
	private ConfigMainPath parent;
	private ConfigMainPath(String p, ConfigMainPath parent) {
		path = p;
		this.parent = parent;
	}
	
	public static final List<ConfigMainPath> l = Collections.unmodifiableList(Arrays.asList(values()));
	
	public static String toPath(ConfigMainPath path) {
		if(path.parent == null) {
			return path.path;
		} else {
			return toPath(path.parent);
		}
	}
}
