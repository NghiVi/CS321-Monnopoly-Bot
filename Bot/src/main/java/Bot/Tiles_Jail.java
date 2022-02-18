package Bot;

public class Tiles_Jail extends Tiles {
  int rent = 0;
  int random = 0;
  boolean hasOwner = false;
  int owner;
  
  public Tiles_Jail() {
    // TODO Auto-generated constructor stub
    name = "Jail! No action";
    type = 3;
    emoji = ":passport_control:";
    this.message = "If you are seeing this message, you did something wrong.";
  }
  public Tiles_Jail(String emoji) {
    // TODO Auto-generated constructor stub
    name = "Jail! No action";
    type = 3;
    this.emoji = emoji;
    this.message = "If you are seeing this message, you did something wrong.";
  }
  
  void setName(String name) {
    this.name = name;
  }
  //I am not sure if this is how the jail 'with action' will work. The idea is player will pay 200 if it is 'with action'.
  //Correct if needed.
  int returnFee() {
    return 50;
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
    return 50;
  }
  public String toString() { return ":passport_control:"; }
  
  public void adjustMessage(Player p) {
	  if(p.getInJail()) {
		  message = "Press 'd' to try and roll double. If not, you will need to pay bail for $" + getValue();
		  if(p.getMoney() < getValue())
			  message += "\nYou don't have enough funds to pay bail. So you either roll doubles or you're out!";
	  }
	  else
		  message = "You're in jail. Press 'd' to roll your dice and get out.";
  }
@Override
public String getMessage(int playNum) {
	// TODO Auto-generated method stub
	return message;
}

}
