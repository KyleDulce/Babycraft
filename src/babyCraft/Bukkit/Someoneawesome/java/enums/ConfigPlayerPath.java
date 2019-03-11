package babyCraft.Bukkit.Someoneawesome.java.enums;

import java.util.*;

public enum ConfigPlayerPath {
	user {
		public String toString() {
			return ".username";
		}
	},
	gender {
		public String toString() {
			return ".gender";
		}
	},
	children {
		public String toString() {
			return ".children";
		}
	},
	preg {
		public String toString() {
			return ".preg";
		}
	},
	pregStat {
		public String toString() {
			return preg.toString() + ".pregStat";
		}
	},
	pregWith {
		public String toString() {
			return preg.toString() + ".pregWith";
		}
	},
	player {
		public String toString() {
			return "";
		}
	};
	
	public static String toPath(UUID uuid, ConfigPlayerPath path) {
		return uuid.toString() + path.toString();
	}
	
}
