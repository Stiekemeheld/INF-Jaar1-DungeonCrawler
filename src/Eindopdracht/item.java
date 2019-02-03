package Eindopdracht;

public class item {
	private String name;
	private String usageText;
	private String itemusing;

	public void use() {// print using tekst
		System.out.println(itemusing);
	}

	public String getName() {// return naam
		return this.name;
	}

	public String getusagetext() {
		return usageText;// pring hoe je het item moet gebruiken
	}

	public item(String name, String usageText, String itemusing) {
		super();
		this.name = name;
		this.usageText = usageText;
		this.itemusing = itemusing;
	}
}