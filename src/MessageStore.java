import java.util.*;
class MessageStore {

	public String msgContents;
	public long timeSent;

	public MessageStore(String msg) {
		this.msgContents = msg;
		this.timeSent = System.currentTimeMillis();
	}
}
