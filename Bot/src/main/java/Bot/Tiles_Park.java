package Bot;

public class Tiles_Park extends Tiles {

	public Tiles_Park() {
	}
	public Tiles_Park(String name, int type, String emoji) {
		this.name = name;
		this.type = type;
		this.emoji = emoji;
	}
	@Override
	public boolean hasOwner() {
		return false;
	}

	@Override
	public int getRent() {
		return 0;
	}

	public int getOwner() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getMessage(int playNum) {
		return "Free Parking! Press 'd' to roll to dice.";
	}
	@Override
	public int getRandom(){
		return 0;
	}
	public int getValue(){
		return 0;
	  }
}
