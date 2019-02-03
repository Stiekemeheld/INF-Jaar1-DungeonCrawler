package Eindopdracht;

import java.util.ArrayList;

public class player {
	private ArrayList<item> items = new ArrayList<item>();
	private String playernaam;
	private room currentroom;

	public player(String playernaam, room currentroom) {
		super();
		this.playernaam = playernaam;
		this.currentroom = currentroom;
	}

	public void useItem(item item) { // als de gebruiker het item heeft kan hij hem gebuiken
		if (items.contains(item)) {
			item.use();
		} else if (currentroom.getItems().contains(item.getName())) {
			item.use();
		} else {
			System.out.println("Dit item heeft u helaas niet in uw tas zitten");
		}
	}

	public void setName(String s) {
		this.playernaam = s;
	}

	public String getName() {
		return this.playernaam;
	}

	public String getItems() { // return een string van alle items in de tas

		String itemstring = "";
		if (items.size() == 0)
			itemstring = " geen items";
		{
			for (int i = 0; i < items.size(); i++) {
				itemstring = itemstring + " " + items.get(i).getName();

			}
		}
		itemstring = itemstring + ".";
		return itemstring;
	}

	public room getCurrentroom() { // return de huidige room
		return currentroom;
	}

	public void setCurrentroom(room currentroom) {// verander de huidige room
		this.currentroom = currentroom;

	}

	public void addItem(item item) {// voeg item toe aan de arraylist van items
		items.add(item);
	}

	public void removeItem(item item) {// verwijder item
		items.remove(item);
	}

	public int searchItem(item item) {// zoek of de user het item heeft
		if (items.contains(item))
			return 1;
		else
			return 0;
	}

}
