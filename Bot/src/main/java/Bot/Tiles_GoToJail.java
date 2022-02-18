package Bot;

public class Tiles_GoToJail extends Tiles {
	// TODO Auto-generated constructor stub

	String name = "Go To Jail!";
	int type = 2;
	boolean hasOwner = false;
	int rent = 0;
	int random = 0;
	int owner = 0;

	public Tiles_GoToJail(String emoji) {
		// TODO Auto-generated constructor stub
		name = "Go To Jail!";
		type = 2;
		this.emoji = ":passport_control:";
	}

	public Tiles_GoToJail() {
		// TODO Auto-generated constructor stub
		name = "Go To Jail!";
		type = 2;
		this.emoji = ":passport_control:";
	}

	void setName(String name) {
		this.name = name;
	}

	// I am not sure if this is how the jail 'with action' will work. The idea is
	// player will pay 200 if it is 'with action'.
	// Correct if needed.
	int returnFee() {
		return 50;
	}

	public int getOwner() {
		return owner;
	}

	public boolean hasOwner() {
		return hasOwner;
	}

	public int getRent() {
		return rent;
	}

	public int getRandom() {
		return random;
	}

	public int getValue() {
		return 0;
	}

	public String toString() {
		return ":passport_control:";
	}

	@Override
	public String getMessage(int playNum) {
		// TODO Auto-generated method stub
		return "Go to jail!, you will be taken to jail!";
	}

}
