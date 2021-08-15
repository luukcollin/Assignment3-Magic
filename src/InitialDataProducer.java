import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

public class InitialDataProducer implements JMSConnection {
    static public void main(String[] args) throws IOException {



        /**
         * Plan de campagne
         *
         * 1. SorteerNodes worden opgestart
         * 2. MergeNodes worden opgestart
         * 3. Data wordt geinitaliseerd
         * 4. SorteerNodes gaan sorteren
         * 5. SorteerNodes sturen gesorteerde data naar sortedQ queue
         * 5. MergeNodes zijn gesubt op een een Topic, elke mergeNode haalt 2 gesorteerde elementen uit de lijst.
         * 6? Queue weet hoeveel consumers er zijn? Aantal nodes enzo. Voor seperatedArrays?
         * 6. Topic weet hoeveel mensen er gesubt zijn. Topic stuurt naar gesubten hoeveel er gesubt zijn.
         * 6. Topic stuurt hoeveel berichten er al dequeued zijn.
         * 6. MergeNode merget 2 gesorteerde lijsten en stuurt deze naar dezelfde topic als waar deze vandaan kwam
         *
         *
         * De mergers zijn gesubscribet op merger topic. Op merger topic komen berichten aan die gemerged mogen worden.
         * Het merger topic geeft een seintje als er 2 berichten op de topic staan?
         */

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMSConnection.URL);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(JMSConnection.QUEUE_NAME);
            MessageProducer messageProducer = session.createProducer(queue);


            //Genereer messages op de queue. Oftewel genereer aparte lijsten met ongesorteerde data.

                writeTimeToFile();
                for (int i = 0; i < AMOUNT_OF_NODES; i++) {
                    ObjectMessage message = session.createObjectMessage(new CoinListMessage(generateData(AMOUNT_OF_ELEMENTS)));
                    messageProducer.send(message);
                }

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to generate amountOfElements amount of coin objects
     * @param amountOfElements
     * @return
     */
    static List<Coin> generateData(int amountOfElements){
        return new DataGenerator(amountOfElements, new String[0]).generate();
    }

    /**
     * Method to write the startTime to a .txt file
     * @throws IOException
     */
    static void writeTimeToFile() throws IOException {
        String path = "./haribo.txt";
        FileWriter write = new FileWriter(path, true);
        PrintWriter print_line = new PrintWriter(write);
        String startTime = "\n StartTime: " + System.currentTimeMillis();
        print_line.printf(startTime);
        print_line.close();
    }
}
