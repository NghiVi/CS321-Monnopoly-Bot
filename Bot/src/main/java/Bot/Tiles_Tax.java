package Bot;

public class Tiles_Tax extends Tiles {
 int tax;
 int random = 0;
 int rent = 0;
 int owner = 0;
 String message;
 boolean hasOwner = false;
 public Tiles_Tax(int tax) {
   name = "Tax";
   type = 5;
   this.tax = tax;
   emoji = ":money_with_wings:";
   message = "ERROR";
 }
 public Tiles_Tax(int tax, String emoji) {
   name = "Tax";
   type = 5;
   this.emoji = emoji;
   this.tax = tax;
   message = "ERROR";
 }
 int getTax(){
   return tax;
 }
 public int getOwner(){
   return owner;
 }
 public boolean hasOwner(){
   return hasOwner;
 }
 
 public int getRent(){
   return rent;
 }
 
 public int getRandom(){
   return random;
 }
 public int getValue(){
  return 0;
}
 
 void setTax(int t){
   tax = t;
}
 public void adjustMessage(Player p) {
	 if(p.getInJail())
		 message = "Time to pay your taxes! Press 'p' to pay " + getTax() + ", or type \"bankrupt\" to declare bankrupcy!";
	 else
		 message = "You paid your taxes, press 'd' to roll your dice";
 }
@Override
public String getMessage(int playNum) {
	// TODO Auto-generated method stub
	return message;
}
}
