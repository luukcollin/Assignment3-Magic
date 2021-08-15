
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;

import javax.jms.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JMSMerger implements JMSConnection{
    private static Merger merger = new Merger();
    public static void main(String[] args) {
        //Consumes data from sorted queue to merge sorted coin data
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSConnection.URL);
        connectionFactory.setTrustAllPackages(true);

        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer topicConsumer = session.createConsumer(session.createTopic(MERGER_TOPIC));
            ConsumerMessageListener messageListener = new ConsumerMessageListener("cons");
            topicConsumer.setMessageListener(messageListener);
            boolean received = false;
            while(!received) {
                received = messageListener.allMessagesAreReceived();
                if (messageListener.getMessages().size() > 0) {
                    System.out.println("I received messages!");
                }
            }

            //Maak een topic aan voor de mergers
            MessageProducer topicProducer =  createTopicProducer(session,MERGER_TOPIC);
            System.out.println("Has elements should be true here");
            boolean hasElements = messageListener.getMessages().size() > 1;
            System.out.println("hasElements: " + hasElements);


                if (messageListener.getMessages().size() > 1) {
                    System.out.println("has Elements");
                    List<Coin> merged = new ArrayList<>();

                    boolean stop = false;
                    do {
                        CoinListMessage toBeMerged1 = (CoinListMessage)messageListener.getMessageAndDelete().getObject();
                        CoinListMessage toBeMerged2 = (CoinListMessage)messageListener.getMessageAndDelete().getObject();

                        if(toBeMerged1 != null && toBeMerged2 != null) {

                            merged = merger.mergeInOrder(toBeMerged1.getCoins(), toBeMerged2.getCoins());
                            System.out.println("Length of CoinListMessage: " + merged.size());
                            ObjectMessage objectMessage = session.createObjectMessage();
                            objectMessage.setObject(new CoinListMessage(merged));
                            topicProducer.send(objectMessage);
                        }
                        stop = (merged.size() == AMOUNT_OF_ELEMENTS * AMOUNT_OF_NODES);
                        if(stop){
                            System.out.println("Results of the Merge Sort");
                            System.out.println(merged);
                        }
                    } while (!stop);
                    writeTimeToFile();
                    System.out.println("Out of do while");
                }

        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateTotalTime() throws FileNotFoundException  {
        File file = new File("./haribo.txt");
        Scanner sc = new Scanner(file);
        long startTime = 0;
        while(sc.hasNextLine()){
            startTime = Long.parseLong(sc.nextLine());
        }
        return (System.currentTimeMillis() - startTime);
    }

    /**
     * Method to write the startTime to a .txt file
     * @throws IOException
     */
    static void writeTimeToFile() throws IOException {
        String path = "./haribo.txt";
        FileWriter write = new FileWriter(path, true);
        PrintWriter print_line = new PrintWriter(write);
        String endTime = "\n EndTime: " + System.currentTimeMillis();
        print_line.printf(endTime);
        print_line.close();
    }

    private static MessageProducer createTopicProducer(Session session,  String topicName) throws JMSException {
        Destination destination = session.createTopic(topicName);
        return session.createProducer(destination);
    }


}
