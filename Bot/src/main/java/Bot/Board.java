package Bot;

import java.util.ArrayList;

public class Board {
 Tiles[] tiles; //array of tiles
 Player[] playerList; //list of players currently playing the game
 int numPlayers;
 int currPlayer;
 
 //public Tiles(int prices, String names, int index, int[6] rents){
 public Board(){
  tiles = new Tiles[40];
  playerList = new Player[4];
  currPlayer = 0;
  buildBoard();
 }
 
 public Board(Tiles[] tileList, Player[] players){
  tiles = tileList;
  playerList = players;
  numPlayers = 0;
  currPlayer = 0;
  buildBoard();
 }

 void buildBoard() {
  for(int i = 0; i < 40; i++) {
   if(i==0)
    tiles[i] = new Tiles_Start();
   else if(i==1)
    tiles[i] = new Tiles_Property(60,10,"Mediteranean Avenue");
   else if(i==2) //This is a chest
    tiles[i] = new Tiles_Chance("Community Chest!", ":coin:");
   else if(i==3)
    tiles[i] = new Tiles_Property(200,20,"Baltic Avenue");
   else if(i==4)
    tiles[i] = new Tiles_Tax(200);
   else if(i==5) //We may need to implement something for rent of railroad.
    //Rent of railroad *should* be $25 for one, 50 for two, 100 for three, 200 for all four owned
    tiles[i] = new Tiles_Property(200, 25, "Reading Railroad", ":steam_locomotive:");
   else if(i==6)
    tiles[i] = new Tiles_Property(100, 30, "Oriental Avenue");
   else if(i==7)
    tiles[i] = new Tiles_Chance();
   else if(i==8)
    tiles[i] = new Tiles_Property(100, 30, "Vermont Avenue");
   else if(i==9)
    tiles[i] = new Tiles_Property(100, 30, "Conneticut Avenue");
   else if(i==10) //No Action Jail
    tiles[i] = new Tiles_Jail();
   else if(i==11)
    tiles[i] = new Tiles_Property(140, 50, "St. Charles Place");
   else if(i==12)
    //This also doesn't have the same rent mechanics as the real game
    tiles[i] = new Tiles_Property(140, 20, "Electric Company", ":bulb:");
   else if(i==13)
    tiles[i] = new Tiles_Property(140, 50, "States Avenue", ":house_with_garden:");
   else if(i==14)
    tiles[i] = new Tiles_Property(140, 60, "Virginia Avenue", ":house_with_garden:");
   else if(i==15)
    tiles[i] = new Tiles_Property(200, 40, "Pensylvania Railroad", ":steam_locomotive:");
   else if(i==16)
    tiles[i] = new Tiles_Property(180, 70, "St. James Palace", ":house_with_garden:");
   else if(i==17) //This is a chest
    tiles[i] = new Tiles_Chance("Community Chest", ":coin:");
   else if(i==18)
    tiles[i] = new Tiles_Property(180, 70, "Tennesse Avenue", ":house_with_garden:");
   else if(i==19)
    tiles[i] = new Tiles_Property(200, 80, "New York Avenue",":house_with_garden:");
   else if(i==20)
    tiles[i] = new Tiles_Park("Free Parking",0, ":parking:");
   else if(i==21)
    tiles[i] = new Tiles_Property(220, 90, "Kentucky Avenue", ":homes:");
   else if(i==22)
    tiles[i] = new Tiles_Chance();
   else if(i==23)
    tiles[i] = new Tiles_Property(220, 90, "Indiana Avenue", ":homes:");
   else if(i==24)
    tiles[i] = new Tiles_Property(240, 100, "Illinois Avenue", ":homes:");
   else if(i==25)
    tiles[i] = new Tiles_Property(200, 60, "B & O Railroad", ":steam_locomotive:");
   else if(i==26)
    tiles[i] = new Tiles_Property(260, 110, "Atlantic Avenue", ":homes:");
   else if(i==27)
    tiles[i] = new Tiles_Property(260, 110, "Ventnor Avenue", ":homes:");
   else if(i==28)
    tiles[i] = new Tiles_Property(150, 20, "Water Works", ":shower:");
   else if(i==29)
    tiles[i] = new Tiles_Property(280, 120, "Marvin Gardens", ":homes:");
   else if(i==30) //Jail WITH action
    tiles[i] = new Tiles_GoToJail();
   else if(i==31)
    tiles[i] = new Tiles_Property(300, 130, "Pacific Avenue", ":classical_building:");
   else if(i==32)
    tiles[i] = new Tiles_Property(300, 130, "North Carolina Avenue", ":classical_building:");
   else if(i==33) //This is a chest
    tiles[i] = new Tiles_Chance("Community Chest!", ":coin:");
   else if(i==34)
    tiles[i] = new Tiles_Property(320, 150,"Pennsylvania Avenue", ":classical_building:");
   else if(i==35)
    tiles[i] = new Tiles_Property(200, 80, "Short Line", ":steam_locomotive:");
   else if(i==36)
    tiles[i] = new Tiles_Chance();
   else if(i==37)
    tiles[i] = new Tiles_Property(350, 175, "Park Place", ":classical_building:");
   else if(i==38)
    tiles[i] = new Tiles_Tax(100);
   else if(i==39)
    tiles[i] = new Tiles_Property(400, 200, "Boardwalk", ":classical_building:");
   
    
  }
 }
 Player[] getPlayerList(){ //returns the list of players
  return playerList;
 }
 
 void setPlayerList(Player[] players){ //set the list of players
  playerList = players;
 }
  
 Tiles[] getTiles(){ //return the list of tiles that are used for the board
  return tiles;
 } 
 Tiles getCurrTile() {
	 return tiles[playerList[getCurrPlayer()].getPosition()];
 }
 void setTiles(Tiles[] tileList){ //set the tiles used for the board
  tiles = tileList;
 }

 int getNumPlayers(){
  return numPlayers;
 }
 void setNumPlayers(int newNumPlayers){
  numPlayers = newNumPlayers;
 }

 int getCurrPlayer(){
	 while(playerList[currPlayer] == null) {
		 currPlayer++;
		 if(currPlayer > 3)
		 	currPlayer = 0;
	 }
	 return currPlayer;
 }
 Player getPlayer() {
	 return playerList[getCurrPlayer()];
 }

 void setCurrPlayer(int newCurrPlayer){
  currPlayer = newCurrPlayer;
 }
 
 void goToNextPlayer() {
	    currPlayer++;
	    if(currPlayer > 3){
	        currPlayer = 0;
	     } 
	    while(playerList[currPlayer] == null){ // add = since if player is at 3, it break the loop since we pre-increment by 1.
	        currPlayer++;
	        if(currPlayer > 3){
	            currPlayer = 0;
	        }
	    }

 }
 
 void addPlayer(String playerId, String emoji){
  Player newPlayer = new Player(playerId, emoji);
  playerList[numPlayers] = newPlayer;
  numPlayers++;
 }
 void addPlayer(String playerId, int position, String emoji){
	  Player newPlayer = new Player(playerId, emoji);
	  playerList[position] = newPlayer;
	  numPlayers++;
	 }
 
 void removePlayer(int playerInd) {
	 Player[] tempList = new Player[4];
	 for(int i = 0; i < 4; i++)
		 if(i != playerInd)
			 tempList[i] = playerList[i];
	 
	 playerList = tempList;
	 numPlayers--;
 }
 void removePlayer(String playerID) {
	 Player[] tempList = new Player[4];
	 for(int i = 0; i < 4; i++)
		 if(playerList[i].getId() != playerID)
			 tempList[i] = playerList[i];
	 
	 playerList = tempList;
	 numPlayers--;
 }

 String printBoard() {
  String strBoard = "";
  for(int i = 20; i < 31; i++)
   strBoard += tiles[i].getEmoji();
  strBoard += "\n";
  int L = 19;
  for(int R = 31; R < 40; R++) {
   strBoard += tiles[L].getEmoji();
   strBoard += printSpaces();
   strBoard += tiles[R].getEmoji();
   L--;
   strBoard += "\n";
  }
  for(int j = 10; j >= 0; j--)
   strBoard += tiles[j].getEmoji();
  
  return strBoard;
 }
 
 private int calcSpace(int[] ind, int pos) {
	 ArrayList<Integer> arr = new ArrayList<Integer>();
	 ArrayList<Integer> diff = new ArrayList<Integer>();
	 for(int i = 0; i < 4; i ++) { //TOP
		 if(pos == 0 && ind[i]<30 && ind[i] > 20) { //TOP
				 arr.add(ind[i]);
		 }
		 else if(pos == 1 && ind[i]<10 && ind[i] > 0) { //BOTTOM
				 arr.add(ind[i]);
		 }
		 else if(pos == 2 && ind[i]>=30 && ind[i] <= 39) { //RIGHT
				 arr.add(ind[i]);
		 }
		 else if(pos == 3 && ind[i]<=20 && ind[i] >= 10) { //LEFT
				 arr.add(ind[i]);
		 }
	 }
	 for(int i = 0; i < arr.size(); i++) {
		 if(diff.contains(arr.get(i)) == false) {
			 diff.add(arr.get(i));
		 }
	 }
	 if(arr.size() == 0)
		 return 0;
	 else
		 return arr.size() + 1 - diff.size();
	 
 }
String printBoard(int a, int b, int c, int d) { //Integer = 40 if player doesn't exist
	String strBoard = "";
	int[] arr = {a,b,c,d};
	int top = calcSpace(arr, 0);
	int bottom = calcSpace(arr, 1);
	int left = calcSpace(arr, 3);
	int right = calcSpace(arr, 2);
	for(int i = 0; i < top; i++) {//print top tiles
		for(int l = 0; l < left; l++) //Adjust left
			strBoard += "⬛";
		for(int j = 20; j < 31; j++) { //Top tiles
			if(j!= 20 && j!= 30 && j == a) {
				strBoard += ":pickup_truck:";
				a = 40; //set position as 'printed'
			}
			else if(j!= 20 && j!= 30 && j == b) {
				strBoard += ":race_car:";
				b = 40;
			}
			else if(j!= 20 && j!= 30 && j == c) {
				strBoard += ":bus:";
				c = 40;
			}
			else if(j!= 20 && j!= 30 && j == d) {
				strBoard += ":motorcycle:";
				d = 40;
			}
			else
				strBoard += "⬛";
		}
		strBoard += "\n";
	}
	for(int l = 0; l < left; l++) {//Adjust top-left
			if(a == 20) {
				strBoard += ":pickup_truck:";
				a = 40;
			}
			else if(b == 20) {
				strBoard += ":race_car:";
				b = 40;
			}
			else if(c == 20) {
				strBoard += ":bus:";
				c = 40;
			}
			else if(d == 20) {
				strBoard += ":motorcycle:";
				d = 40;
			}
			else
				strBoard += "⬛";
	}
	for(int i = 20; i < 31; i++) {//Top side
		strBoard += tiles[i].getEmoji();
	}
	if(a == 30) {
		strBoard += ":pickup_truck:";
		a = 40;
	}
	if(b == 30) {
		strBoard += ":race_car:";
		b = 40;
	}
	if(c == 30) {
		strBoard += ":bus:";
		c = 40;
	}
	if(d == 30) {
		strBoard += ":motorcycle:";
		d = 40;
	}
	strBoard += "\n";
	int L = 19;
	for(int R = 31; R < 40; R++) { //Left and Right
		for(int i = 0; i < left; i++) {
			if(L == a) {
				strBoard += ":pickup_truck:";
				a = 40; //set position as 'printed'
			}
			else if(L == b) {
				strBoard += ":race_car:";
				b = 40;
			}
			else if(L == c) {
				strBoard += ":bus:";
				c = 40;
			}
			else if(L == d) {
				strBoard += ":motorcycle:";
				d = 40;
			}
			else
				strBoard += "⬛";
		}
		strBoard += tiles[L].getEmoji();
		strBoard += printSpaces();
		strBoard += tiles[R].getEmoji();
		for(int i = 0; i < right; i++) {
			if(R == a) {
				strBoard += ":pickup_truck:";
				a = 40; //set position as 'printed'
			}
			if(R == b) {
				strBoard += ":race_car:";
				b = 40;
			}
			if(R == c) {
				strBoard += ":bus:";
				c = 40;
			}
			if(R == d) {
				strBoard += ":motorcycle:";
				d = 40;
			}
		}
		L--;
		strBoard += "\n";
	}
	for(int l = 0; l < left; l++) {//Adjust bottom-left
		if(a == 10) {
			strBoard += ":pickup_truck:";
			a = 40;
		}
		else if(b == 10) {
			strBoard += ":race_car:";
			b = 40;
		}
		else if(c == 10) {
			strBoard += ":bus:";
			c = 40;
		}
		else if(d == 10) {
			strBoard += ":motorcycle:";
			d = 40;
		}
		else
			strBoard += "⬛";
	}
	for(int j = 10; j >= 0; j--) //Bottom
		strBoard += tiles[j].getEmoji();
	if(a==0) {
		strBoard += ":pickup_truck:";
		a = 40; //set position as 'printed'
	}
	if(b==0) {
		strBoard += ":race_car:";
		b = 40;
	}
	if(c==0) {
		strBoard += ":bus:";
		c = 40;
	}
	if(d == 0) {
		strBoard += ":motorcycle:";
		d = 40;
	}
	for(int i = 0; i < bottom; i++) {
		strBoard += "\n";
		for(int l = 0; l < left; l++)
			strBoard += "⬛";
		for(int j = 10; j >= 0; j--) {
			if(j!= 0 && j!= 10 && j == a) {
				strBoard += ":pickup_truck:";
				a = 40; //set position as 'printed'
			}
			else if(j!= 0 && j!= 10 && j == b) {
				strBoard += ":race_car:";
				b = 40;
			}
			else if(j!= 0 && j!= 10 && j == c) {
				strBoard += ":bus:";
				c = 40;
			}
			else if(j!= 0 && j!= 10 && j == d) {
				strBoard += ":motorcycle:";
				d = 40;
			}
			else
				strBoard += "⬛";
		}
	}
	return strBoard;
}
 String rollDice(){
  int dice1;
     int dice2;
  dice1 = (int)(Math.random()*6+1);
  dice2 = (int)(Math.random()*6+1);
  //movePosition(dice1, dice2, playerID);
  String rollOutput =  dice1 + " " + dice2;
  return rollOutput;
 }

 //return 0 if there is nothing the user can input after moving
 //return 1 if the user can buy the property landed on
 //return 2 if the user landed on an owned tile
 //return 3 if the user is out of money //Shouldn't this be 'chance' ?
 //return 4 if the user was sent to jail
 //return 5 if the user landed on chance //This is now functioning as in-jail, failed to roll double
 //return 6 if player paid bail
 int movePosition(int dice1, int dice2, int playerID){
   int sum = dice1 + dice2;
   int index = playerID;
   Player player = playerList[index];
   if(player.getInJail() == true && getCurrTile().getType() == 3){ //if player is in jail, must pay or roll double
     if(dice1 == dice2){ //if roll double, no longer in jail
       player.setInJail(false);
       return 6;
     }
     else { //if there is no double, the player has to pay
       //player.payJail();
	   if(player.getMoney() < 50){ //IF the player doesn't have bail money, they are bankrupt
		  return 5;
	   }
       return 5;
     }
   }
   player.position = player.getPosition() + sum;
   if(player.position > 39){
     player.position -= 39;
     player.addMoney(200);
   }

   if(tiles[getPlayer().getPosition()].getType() == 5) { //If landed on a tax tile, set inJail to true to not let player move until pay bail
	   player.setInJail(true);
	   return 0;
   }
   if(player.position ==2 || player.position == 7 || player.position == 22 || player.position ==33 || player.position == 37){
     return 3;
   }
   if(player.position == 30){
     player.goToJail();
     return 4;
   }
   //handle chance spot
   
   //if player lands on property we want to give them an option to purchase
   //But how do we do this without user input for option to purchase
   if(getCurrTile().getType() == 2 && tiles[player.position].hasOwner() == false){
	 player.setInJail(true);
	 return 1;
   }
   //RETURN 1 this means that the user has the option to buy this property
   if(getCurrTile().getType() == 2 && tiles[player.position].hasOwner() == false){
	  player.setInJail(true);
     return 2;
   }
   if(tiles[player.position].getType() == 5) //If landed on a tax tile, set inJail to true to not let player move until pay bail
	   player.setInJail(true);
   return 0;

 }
 
 String printSpaces() {
  String space = "";
  for(int i = 0; i < 9; i++) {
   space += "⬛";
  }
  return space;
 }
 
}