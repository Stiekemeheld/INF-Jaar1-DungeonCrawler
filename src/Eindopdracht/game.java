package Eindopdracht;

import java.util.Scanner;

import java.util.Random;

public class game {
	/*
	 * Voeg alle items toe
	 */
	item schep = new item("schep",
			"U heeft een schep ontvangen. Met deze schep kunt u een gat graven en misschien een schatkist vinden",
			"U heeft de schep geactiveerd u bent nu aan het graven");
	item gieter = new item("gieter", "U heeft een gieter ontvangen hiermee kunt u planten water geven.",
			"De gieter is nu geactiveerd u bent aan het gieteren.");
	item plantenspuit = new item("plantenspuit", "U heeft een gieter ontvangen hiermee kunt u planten water geven.",
			"De gieter is nu geactiveerd u bent aan het gieteren.");
	item sleutel = new item("sleutel", "U heeft een sleutel ontvangen hiermee kunt u de shatkist openen",
			"De sleutel is geactiveerd en de schatkist is openn.");
	item schatkist = new item("schatkist", "Met de sleutel kunt u hem openmaken en de buit bemachtigen",
			"Het geld is nu bemachtigd.");
	item vuursteen = new item("vuursteen", "", "");
	item shotgun = new item("shotgun", "Met de shotgun verslaat u alle vijanden gelijk.", "");
	room teleportRoom = new room("teleportatiekamer u wordt nu geteleporteerd");

	/*
	 * Maak een array aan voor alle kamers, dit is nodig zodat je later naar een
	 * random kamer kan teleporteren
	 */
	room[] room = { new room("startkamer"), new room("deatchamber"), new room("firenest"), new room("cuarto rojo"),
			new room("thunder dome"), new room("slayers appartment"), new room("smelly prison"), new room("mad puddle"),
			new room("sword storage"), new room("dragons room"),
			new room("laatstse kamer gefeliciteerd u heeft gewonnen!!!") };
	/*
	 * Maak enemys aan
	 */
	enemy goblin = new enemy("goblin", sleutel);
	enemy draak = new enemy("draak", schatkist);

	Scanner scanIn = new Scanner(System.in); // Open de scanner zodat input gelezen kan worden

	public void initializegame() {
		/*
		 * Voeg de spelers toe
		 */
		player player1 = new player("Player 1", room[0]);
		player player2 = new player("Player 2", room[0]);
		/*
		 * voeg de items toe in alle kamers
		 */
		room[1].additem(schep);
		room[3].additem(gieter);
		room[5].additem(plantenspuit);
		room[7].additem(vuursteen);
		room[2].additem(shotgun);

		/*
		 * voeg de vijanden toe in de kamers
		 */
		room[9].addenemy(draak);
		room[2].addenemy(goblin);
		/*
		 * voeg de aanliggende kamers toe 0 is north 1 is east 2 is south 4 is west
		 */
		room[0].connectingrooms[0] = room[1];
		room[0].connectingrooms[1] = room[2];

		room[1].connectingrooms[2] = room[0];
		room[1].connectingrooms[1] = room[3];
		room[1].connectingrooms[3] = teleportRoom;

		room[2].connectingrooms[3] = room[0];
		room[2].connectingrooms[0] = room[3];

		room[3].connectingrooms[0] = room[4];
		room[3].connectingrooms[1] = room[5];
		room[3].connectingrooms[2] = room[2];
		room[3].connectingrooms[3] = room[1];

		room[4].connectingrooms[1] = room[7];
		room[4].connectingrooms[2] = room[3];

		room[5].connectingrooms[0] = room[7];
		room[5].connectingrooms[1] = room[6];
		room[5].connectingrooms[3] = room[3];

		room[6].connectingrooms[2] = room[8];
		room[6].connectingrooms[3] = room[5];

		room[7].connectingrooms[1] = room[9];
		room[7].connectingrooms[2] = room[5];
		room[7].connectingrooms[3] = room[4];

		room[8].connectingrooms[0] = room[6];

		room[9].connectingrooms[0] = room[10];
		room[9].connectingrooms[3] = room[7];

		room[10].connectingrooms[3] = room[9];
		/*
		 * Welkomtekst
		 */

		System.out.println("Welkom bij de dungeon crawler van Jonah Schmitz");
		System.out.println("Dit is een project dat hij gemaakt heeft voor het vak PROG2 op Avans.");
		System.out.println("Met hoeveel mensen wilt u spelen?");
		System.out.println("U kunt kiezen uit 1 of 2.");

		int players = 0; // 0 players zodat de while loop door blijft gaan
		String input;
		while (players == 0) {

			input = getInput();

			try {
				players = Integer.parseInt(input); // maak van de inputstring een integer

				if (players == 1) {// als er 1 player is verander de naam naar u zodat alle tekst klopt
					player1.setName("u");
					playgame(player1, player2, players);
				} // start het spel op singleplayer
				else if (players == 2)// als er 2 spelers zijn

					playgame(player1, player2, players);

			} catch (NumberFormatException e) {// als de input geen integer was
				System.out.println("Dit is geen goede hoeveelheid");
			}

		}
		scanIn.close();
	}

	private String getInput() {

		return scanIn.nextLine(); // return de input van user als een string
	}

	private void playgame(player player1, player player2, int howmanyplayers) {

		int playing = 0;// maak playing 0, dit zorgt ervoor dat de loop blijft lopen tot de speler stopt
		String input = "";
		if (howmanyplayers == 1) {
			System.out.println("-----------------------");
			System.out.println("U bevind zich in de " + player1.getCurrentroom().getName());
			System.out.println("Commando's kunnen uitgevoerd worden zonder eerst de player aan te roepen.");
			while (playing == 0) {
				playing = playerActions(player1, getInput()); // omdat er maar 1 speler is roep gelijk de actiefunctie
																// aan met de input
			}
		} else if (howmanyplayers == 2) {
			System.out.println("-----------------------");
			System.out.println("player1 bevindt zich in de " + player1.getCurrentroom().getName());
			System.out.println("player2 bevindt zich in de " + player2.getCurrentroom().getName());
			System.out.println("Commando's kunnen uitgevoerd worden door eerst de player aan te roepen.");
			int stoppedPlayers = 0;// zodat er nog niemand gestopt is
			int player1quit = 0;
			int player2quit = 0;
			while (playing == 0) {
				input = getInput();
				if (input.startsWith("player1") && player1quit == 0) // als het commando voor player1 is
				{
					player1quit = stoppedPlayers + playerActions(player1, input); // als speler stopt maakt het de
																					// variabele 1 hoger
					stoppedPlayers = stoppedPlayers + player1quit;
				} else if (input.startsWith("player2") && player2quit == 0) {
					stoppedPlayers = stoppedPlayers + playerActions(player2, input); // als deze ook stopt maakt het de
																						// variabele nog 1 hoger
					stoppedPlayers = stoppedPlayers + player2quit;
				} else {
					if (input.contains("player1") && player1quit == 1)
						System.out.println("Deze speler is helaas gestopt.");
					else if (input.contains("player2") && player2quit == 1)
						System.out.println("Deze speler is helaas gestopt.");
					else {
						helpFunction(howmanyplayers);
					}
				}

				if (stoppedPlayers == 2)
					playing = 1;// als er 2 spelers gestopt zijn stopt t spel

			}

		}

	}

	private int playerActions(player player, String input) { // geeft de player mee en de inputstring

		String command = "";
		String subCommand = "";

		if (input.contains("player1") || input.contains("player2")) { // als de string player bevat
			String[] opdelen = input.split(" ");
			if(opdelen.length >2) {
				input = opdelen[1] + " " + opdelen[2];

			} else if (opdelen.length == 2){
					input = opdelen[1];
				} else  {
					input = "";
				}
			}

		

		if ((input.contains(" "))) { // als het commando uit 2 delen bestaat split de delen op
			String[] opdelen = input.split(" ");
			if (opdelen.length > 1) {
				command = opdelen[0] + "";
				subCommand = opdelen[1];
			} else {
				command = "";
			}
		} else {
			command = input;
			subCommand = "";

		}
		switch (command) { // voor elk eerste deel van het commando roep dan de bijbehorende functie aan
		case "go":
			goFunction(subCommand, player);
			return 0;
		case "use":
			useFunction(subCommand, player);
			return 0;
		case "get":
			pickFunction(subCommand, player);
			return 0;
		case "help":
			helpFunction(1);
			return 0;
		case "drop":
			dropFunction(subCommand, player);
			return 0;
		case "quit":
			quitFunction(player);
			return 1;
		case "pack":
			userItems(player);
			return 0;
		case "look":
			lookRoom(player);
			return 0;
		case "fight":
			fightEnemy(subCommand, player);
			return 0;
		case "loot":
			lootEnemy(subCommand, player);
			return 0;
		default:
			System.out.println("Typ help voor alle commando's");
			return 0;
		}

	}

	private void quitFunction(player player) { // laatstefunctie die alles laat zien
		System.out.println(player.getName() + " heeft het spel beeindigd.");
		System.out.println("De items die " + player.getName() + " bemachtigd heeft zijn:" + player.getItems());

	}

	private void lootEnemy(String subCommand, player player) {
		if (player.getCurrentroom().getEnemy() != "") { // als er een enemy in de kamer zit
			player.getCurrentroom().lootEnemy(player); // loot de enemy
		} else {
			System.out.println("Er is geen enemy in deze kamer om te looten.");
		}

	}

	private void fightEnemy(String subCommand, player player) {

		if (player.getCurrentroom().getEnemy() != "") { // als er een enemy is om tegen te vechten val m aan
			switch (subCommand) {
			case "goblin":
				player.getCurrentroom().fightEnemy(goblin, player, player.searchItem(shotgun));
				break;
			case "draak":
				player.getCurrentroom().fightEnemy(draak, player, player.searchItem(shotgun));
				break;
			default:
				System.out.println("Deze vijand bestaat niet.");
				break;
			}
		} else {

			System.out.println("Er is geen vijand om tegen te vechten.");
		}

	}

	private void userItems(player player) { // welke items de user heeft
		System.out.println("De items die " + player.getName() + " nu in bezit heeft zijn:" + player.getItems());

	}

	private void lookRoom(player player) { // items in de kamer en doorgangen
		System.out.println("De items die in deze kamer zijn:" + player.getCurrentroom().getItems());
		System.out.println("De richtingen waar " + player.getName() + " heen kunt zijn:"
				+ player.getCurrentroom().returnConnectingRooms());

	}

	private void goFunction(String subCommand, player player) {

		switch (subCommand) {
		case "north":
			if (player.getCurrentroom() != player.getCurrentroom().getConnectingroom(0, player, 0))
				player.setCurrentroom(player.getCurrentroom().getConnectingroom(0, player, 1));

			else
				System.out.println("Er is geen kamer deze kant op.");
			break;
		case "east":
			if (player.getCurrentroom() != player.getCurrentroom().getConnectingroom(1, player, 0))
				player.setCurrentroom(player.getCurrentroom().getConnectingroom(1, player, 1));
			else
				System.out.println("Er is geen kamer deze kant op.");
			break;
		case "south":
			if (player.getCurrentroom() != player.getCurrentroom().getConnectingroom(2, player, 0))
				player.setCurrentroom(player.getCurrentroom().getConnectingroom(2, player, 1));
			else
				System.out.println("Er is geen kamer deze kant op.");
			break;
		case "west":
			if (player.getCurrentroom() != player.getCurrentroom().getConnectingroom(3, player, 0))
				player.setCurrentroom(player.getCurrentroom().getConnectingroom(3, player, 1));
			else
				System.out.println("Er is geen kamer deze kant op.");
			break;
		default:
			System.out.println("U kunt kiezen uit North, East, South, West");
		}
		if (player.getCurrentroom().getEnemy() != "") {
			System.out.println("Er bevind zich een " + player.getCurrentroom().getEnemy() + " in deze kamer!");
			System.out.println("Gebruik 'fight' + naam om hem aan te vallen");
		}
		if (player.getCurrentroom() == teleportRoom) {

			Random rand = new Random();
			int n = rand.nextInt(10) + 1;// random nummer tussen 1 en 10
			System.out.println(player.getName() + " wordt nu geteleporteerd naar de " + room[n].getName());
			player.setCurrentroom(room[n]);// maak de huidige kamer de kamer waar speler naartoe geteleporteerd is

		}

	}

	private void useFunction(String subCommand, player player) {
		switch (subCommand) {
		case "schep":
			player.useItem(schep);
			break;
		case "gieter":
			player.useItem(gieter);
			break;
		case "plantenspuit":
			player.useItem(plantenspuit);
			break;
		case "sleutel":
			if (player.searchItem(schatkist) == 1) {
				player.useItem(sleutel);
			} else
				System.out.println(player.getName() + " heeft geen schatkist om de sleutel voor te gebruiken");
			break;
		case "schatkist":
			if (player.searchItem(sleutel) == 1) {
				player.useItem(schatkist);
			} else {
				System.out.println("U heeft geen sleutel.");
			}

			break;
		case "vuursteen":
			player.useItem(vuursteen);
			break;
		case "shotgun":
			player.useItem(shotgun);
			break;
		default:
			System.out.println("Dit is helaas geen item in dit spel");
			break;
		}
	}

	private void pickFunction(String subCommand, player player) {
		switch (subCommand) {
		case "schep":
			player.getCurrentroom().pickitem(schep, player);
			break;
		case "gieter":
			player.getCurrentroom().pickitem(gieter, player);
			break;
		case "plantenspuit":
			player.getCurrentroom().pickitem(plantenspuit, player);
			break;
		case "sleutel":
			player.getCurrentroom().pickitem(sleutel, player);
			break;
		case "schatkist":
			if (player.searchItem(schep) == 1) {
				player.getCurrentroom().pickitem(schatkist, player);
			} else {
				System.out.println(player.getName() + " heeft geen schep om de schatkist mee op te graven");
			}
			break;
		case "vuursteen":
			player.getCurrentroom().pickitem(vuursteen, player);
			break;
		case "shotgun":
			player.getCurrentroom().pickitem(shotgun, player);
			break;
		default:
			System.out.println("Dit is helaas geen item in dit spel");
			break;
		}
	}

	private void dropFunction(String subCommand, player player) {
		switch (subCommand) {
		case "schep":
			player.getCurrentroom().dropitem(schep, player);
			break;
		case "gieter":
			player.getCurrentroom().dropitem(gieter, player);
			break;
		case "plantenspuit":
			player.getCurrentroom().dropitem(plantenspuit, player);
			break;
		case "sleutel":
			player.getCurrentroom().dropitem(sleutel, player);
			break;
		case "schatkist":
			player.getCurrentroom().dropitem(schatkist, player);
			break;
		case "vuursteen":
			player.getCurrentroom().dropitem(vuursteen, player);
			break;
		case "shotgun":
			player.getCurrentroom().dropitem(shotgun, player);
			break;
		default:
			System.out.println("Dit is helaas geen item in dit spel");
			break;
		}
	}

	private void helpFunction(int players) {
		System.out.println("-----------------------");

		if (players == 2) {
			System.out.println("Begin alle commando's met player1 of player2");
		}
		System.out.println("Typ use + itemnaam om een item te gebruiken");
		System.out.println("Typ get + itemnaam om een item op te rapen");
		System.out.println("Typ drop + itemnaam om een item neer te leggen");
		System.out.println("Typ quit om te stoppen met spelen");
		System.out.println("Typ go plus richting om je te verplaatsen.");
		System.out.println("Typ pack om te kijken wat in je tas zit.");
		System.out.println("Typ look om te kijken welke items zich in de kamer bevinden.");
		System.out.println("Typ fight + vijandnaam om de vijand aan te vallen");
		System.out.println("Typ loot om de vijand zijn buit te bemachtigen");
	}
}