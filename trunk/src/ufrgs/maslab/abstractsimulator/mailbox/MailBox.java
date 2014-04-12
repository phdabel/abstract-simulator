package ufrgs.maslab.abstractsimulator.mailbox;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.mailbox.message.Message;

public class MailBox {
	
	/**
	 * <ul>
	 * <li>IDs of the messages in the inbox of the agent</li>
	 * </ul>
	 */
	private ArrayList<Message> InBox = new ArrayList<Message>();

	/**
	 * <ul>
	 * <li>IDs of the messages in the sent box of the agent</li>
	 * </ul>
	 */
	private ArrayList<Message> Sent = new ArrayList<Message>();
	
	/**
	 * <ul>
	 * <li>IDs of the messages in the trash of the agent</li>
	 * </ul>
	 */
	private ArrayList<Message> Trash = new ArrayList<Message>();

	/**
	 * <ul>
	 * <li>IDs of the outgoing messages</li>
	 * </ul>
	 */
	private ArrayList<Message> Outgoing = new ArrayList<Message>();
	/**
	 * returns the inbox list
	 * 
	 * @return
	 */
	public ArrayList<Message> getInBox() {
		return InBox;
	}
	

	/**
	 * returns the sent list
	 * 
	 * @return
	 */
	public ArrayList<Message> getSent() {
		return Sent;
	}

	/**
	 * returns the trash
	 * 
	 * @return
	 */
	public ArrayList<Message> getTrash() {
		return Trash;
	}

	/**
	 * remove all items from trash
	 */
	public void clearTrash() {
		this.getTrash().clear();
	}

	/**
	 * returns outgoing messages
	 * @return
	 */
	public ArrayList<Message> getOutgoing() {
		return Outgoing;
	}

	
	
}
