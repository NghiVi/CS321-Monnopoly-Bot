package Bot;

/* Parent class for all tiles of the board */
public abstract class Tiles {
	/* contain the coordinate of the tile on the board */
	// private int[] coordinate;
	int type; // Type = ( 0=Nothing Specific/Parking ) (1=Start) (2=Property) (3=Jail)
				// (4=Chance/Chest) (5=Tax)
	String name; // Will hold name that the bot will print. i.e Old Kent Road, Community Chest,
					// etc
	String emoji = "";
	String message;
	public Tiles() {		

	}

	public Tiles(String name, int type, String emoji) {
		this.name = name;
		this.type = type;
		this.emoji = emoji;
	}

	String getName() {
		return name;
	}

	String getEmoji() {
		return emoji;
	}

	int getType() {
		return type;
	}

	public String toString() {
		return emoji;
	}

	public abstract String getMessage(int playNum);
	public abstract boolean hasOwner();
	public abstract int getRent();
	public abstract int getRandom();
	public abstract int getOwner();
	public abstract int getValue();

}
