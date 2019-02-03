package Eindopdracht;

import java.util.ArrayList;
import java.util.Random;

public class room {
	private String description;
	private ArrayList<item> items = new ArrayList<item>();
	private enemy enemyinroom = null;
	room[] connectingrooms = { this, this, this, this }; // N,E,S,W zorg dat alle connectingrooms zichzelf zijn, en dan
															// wordt het later aangepast met het aanmaken
	private int enemydefeated = 0;

	public room(String description) {
		super();
		this.description = description;
	}

	public String getItems() {
		// maak een string van de naam van alle items in de kamer
		String itemstring = " ";
		if (items.size() == 0)
			itemstring = " geen items";
		{
			for (int i = 0; i < items.size(); i++) {
				itemstring = itemstring + items.get(i).getName() + " ";

			}
		}
		itemstring = itemstring + ".";
		return itemstring;
	}

	public String getName() {
		return description;
	}

	public room getConnectingroom(int direction, player player, int moveint) {

		if (connectingrooms[direction] != this) { // kijk of de kamer die ermee verbonden is ongelijk is aan de kamer
													// waar je nu in zit

			if (moveint == 1) {
				System.out.println(player.getName() + " heeft zich verplaatst van de " + this.getName() + " naar de "
						+ connectingrooms[direction].getName());
			}
			return connectingrooms[direction];

		}
		return this;
	}

	public void additem(item item) {// add item in de arraylist van items
		this.items.add(item);
	}

	public void dropitem(item item, player Player) { // als de speler een item bezit, drop hem in de huidige kamer
		int ifitem = Player.searchItem(item);
		if (ifitem == 1) {
			items.add(item);
			Player.removeItem(item);
			System.out.println("U heeft nu de " + item.getName() + " achtergelaten in de kamer.");
		} else
			System.out.println("U heeft dit item niet in het bezit");
	}

	public String returnConnectingRooms() {
		String Stringconnectingrooms = " "; // String voor alle connectingrooms
		for (int i = 0; i < 4; i++) {
			if (connectingrooms[i] != this) {
				switch (i) {
				case 0:
					Stringconnectingrooms = Stringconnectingrooms + "north, ";
					break;
				case 1:
					Stringconnectingrooms = Stringconnectingrooms + "east, ";
					break;
				case 2:
					Stringconnectingrooms = Stringconnectingrooms + "south, ";
					break;
				case 3:
					Stringconnectingrooms = Stringconnectingrooms + "west, ";
					break;
				}
			}
		}
		return Stringconnectingrooms;

	}

	public void lootEnemy(player player) { // als de enemy verslagen is krijgt de player de loot
		if (enemydefeated == 1) {
			player.addItem(this.enemyinroom.getLoot());
			System.out.println("Enemy geloot, u heeft nu een " + enemyinroom.getItemName() + " ontvangen.");
		} else {
			System.out.println("U moet eerst de vijand verslaan.");
		}
	}

	public void fightEnemy(enemy enemy, player player, int gun) {// als er een enemy is kan de speler hem aanvallen
		if (enemy == enemyinroom) {
			System.out.println("U bent in gevecht");
			Random rand = new Random();
			int n = 0;
			if (gun == 0)
				n = rand.nextInt(2) + 1;// 50 procent kans om te winnen
			else if (gun == 1) // als de speler een shotgun heeft verslaat hij de enemy gelijk
			{
				System.out.println("Met de shotgun is de vijand gelijk verslagen.");
				n = 1;
			}
			if (n == 1) {
				System.out.println("");
				System.out.println("U heeft het gevecht gewonnen, u kunt nu de vijand looten");
				enemydefeated = 1;

			}
			if (n == 2)
				System.out.println("U heeft het gevecht verloren.");
		}
	}

	public void addenemy(enemy enemy) {// voeg een enemy toe
		this.enemyinroom = enemy;
	}

	public String getEnemy() {// return de enemynaam
		if (enemyinroom == null)
			return "";
		else {
			return this.enemyinroom.getName();
		}
	}

	public void pickitem(item item, player Player) {// als het item in de kamer is geef hem aan de speler
		if (items.contains(item)) {
			items.remove(item);
			Player.addItem(item);
			System.out.println("U heeft de " + item.getName() + " opgeraapt, hij zit nu in de rugzak");
		} else
			System.out.println("Dit item bevind zich helaas niet in deze kamer.");

	}
}
