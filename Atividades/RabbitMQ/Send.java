import com.rabbitmq.client.*;
import java.io.*;
import java.util.*;

// Lê mensagens do stdin e manda para o servidor
// Carrega numa fila de "ida"
// Lê texto traduzido de uma fila de volta

public class Send {

	private final static String FILA_ToServer = "inputGRUPO3AAG";
	private final static String FILA_ToClient = "outputGRUPO3AAG";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("sdi");
		factory.setPassword("sdi");
		factory.setVirtualHost("/");
		factory.setHost("ens1");
		factory.setPort(5672);

		Connection connection = factory.newConnection();

		Channel channelToServer  = connection.createChannel();
		Channel channelToClient = connection.createChannel();

		channelToServer.queueDeclare(FILA_ToServer, false, false, false, null);
		channelToClient.queueDeclare(FILA_ToClient, false, false, false, null);

		List<String> texto = new ArrayList<String>();

		Scanner sc = new Scanner(System.in);
		String s1 = "";
		s1 = sc.nextLine();
		texto.add(s1);
//		while(sc.hasNextLine()){
//			s1 = sc.nextLine();
//			// só começo a ler quando acho as palavras do cliente
//			System.out.print("Texto lido: \t");
//			System.out.println(s1);
//			if(s1.equals("##Cliente##;")){
//				while(true){
//					s1 = sc.nextLine();
//					// e paro quando acho o delimitador
//					if(s1.equals("###;")) break;
//					texto.add(s1);
//				}
//			}else{
//				continue;
//			}
//		}
		sc.close();

		for(String message: texto){
			channelToServer.basicPublish("", FILA_ToServer, null, message.getBytes("UTF-8"));

			Consumer consumer = new DefaultConsumer(channelToClient){
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {

					String newmessage = new String(body, "UTF-8");
					System.out.println("##RELATORIO##;");
					System.out.println(newmessage);
					System.out.println("###;");
					}
			
			};
			channelToClient.basicConsume(FILA_ToClient, true, consumer);
		}
		channelToServer.close();
		channelToClient.close();
		connection.close();
	}
}

