import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.io.Serializable;
import java.util.Enumeration;

import java.util.List;

public class JMSSorter implements JMSConnection{
    public static void main(String args[])  {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSConnection.URL);
        connectionFactory.setTrustAllPackages(true);


            try {
                Connection connection = connectionFactory.createConnection();
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Queue queue = session.createQueue(JMSConnection.QUEUE_NAME);
                System.out.println("Size of queue " + queue.getQueueName() + " is " + getQueueSize(session, queue));
                MessageConsumer messageConsumer = session.createConsumer(queue);

                System.out.println("Processing messages.");

                //Consume unsorted data from the main queue and add them to unsortedCoins list
                List<Coin> unsortedCoins;
                do {

                    unsortedCoins = ((CoinListMessage) ((ObjectMessage) messageConsumer.receive()).getObject()).getCoins();
                    System.out.printf("Received!");
                    System.out.println("Length of list: " + unsortedCoins.size());
                } while (unsortedCoins.size() < 1);

                System.out.println("Stopped processing messages.");
                connection.stop();

                //Sort the two parts of unsorted coin data.
                Sort sort = new Sort();
                List<Coin> sortedCoins = sort.sort(unsortedCoins);


                connection.start();
                //Create a producer, and send the sorted piece of coin data to the sortedQueue
                MessageProducer producer = createTopicProducer(session, MERGER_TOPIC);
                ObjectMessage objectMessage = session.createObjectMessage();

                objectMessage.setObject(new CoinListMessage(sortedCoins));
                producer.send(objectMessage);

            } catch (JMSException e) {
                e.printStackTrace();
            }

    }

    private static int getQueueSize(Session session, Queue queue) {
        int count = 0;
        try {
            QueueBrowser browser = session.createBrowser(queue);
            Enumeration elems = browser.getEnumeration();

            while (elems.hasMoreElements()) {
                elems.nextElement();
                count++;
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        return count;
    }


    private static MessageProducer createTopicProducer(Session session,  String topicName) throws JMSException {
        Destination destination = session.createTopic(topicName);
        return session.createProducer(destination);
    }


}
