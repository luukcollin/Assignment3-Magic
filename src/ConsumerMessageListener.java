import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * ConsumerMessageListener Class
 * Listens for messages. Contains onMessage function that fires when a message has arrived on topic.
 */

//TODO Look at boolean receivedAllMessages, is it act ually being used?
public class ConsumerMessageListener implements MessageListener, JMSConnection {
    private final String naam;
    private List<Message> messages;
    private boolean hasMessages;
    private boolean receivedAllMessages;

    public ConsumerMessageListener(String naam){
        this.naam = naam;
        messages = new ArrayList<>();
        hasMessages = false;
        receivedAllMessages = false;
    }
    public List<Message> getMessages(){
        return messages;
    }

    public ObjectMessage getMessageAndDelete(){
        Message message;
        if(messages.size() > 0){
            message = messages.get(0);
            messages.remove(message);
            return (ObjectMessage)message;
        }
        return null;
    }
    public boolean hasMessages(){
        return hasMessages;
    }

    private boolean receivedAllMessages(){
        return messages.size() == AMOUNT_OF_NODES;
    }

    public boolean allMessagesAreReceived(){
        System.out.println("Hello");
        return receivedAllMessages;
    }

    @Override
    public void onMessage(Message message) {
        messages.add(message);
        hasMessages = messages.size() > 1;
        receivedAllMessages = receivedAllMessages();
        System.out.println("I currently received " + messages.size() + " messages");
    }


}
