import javax.jms.MessageListener;

public interface JMSConnection {

     String URL = "failover:(tcp://localhost:61616)";
     String QUEUE_NAME = "sortedQueue";
     String MERGER_TOPIC = "MergerTopic";
     int AMOUNT_OF_ELEMENTS = 20_000;
     int AMOUNT_OF_NODES = 8;

}
