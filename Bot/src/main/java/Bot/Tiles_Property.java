package Bot;

public class Tiles_Property extends Tiles  {
 int ownerNum;
 int value;
 int rent;
 boolean isMortgaged = false;
 int random = 0;
 String message;
 
 public Tiles_Property(int value, int rent, String name) {
  // TODO Auto-generated constructor stub
  ownerNum = 4; //Owner number = 4 can mean there is no owner. This removes need for boolean. Simple encapsulation.
  type = 2;
  this.value = value;
  this.rent = rent;
  this.name = name;
  emoji = ":house:";
  message = "error";
 }
 public Tiles_Property(int value, int rent, String name, String emoji) {
  // TODO Auto-generated constructor stub
  ownerNum = 4; //Owner number = 4 can mean there is no owner. This removes need for boolean. Simple encapsulation.
  type = 2;
  this.value = value;
  this.rent = rent;
  this.name = name;
  this.emoji = emoji;
  message = "error";
 }
 
 public int getRandom(){
   return random;
 }
 public boolean hasOwner() {
  return ownerNum != 4;
 }
 void freeOwner() {
  ownerNum = 4;
  freeMortgaged();
 }
 public int getOwner() {
  return ownerNum;
 }
 void setOwner(int newOwner) {
	 ownerNum = newOwner;
 }
 void setMortgaged() {
  isMortgaged = true;
 }
 void freeMortgaged() {
  isMortgaged = false;
 }
 boolean isMortgaged() {
  return isMortgaged;
 }
 public int getValue(){
   return value;
 }
 
 public int getRent(){
   return rent;
 }
 
 void setRent(int ren){
   rent = ren;
 }
 
 void setValue(int val){
   value = val;
 }
 void setName(String name) {
  this.name = name;
 }
 public String toString() { return ":classical_building:"; }
 public void adjustMessage(Player p) {
	 boolean isOwner = false;
	 for(Tiles t: p.propertiesOwned)
		 if(t.equals(this))
			 isOwner = true;
	 if(hasOwner()) {
		 if(isMortgaged())
			 message = "This property is mortgaged. You can stay here rent free!\n";
		 else if(isOwner)
			 message = "You are on your own property. Press 'd' to roll dice!\n";
		 else { //if has to pay rent OR has paid rent and can move on
			 if(p.getInJail()) {
				 int num = getOwner() + 1;
				 message = "This property is owned by player " + num + " pay rent by pressing 'r' to stay here or declare bankrupcy by typing 'bankrupt'";
			 }
			 else
				 message = "Press 'd' to roll dice!";
		 }
	 }
	 else { //Has no owner, can buy or skip
		 if(p.getInJail() == false)
			 message = "Press 'd' to roll dice!";
		 else
			 message = "You can buy this property for $" + getValue() + "\nPress 'b' to make the purchase or press 's' to skip."; 
	 }
	 
 }
	
 public String getMessage(int playerNum) {
	 return message;
 }
 
}
