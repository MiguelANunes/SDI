import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.*;

public class Recv {

  private final static int port = 5672;
  private static String newMessage = null;


  public static void main(String[] argv) throws Exception {
		HashMap<String, Integer> elements = new HashMap<>();
		fillDict(elements);
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("sdi");
    factory.setPassword("sdi");
    factory.setVirtualHost("/");
    factory.setHost("ens1");
    factory.setPort(port);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
				String message = new String(body, "UTF-8");
				newMessage = decrypt(message, elements);
				
				System.out.println("##RELATORIO##;");
    		System.out.println(newMessage);
    		System.out.println("###;");
				System.exit(0);
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);

  } // main

	public static void fillDict(Map<String, Integer> my_dict) {
		my_dict.put("H", 1);
		my_dict.put("He", 2);
		my_dict.put("Li", 3);
		my_dict.put("Be", 4);
		my_dict.put("B", 5);
		my_dict.put("C", 6);
		my_dict.put("N", 7);
		my_dict.put("O", 8);
		my_dict.put("F", 9);
		my_dict.put("Ne", 10);
		my_dict.put("Na", 11);
		my_dict.put("Mg", 12);
		my_dict.put("Al", 13);
		my_dict.put("Si", 14);
		my_dict.put("P", 15);
		my_dict.put("S", 16);
		my_dict.put("Cl", 17);
		my_dict.put("Ar", 18);
		my_dict.put("K", 19);
		my_dict.put("Ca", 20);
		my_dict.put("Sc", 21);
		my_dict.put("Ti", 22);
		my_dict.put("V", 23);
		my_dict.put("Cr", 24);
		my_dict.put("Mn", 25);
		my_dict.put("Fe", 26);
		my_dict.put("Co", 27);
		my_dict.put("Ni", 28);
		my_dict.put("Cu", 29);
		my_dict.put("Zc", 30);
		my_dict.put("Ga", 31);
		my_dict.put("Ge", 32);
		my_dict.put("As", 33);
		my_dict.put("Se", 34);
		my_dict.put("Br", 35);
		my_dict.put("Kr", 36);
		my_dict.put("Rb", 37);
		my_dict.put("Sr", 38);
		my_dict.put("Y", 39);
		my_dict.put("Zr", 40);
		my_dict.put("Nb", 41);
		my_dict.put("Mo", 42);
		my_dict.put("Tc", 43);
		my_dict.put("Ru", 44);
		my_dict.put("Rh", 45);
		my_dict.put("Pd", 46);
		my_dict.put("Ag", 47);
		my_dict.put("Cd", 48);
		my_dict.put("In", 49);
		my_dict.put("Sn", 50);
		my_dict.put("Sb", 51);
		my_dict.put("Te", 52);
		my_dict.put("I", 53);
		my_dict.put("Xe", 54);
		my_dict.put("Cs", 55);
		my_dict.put("Ba", 56);
		my_dict.put("La", 57);
		my_dict.put("Ce", 58);
		my_dict.put("Pr", 59);
		my_dict.put("Nd", 60);
		my_dict.put("Pm", 61);
		my_dict.put("Sm", 62);
		my_dict.put("Eu", 63);
		my_dict.put("Gd", 64);
		my_dict.put("Tb", 65);
		my_dict.put("Dy", 66);
		my_dict.put("Ho", 67);
		my_dict.put("Er", 68);
		my_dict.put("Tm", 69);
		my_dict.put("Yb", 70);
		my_dict.put("Lu", 71);
		my_dict.put("Hf", 72);
		my_dict.put("Ta", 73);
		my_dict.put("W", 74);
		my_dict.put("Re", 75);
		my_dict.put("Os", 76);
		my_dict.put("Ir", 77);
		my_dict.put("Pt", 78);
		my_dict.put("Au", 79);
		my_dict.put("Hg", 80);
		my_dict.put("Tl", 81);
		my_dict.put("Pb", 82);
		my_dict.put("Bi", 83);
		my_dict.put("Po", 84);
		my_dict.put("At", 85);
		my_dict.put("Rn", 86);
		my_dict.put("Fr", 87);
		my_dict.put("Ra", 88);
		my_dict.put("Ac", 89);
		my_dict.put("Th", 90);
		my_dict.put("Pa", 91);
		my_dict.put("U", 92);
		my_dict.put("Np", 93);
		my_dict.put("Pu", 94);
		my_dict.put("Am", 95);
		my_dict.put("Cm", 96);
		my_dict.put("Bk", 97);
		my_dict.put("Cf", 98);
		my_dict.put("Es", 99);
		my_dict.put("Fm", 100);
		my_dict.put("Md", 101);
		my_dict.put("No", 102);
		my_dict.put("Lr", 103);
		my_dict.put("Rf", 104);
		my_dict.put("Db", 105);
		my_dict.put("Sg", 106);
		my_dict.put("Bh", 107);
		my_dict.put("Hs", 108);
		my_dict.put("Mt", 109);
		my_dict.put("Ds", 110);
		my_dict.put("Rg", 111);
		my_dict.put("Cn", 112);
		my_dict.put("Nh", 113);
		my_dict.put("Fl", 114);
		my_dict.put("Mc", 115);
		my_dict.put("Lv", 116);
		my_dict.put("Ts", 117);
		my_dict.put("Og", 118);

	}

	public static String decrypt(String word, HashMap<String, Integer> elements) {
		int i;
		int offset = 31;
		int asciiInt = -1;
		char asciiChar = '~';
		String decryptWord = null;

		for (i = 0; i < word.length() - 1; i++) {
			// Search for uppercase
			if (Character.isUpperCase(word.charAt(i))) {
				// Element with 2 symbols
				if (Character.isLowerCase(word.charAt(i + 1))) {
					String element = String.valueOf(word.charAt(i));
					i++;
					String tmp = String.valueOf(word.charAt(i));
					element = element.concat(tmp);
					//System.out.print(element + "|");
					asciiInt = elements.get(element) + offset;
					//System.out.print(asciiInt);
					asciiChar = (char) asciiInt;
					decryptWord = decryptWord + asciiChar;
					//System.out.print(asciiChar + "\n");
				}
				// Element with 1 Symbol
				else {
					String element = String.valueOf(word.charAt(i));
					//System.out.print(element + "|");
					asciiInt = elements.get(element) + offset;
					//System.out.print(asciiInt);
					asciiChar = (char) asciiInt;
					decryptWord = decryptWord + asciiChar;
					//System.out.print(asciiChar + "\n");
				}
			}
			// If value is digit, we have digit.digit+element
			else if (Character.isDigit(word.charAt(i))) {
				int multiplier = Character.getNumericValue(word.charAt(i));
				i++; // ponto
				i++; // Próximo numero
				int arred = Character.getNumericValue(word.charAt(i));
				i++;
				// Element with 2 symbols
				if (Character.isLowerCase(word.charAt(i + 1))) {
					String element = String.valueOf(word.charAt(i));
					i++;
					String tmp = String.valueOf(word.charAt(i));
					element = element.concat(tmp);
					//System.out.print(element + "|");
					asciiInt = (multiplier * elements.get(element)) + offset + arred;
					//System.out.print(asciiInt);
					asciiChar = (char) asciiInt;
					decryptWord = decryptWord + asciiChar;
					//System.out.print(asciiChar + "\n");
				}
				// Element with 1 symbol
				else {
					String element = String.valueOf(word.charAt(i));
					//System.out.print(element + "|");
					asciiInt = (multiplier * elements.get(element)) + offset + arred;
					//System.out.print(asciiInt);
					asciiChar = (char) asciiInt;
					decryptWord = decryptWord + asciiChar;
					//System.out.print(asciiChar + "\n");
				}
			}
		}

		return (decryptWord.substring(4));
	}

}