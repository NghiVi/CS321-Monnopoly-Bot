package Bot;

import java.util.ArrayList;
public class Player {
 

 int position; //position on board
 int money; //money
 int skippedTurn;
 ArrayList<Tiles> propertiesOwned; //properties owned by the player
 boolean inJail;
 String playerID;
 String playerEmoji;
 
 public Player(){
  position = 40;
  money = 1500;
  skippedTurn = 0;
  propertiesOwned = new ArrayList<Tiles>();
  inJail = false;
 }
 
 public Player(String ID, String emojis){
  position = 0;
  money = 1500;
  skippedTurn = 0;
  propertiesOwned = new ArrayList<Tiles>();
  inJail = false;
  playerID = ID;
  playerEmoji = emojis;
 }
 
 void setId(long id){
   playerID = Long.toString(id);
 }

 String getId(){
   return playerID;
 }

 String getEmoji() {
   return playerEmoji;
 }
 
 void setInJail(boolean jail){
   inJail = jail;
 }
 
 void goToJail(){
   inJail = true;
   position = 10;
 }
 
 boolean getInJail(){
   return inJail;
 }
 
 void setPosition(int pos) { //Set position on board
  position = pos;
 }
 
 int getPosition() { //Get position on board
  return position;
 }
 
void setSkipped(int skipStatus){
  skippedTurn = skipStatus;
}

int getSkipped(){
  return skippedTurn;
}

 void addMoney(int diff) { //Adds or subtracts given amount from player's money
  money += diff;
 }

 void setMoney(int cash) {
  money = cash;
 }
 int getMoney() {
  return money;
 }
 
 void setOwnedProperties(ArrayList<Tiles> properties){ //set the properties owned of the player
   propertiesOwned = properties;
 }
 
 ArrayList<Tiles> getOwnedProperties(){ //get the properties owned by the player
   return propertiesOwned;
 }

void sellOwnedProperty(){
	addMoney(propertiesOwned.get(propertiesOwned.size() - 1).getValue());
    ((Tiles_Property)propertiesOwned.get(propertiesOwned.size() - 1)).freeOwner();
    propertiesOwned.remove(propertiesOwned.size() - 1);
 }

 int getNumProperties(){
   return propertiesOwned.size();
 }
 
 void buyProperty(Tiles_Property tile, int id) {
  tile.setOwner(id);
  propertiesOwned.add(tile);
  addMoney(-tile.getValue());
 }
 
void rentProperty(Tiles tile) {
	addMoney(-tile.getRent());
}
 
void collectRent(Tiles tile){
	addMoney(tile.getRent());
}
 
void payTax(Tiles_Tax tile) {
	addMoney(-tile.getTax());
}
 
void payJail() {	
	addMoney(-50);
	inJail = false;
}
	 //****Functions for tiles****//
	 /*
 
 
 
	
	 void payJail() {
	  addMoney(-5-);
	 }*/
}