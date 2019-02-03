package Eindopdracht;

public class enemy {
	private String name;
	private item loot;

	public enemy(String name, item loot) {
		super();
		this.name = name;
		this.loot = loot;
	}

	public item getLoot() {
		return loot;
	}

	public String getName() {
		return name;
	}

	public String getItemName() {
		return loot.getName();
	}

}
