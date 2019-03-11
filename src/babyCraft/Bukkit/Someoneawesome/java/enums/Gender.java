package babyCraft.Bukkit.Someoneawesome.java.enums;

public enum Gender {

	male(),
	
	female(),
	
	NULL();
	
	public static Gender fromString(String string) {
		if(string == null)
			return NULL;
		
		if(string.equalsIgnoreCase(ConfigValue.NULL.toString()))
			return NULL;
		
		if(string.equalsIgnoreCase(male.toString()))
			return male;
		else
			return female;
	}
}
