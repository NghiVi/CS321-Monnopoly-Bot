package Bot;

public class Tiles_Chance extends Tiles { 
 int min = -50;
 int max = 50;
 int random;
 int rent = 0;
 int owner = 0;
 boolean hasOwner = false;
 int chance = 0;
 public Tiles_Chance() {
  name = "Chance Tile!";
  type = 4;
  emoji = ":question:";
  random = (int)Math.floor(Math.random()*(max-min+1)+min);
  hasOwner = false;
  rent = 0;
 }
 public Tiles_Chance(String name, String emoji) {
  this.name = name;
  type = 4;
  this.emoji = emoji;
  random = (int)Math.floor(Math.random()*(max-min+1)+min);
  hasOwner = false;
  rent = 0;
 }
 public int getOwner(){
   return owner;
 }
 public int getRandom(){ //Set maximum reward and deduction to 100
   chance = ((int)((Math.random())*20)*10)-100;
   return chance;
 }
 public boolean hasOwner(){
   return hasOwner;
 }
 public int getRent(){
   return rent;
 }
 public String getChance() {
	 return "";
 }

 public int getValue(){
  return 0;
}

@Override
public String getMessage(int playNum) {
	message =  "Press 'd' to roll dice!";
	return message;
}
 
}
