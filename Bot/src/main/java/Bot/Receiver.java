package Bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Receiver extends ListenerAdapter {
	
	
	private Game_Control_Center controller = new Game_Control_Center();  
	//private int startCommand = 0;
	private EmbedBuilder embed = new EmbedBuilder();
	//private String resultMessage;     //result of an function in Controller, default is String "success"
	private String content;
	private MessageChannel channel;   //this variable is used to send message to the channel of the commands just entered
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		//get message and channel of the message
		content = e.getMessage().getContentRaw().toLowerCase();
		channel = e.getChannel();
		String userID = e.getAuthor().getId();
		
		
		if(content.equals("!play") || ( (controller.getState() == 1 || controller.getState() == 2))) { // state = 1 means it is setting up the game and should take input 
			controller.run(embed, channel, content, userID);                                          // state = 2 means the game is running and should take input
		}
		if(content.equals("!commands")) {//I don't know why but this doesn't work
			e.getChannel().sendMessage("Type \"!play\" to start the game!");
		}
		if(content.equals("hi")) {
			e.getChannel().sendMessage("what's up mate!").queue();
		}
		
		if(content.equals("bored") || content.equals("this game is the best") )
			testEmbed();
		if(content.contains("!delete")) {
			e.getChannel().purgeMessages(e.getChannel().getHistory().retrievePast(50).complete());
		}
	}
		/* ACTIONS WHEN GAME IS RUNNING */
	
	private void testEmbed() {
		embed = new EmbedBuilder();
		embed.setThumbnail("https://img.memecdn.com/computer-science-students-will-get-this_o_216650.jpg");
		embed.setTitle("You found the Easter Egg !");
		embed.setColor(0x527A00);
		channel.sendMessage(embed.build()).queue();
		embed.setThumbnail(null);
	}
	
	/* Used for constructing and sending generic embed message. 
	 * If no title or footer is present, call the function with them as null: 
	 * sendGenericEmbed(null, description, null); */
	public void sendGenericEmbed(String title, String description, String footer) {
		if ( (title != null) && !(title.equals(""))) //if not null string or empty String
			embed.setTitle(title);
		if ( (description.length() < 2048) && (description != null) ) // max length for an embed description.
			embed.setDescription(description);
		else embed.setDescription("Invalid description String. Cannot exceed 2048 or null");
		
		embed.setFooter(footer); // doesnt need to handle exceoption since setFooter accepts null
		channel.sendMessage(embed.build()).queue();
	}
}









