package Bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

/* 
 * Control Center for game that handles interaction between board and receiver class.
 * Receiver : 
 *      take message from users
 *       call appropriate functions of controller according to message  (such as start new game, next move info 
 *                (trade, roll dice, next turn, buy property ,... ) , reset game ,.... )
 *       take action result from controller
 *        return appropriate message to chat depend on that action result 
 *
 * Controller:
 * 		  keep track of game status (GameState)
 *        take message from receiver
 *        manage logic and run the game. manipulate Board info as necessary
 *        update game state (if a person wins or they decide to concede the game early ->> here we calculate wealth 
 *                    and decides the winner)
 *        return  action result to receiver
 *
 *        Note : controller should have all access to board variables and handles the game logic
 *
 * Board: 
 *        Update board states and player states
 *        Functions in board when called should only perform its task such as move player, add properties, 
 *                 add/subtract money, all logic whether an action is valid should be done in controller.
 * 
 * 
 **/
public class Game_Control_Center {
	
	private int 	gameState = 0 ; // 0 = uninitialized , 1 = setting up, 2 = running , 3 = finished or not running
	private String 	playerEmojis[] = new String[]{":pickup_truck:",":race_car:",":bus:",":motorcycle:"};
	private Board 	board;
	private MessageChannel channel;
	private EmbedBuilder embed;
	
	public Game_Control_Center() {
		gameState = 1 ;
		board = new Board();
		
	}
	
	/* Receive corresponding action from message received in Receiver. 
	 * Call appropriate function in Control Center to handle instruction.
	 * Return result message to receiver.
	 * 
	 * actionID : 
	 * 1-4 = player join, correspondingly to position in array of players
	 * 5   = next turn
	 * 6   = buy properties
	 * 7   = invoke trades
	 * */

	public int getState() {
		return gameState;
	}
	
	/*  
	   *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
	 * ALL PRIVATE HELPER METHODS for gameAction function go down here  * 
	   *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  
	 * */

	Board getBoard() {
		return board;
	}
	private void sendGenericEmbed(String title, String description, String footer) {
		if ( (title != null) && !(title.equals(""))) //if not null string or empty String
			embed.setTitle(title);
		if ( (description != null) && description.length() < 2048) // max length for an embed description.
			embed.setDescription(description);
		else embed.setDescription("Invalid description String. Cannot exceed 2048 or null");
		
		embed.setFooter(footer); // doesn't need to handle exception since setFooter accepts null
		channel.sendMessage(embed.build()).queue();
	}
	
	public void run(EmbedBuilder embed, MessageChannel channel, String input, String userID) {
		this.embed = embed;
		if(this.channel == null)
			this.channel = channel;
		if(this.channel == channel) {
			if(input.equals("!play")) {
				//if there has been an instance of the game. Send error message and return function
				if (gameState == 2) {
					sendGenericEmbed(null, "Failure to start! Another instance is already running. Type !reset to start a new game.", null);
					return;
				}
				else {
					sendGenericEmbed("Welcome to Discord Monopoly!" ,
							 "Select your player emoji. Type \"!join\" followed with the number you wish to join as\nFor example: \"!join1\"\n**1** :pickup_truck:\n**2** :race_car:\n**3** :bus:\n**4** :motorcycle:" ,
							 "2-4 Players required\nPlayers cannot choose the same emoji.\nType \"!start\" when ready.\nType \"commands\" for help." );
					board = new Board();
					gameState = 1;
				
				}
			}
			else if(input.contains("!join")) {
				if(gameState == 1)
					joinReceiver(input, userID);
				else if(gameState == 0 || gameState == 3) //If there is no running state
					sendGenericEmbed(null, "You haven't started a game.",null);
				else if(gameState == 2) //if the game is already running
					sendGenericEmbed("FAIL", "Can't join running game.",null);
			}
			else if(input.equals("!leave")) {
				leaveGame(userID);
				sendGenericEmbed("You left!", "<@"+userID+">",null);
				if(board.numPlayers < 2 && gameState == 2) { //Ends game and resets values if not enough players
					sendGenericEmbed("Game ended, Not enough players","Player has left, leaving you alone in the game. The game has ended", null);
					board = new Board();
					gameState = 3;
				}
			}
			else if(input.equals("!printboard"))
				board.printBoard();
			else if(input.equals("commands") || input.equals("!commands"))
				sendGenericEmbed("Commands:","**!play** to start game.\n**!join 1/2/3/4** to join game.\n**!leave** to leave game\n**!start** to start game.",null);
			
			//Functions for running game
			else if(input.equals("!start") && gameState == 1) {
				if(board.numPlayers >= 1) {
					gameState = 2;
					printboard();
				}
				else
					sendGenericEmbed("Not enough players!", "You need at least 2 players to start",null);
			}
			else if(gameState == 2 && input.contains("<@") && input.contains("!giveme")) {
				
				
			}
			else if(input.equals("take my money") && gameState == 2) {
				board.getPlayer().setMoney(0);
				sendGenericEmbed("You lost all of your money", "<@"+board.getPlayer().getId()+">",null);
			}
			/*else if(input.equals("take my money") && gameState == 2) {
				board.getPlayer().setMoney(0);
				sendGenericEmbed("You lost all of your money", "<@"+board.getPlayer().getId()+">",null);
			}*/
				
			//Functions for 
			else if(gameState == 2 && board.playerList[board.getCurrPlayer()].getId().equals(userID)) {
				//moveState = 0 if there is nothing the user can input after moving
 				//moveState = 1 if the user can buy the property landed on
 				//moveState = 2 if the user landed on an owned tile
				//moveState = 3 if the user landed on chance
				//moveState = 4 if the user was sent to jail
				//moveState = 5 if the user is out of money
				if((input.equals("d") || input.contains("d ")) && !(board.getCurrTile().getType() != 3 && board.getPlayer().getInJail() == true)) { //To roll dice, command instructions will be given in the footer of each print board
					Player currentPlayer = board.playerList[board.getCurrPlayer()];
					int initialPosition = currentPlayer.getPosition();
					String diceOutput = board.rollDice();
     				String[] dice = diceOutput.split(" ");
     				int dice1 = Integer.parseInt(dice[0]);
     				int dice2 = Integer.parseInt(dice[1]);
     				
     				String[] spl = {null,null};
     				if(input.contains("d ")) {
     					spl = input.split(" ");
     					if(spl[1] != null) {
     						dice1 = Integer.parseInt(spl[1]);
     						dice2 = Integer.parseInt(spl[2]);
     					}
     				}
 					int moveState = board.movePosition(dice1, dice2, board.getCurrPlayer());


					
					if(initialPosition > board.getPlayer().getPosition() && dice1 + dice2 + initialPosition != 30) //If past the start, earn money.
						sendGenericEmbed("Congrats!","You just earned $200 for completing a full tour around the board.", "New bank balance: "+board.getPlayer().getMoney());
					
					if(board.getCurrTile().getType() == 0) { //If at parking
						sendGenericEmbed("Free parking!", "<@"+board.getPlayer().getId()+"> You landed on free parking!",null);
						board.goToNextPlayer();
						printboard();
					}
					if(board.getCurrTile().getType() == 5) { //landed on tax
						printboard();
						//board.goToNextPlayer();
					}
						
					if(dice1 + dice2 + initialPosition == 30 || dice1 + dice2 + initialPosition == 10) { //If the player is in jail
						((Tiles_Jail)(board.getCurrTile())).adjustMessage(currentPlayer);
						if(dice1 + dice2 + initialPosition == 30) {//If went to jail through top-right
							sendGenericEmbed("You are in jail!","<@"+board.getPlayer().getId()+">You have been taken from your current position to jail!",null);
						}
						else {
							sendGenericEmbed("You are in jail!","<@"+board.getPlayer().getId()+"> you are in jail! You are only visiting so no further action is required",null);
						}
						
						board.goToNextPlayer();
						printboard();
					}
					/*if((initialPosition > currentPlayer.getPosition()) && (currentPlayer.getInJail() == false)){
						//Print passing GO message
					}*/
					if(moveState == 1){
						//Buy property message
						printboard(); //If it is an un-bought property, offer to buy.
					}
					if(moveState == 2){ //If user landed on an owned tile
						if(board.getCurrTile().getOwner() != board.getCurrPlayer()) //If the current player is NOT the owner....
							printboard(); //If the user landed on an owned tile, the program will want another input to buy, rent, etc.
						else {
							sendGenericEmbed("You own this tile", "You own this tile and can stay here rent free!", null);
							board.goToNextPlayer();
						}
					}
					if(board.getCurrTile().getType() == 4/*moveState == 3*/){ //If the user landed on 'chance'
						currentPlayer.addMoney(((Tiles_Chance)(board.getCurrTile())).getRandom());
						String msg = "You landed on a " + board.getCurrTile().getName() + "\n";
						if(board.getCurrTile().getRandom() > 0)
							msg += "You won $" + board.getCurrTile().getRandom();
						else if(board.getCurrTile().getRandom() < 0)
							msg += "Unlucky! You lost $" + board.getCurrTile().getRandom();
						else
							msg += "You didn't get anything...";
						sendGenericEmbed("Chance tile!", "<@" + userID + "> " + msg, null);
						if(currentPlayer.getMoney() < 0)
							playerLose(board.getCurrPlayer());
						board.goToNextPlayer();
						printboard();
						//Print "Landed on chance" message
					}
					/*if(moveState == 4){
						//Print "Sent to jail" message
						sendGenericEmbed("Player " + (int)(board.getCurrPlayer()+1) + " went to jail!", "<@"+board.getPlayer().getId()+"> You were taken to jail!",null);
						board.goToNextPlayer();
						printboard();
					}*/
					if(moveState == 5){ //If the player couldn't roll double...
						if(board.playerList[board.getCurrPlayer()].getNumProperties() == 0 && board.getPlayer().getMoney() < 50){ //If player has no money or properties, declares bankruptcy
							//Declare bankruptcy
							playerLose(board.getCurrPlayer()); //Player looses
							sendGenericEmbed(board.getPlayer().getEmoji() + "Player " + (int)(board.getCurrPlayer()+1) + " Lost!","<@"+board.getPlayer().getId()+">You didn't have enough money to pay bail and had no properties to give.", null);
							board.goToNextPlayer(); //Goes to next player
							printboard();
						}
						else if(board.getPlayer().getMoney() < 50) { //Sells property if can't pay.
							//Sell property
							sendGenericEmbed(board.getPlayer().getEmoji() + "Player " + (int)(board.getCurrPlayer()+1) + " you sold a property to pay bail!", "You couldn't afford bail so you had to sell " + board.getPlayer().propertiesOwned.get(currentPlayer.propertiesOwned.size() - 1).getName() 
									+ " for $" + board.getPlayer().propertiesOwned.get(currentPlayer.propertiesOwned.size() - 1).getValue(), "New bank balance: $"+(currentPlayer.getMoney() - 50));
							currentPlayer.sellOwnedProperty(); //This handles the money taken from the property
							currentPlayer.payJail();
							board.goToNextPlayer();
							printboard();
						}
						else { //Has to pay bail.
							board.getPlayer().payJail();
							sendGenericEmbed(board.getPlayer().getEmoji() + "Paid Bail!",
									"Paid $50 worth of bail to get out of jail.", "New bank balance: $" + 
							board.getPlayer().getMoney());
							board.goToNextPlayer();
							printboard();
								
						}
							
					}
					if(moveState == 6) { //rolled double
						sendGenericEmbed("Congrats! You rolled double!", "<@"+board.getPlayer().getId()+"> you will be free to leave when it's your turn again.",null);
						board.goToNextPlayer();
						printboard();
					}
					if(board.tiles[0].name == "You are at the start!")
						changeStart();
					
				}
				if(input.equals("b") && board.getCurrTile().getType() == 2 && !board.getCurrTile().hasOwner() && board.getPlayer().getInJail()==true) { //To buy property, checks if property and if has owner
					
					if(board.getPlayer().getMoney() >= board.getCurrTile().getValue()) { //If the player has enough money
						board.getPlayer().buyProperty((Tiles_Property)board.getCurrTile(), board.getCurrPlayer());
						sendGenericEmbed(board.getPlayer().getEmoji() + "Bought " + board.getCurrTile().getName() + board.getCurrTile().getEmoji(),
								"Congrats! You just bought this property for " + board.getCurrTile().getValue(), "New bank balance: $"+board.getPlayer().getMoney());
					}
					if(board.getPlayer().getMoney() < board.getCurrTile().getValue()) {
						sendGenericEmbed("You don't have enough money.","<@"+board.getPlayer().getId()+"> You will be forced to skip.",null);
					}
					
					board.getPlayer().setInJail(false);
					board.goToNextPlayer();
					printboard();
					
				}
				if(input.equals("s") && board.getCurrTile().getType() == 2 && !board.getCurrTile().hasOwner()) { //To skip property, checks if property and if has owner
					board.getPlayer().setInJail(false);
					board.getPlayer().setSkipped(1);
					board.goToNextPlayer();
					printboard();
				}
				if(input.equals("r") && board.getCurrTile().getType() == 2 && board.getCurrTile().getOwner() != board.getCurrPlayer()) { //To rent, checks if property and isn't owner
					if(board.getPlayer().getMoney() >= board.getCurrTile().getRent()) { //If the player has enough money
						board.getPlayer().rentProperty((Tiles_Property)board.getCurrTile());
						board.playerList[board.getCurrTile().getOwner()].collectRent(board.getCurrTile());
						sendGenericEmbed(board.getPlayer().getEmoji() + "You just paid rent on " + board.getCurrTile().getName() + board.getCurrTile().getEmoji(),
								"Congrats! You just paid player "+((int)(board.getCurrTile().getOwner()+1))+" $" + board.getCurrTile().getRent() + " rent for " + board.getCurrTile().getName(), 
								"New bank balance: $" + board.getPlayer().getMoney());
						board.getPlayer().setInJail(false);
						board.goToNextPlayer();
						printboard();						
					}
					if(board.getPlayer().getMoney() <= board.getCurrTile().getValue()) { //If the player doesn't have enough money, goes BANKRUPT
						sendGenericEmbed(board.getPlayer().getEmoji() + "Game over Player " + (int)(board.getCurrPlayer()+1) + "!",
								"You are bankrupt! You are now out of the game!", null);
						playerLose(board.getCurrPlayer()); //This will automatically end the game if there are no players left
						board.goToNextPlayer();
						printboard();
					}
				}
				if(input.equals("p") && board.getCurrTile().getType() == 5 && board.getPlayer().getInJail()==true) { //To pay tax
					if(board.getPlayer().getMoney() >= ((Tiles_Tax)(board.getCurrTile())).getTax()) { //If the player has enough money
						board.getPlayer().payTax(((Tiles_Tax)(board.getCurrTile())));
						sendGenericEmbed(board.getPlayer().getEmoji() + "Paid Tax!",
								"Paid $" + ((Tiles_Tax)(board.getCurrTile())).getTax() + " worth of tax.", "New bank balance: $" + board.getPlayer().getMoney());
						board.getPlayer().setInJail(false);
						board.goToNextPlayer();
						printboard();
					}
					if(board.getCurrTile().getType() == 5 && board.getPlayer().getMoney() <= ((Tiles_Tax)(board.getCurrTile())).getTax()) { //If the player doesn't have enough money, goes BANKRUPT
						sendGenericEmbed(board.getPlayer().getEmoji() + "Game over Player " + (int)(board.getCurrPlayer()+1) + "!",
								"You are bankrupt! You are now out of the game!", null);
						playerLose(board.getCurrPlayer()); //This will automatically end the game if there are no players left
						board.goToNextPlayer();
						printboard();
					}
				}
				if(input.equals("p") && board.getCurrTile().getType() == 3 && board.getPlayer().getInJail()) { //To pay bail
					if(board.getPlayer().getMoney() >= ((Tiles_Jail)(board.getCurrTile())).getValue()) { //If the player has enough money
						board.getPlayer().payJail();
						sendGenericEmbed(board.getPlayer().getEmoji() + "Paid Bail!",
								"Paid $" + ((Tiles_Jail)(board.getCurrTile())).getValue() + "worth of bail to get out of jail.", "New bank balance: $" + board.getPlayer().getMoney());
						board.goToNextPlayer();
						printboard();
					}
					if(board.getPlayer().getMoney() <= ((Tiles_Jail)(board.getCurrTile())).getValue()) { //If the player doesn't have enough money, goes BANKRUPT
						sendGenericEmbed(board.getPlayer().getEmoji() + "Game over Player " + (int)(board.getCurrPlayer()+1) + "!",
								"You are bankrupt! You are now out of the game!", null);
						playerLose(board.getCurrPlayer()); //This will automatically end the game if there are no players left
						board.goToNextPlayer();
						printboard();
					}
				}
				if(input.equals("bankrupt")) {
					sendGenericEmbed(board.getPlayer().getEmoji() + "Player " + (int)(board.getCurrPlayer()+1) + " declared bankrupcy!", "<@" + board.getPlayer().getId() + "> you are out of the game!", null);
					playerLose(board.getCurrPlayer());
					board.goToNextPlayer();
					board.printBoard();
				}
				
			}
			//functions for testing
			else if(input.contains("show"))
				showPlayers();		
			else if(input.contains("teleport")) {
				String tele = input;
 				String[] teleArr = tele.split(" ");
 				int pos1 = Integer.parseInt(teleArr[1]);
 				int pos2 = Integer.parseInt(teleArr[2]);
 				int pos3 = Integer.parseInt(teleArr[3]);
 				int pos4 = Integer.parseInt(teleArr[4]);
				sendGenericEmbed("Teleport", board.printBoard(pos1,pos2,pos3,pos4),null);
			}

		}
		
	}

	public void leaveGame(String userID) {
		for(int i = 0; i < 4; i++) {
			if(board.playerList[i].getId().equals(userID)) {
				board.removePlayer(i);
				break;
			}
			sendGenericEmbed("Player " + (i+1) + "Left!","Space is now available", null);
		}
	}
	public void changeStart() {//This will change the message at start if the player has passed it
		int last = 0;
		for(int i = 0; i < 4; i++)
			if(board.playerList[i] != null)
				last = i;
		
		if(last ==  board.getCurrPlayer()) {
			board.tiles[0].name = "Collect $200 as you pass!";
			board.tiles[0].message = "You're at the start! Collect $200!";
		}
	}
	public void playerLose(int playerInd) {
		board.removePlayer(playerInd);
		if(board.getNumPlayers() == 1) {
			sendGenericEmbed(board.getPlayer().getEmoji()+"Congrats Player "+(int)(board.getCurrPlayer()+1)+"!", "<@"+board.getPlayer().getId() + "> You've won the game!:champagne:", "The game is now over. Press !play to start again");
			board = new Board();
			gameState = 3;
		}
	}
	public void printboard() {
		String message = "";
		if(board.getCurrTile().getType() == 3)
			((Tiles_Jail)board.getCurrTile()).adjustMessage(board.getPlayer());
		else if(board.getCurrTile().getType() == 5)
			((Tiles_Tax)board.getCurrTile()).adjustMessage(board.getPlayer());
		else if((board.getCurrTile().getType() == 2))
			((Tiles_Property)board.getCurrTile()).adjustMessage(board.getPlayer());
		int[] playerPositions = {40,40,40,40}; //all positions are set as non-existing
		for(int i = 0; i < 4; i++)
			if(board.playerList[i] != null)
				playerPositions[i] = board.playerList[i].getPosition();
		String strBoard =  board.printBoard(playerPositions[0],playerPositions[1],playerPositions[2],playerPositions[3]);

		if(board.getPlayer(). getSkipped() == 1){
			message = "Press 'd' to roll dice!";
			board.getPlayer().setSkipped(0);
		}
		else{
			message = (board.tiles[board.playerList[board.getCurrPlayer()].getPosition()]).getMessage(board.currPlayer);
		}
		sendGenericEmbed(board.playerList[board.getCurrPlayer()].getEmoji() + " Player " + (int)(board.getCurrPlayer() +1) + "'s Turn!\n" + ":moneybag:Money: " + board.playerList[board.getCurrPlayer()].getMoney() + "\n"+board.getCurrTile().getEmoji() + board.getCurrTile().getName(),
				"<@"+board.getPlayer().getId() + ">\n" + strBoard, message);
	}
	public void joinReceiver(String input, String userID) {
		int playerNum;
		if(input.equals("!join1") || input.equals("!join 1"))
			playerNum = 0;
		else if(input.equals("!join2")	|| input.equals("!join 2"))
			playerNum = 1;
		else if(input.equals("!join3")	|| input.equals("!join 3"))
			playerNum = 2;
		else if(input.equals("!join4")	|| input.equals("!join 4"))
			playerNum = 3;
		else {
			sendGenericEmbed("Invalid Join Input.", "You can only join spaces 1-4.", null);
			return;
		}
		int oldPlayerNum = 4; //5 is a place holder that lets the program know, the user was not already in a position before this.
		for(int i = 0; i < 4; i++) {
				if(board.playerList[i] != null) {
					if(board.playerList[i].getId().equals(userID)) //checks if the player is registered in the board
						oldPlayerNum = i;
				}
		}
		
		if(board.playerList[playerNum]==null)  { //If space is empty, player joins!
			
			if(oldPlayerNum == 4) //player isn't in board and can be added
				sendGenericEmbed("Sucessfully added player " + (playerNum+1), "Player Emoji: " + playerEmojis[playerNum] + "\nPlayer Number: " + 
						(playerNum+1), null);
			
			else {//player is registered at another space and should move
				sendGenericEmbed("You've registered for space " + (playerNum+1) + " and are no longer in space " + (oldPlayerNum+1), "Player Emoji: " + 
						playerEmojis[playerNum] + "\nPlayer Number: " + (playerNum+1), null);
				//board.removePlayer(oldPlayerNum);
				board.removePlayer(oldPlayerNum);
			}
			board.addPlayer(userID, playerNum, playerEmojis[playerNum]);			
		}
		else { //Spot is taken
			if(oldPlayerNum == playerNum) {//If the player already exists in the board as this player
				sendGenericEmbed("You've already registered at space " + (oldPlayerNum+1),
						"Player Emoji: " + playerEmojis[playerNum] + "\nPlayer Number: " + (playerNum+1), null);
				return;
			}
			String description = "Can't join game!";
			int emptySlots = 0;
			for(int i=0; i < 4; i++) {
				if(board.playerList[i] == null) {
					emptySlots++;
					if(emptySlots == 1)
						description += " Empty player slots are: ";
					description += (i+1) + " ";
				}
			}
			if(emptySlots == 0)
				description += " There are no empty slots. Players can leave with the command \"!leave\" to make space for you.";
			sendGenericEmbed("Player " + (playerNum+1) + " spot already taken", description, null);
		}
	}
	
	
	//**********************************//
	//*******Tools for testing*********//
	//********************************//
	public void showPlayers() {
		String ret = "";
		for(Player p: board.getPlayerList())
			if(p != null)
				ret += p.getEmoji() + " ID: " + p.getId() + "\nPosition: " + p.getPosition();
		sendGenericEmbed("Showing Players:",ret,null);
	}
}
