import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.*;

public class Recv {

	private static String newMessage = null;
	private final static String FILA_ToServer = "inputGRUPO3AAG";
	private final static String FILA_ToClient = "outputGRUPO3AAG";


	public static void main(String[] argv) throws Exception {

		HashMap<String, Integer> MapaElementos = new HashMap<>();
		populateMap(MapaElementos);

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

		Consumer consumer = new DefaultConsumer(channelToServer){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {

				String message = new String(body, "UTF-8");
				String newMessage = decrypt(message, MapaElementos);
				// System.out.println(newMessage);
				channelToClient.basicPublish("", FILA_ToClient, null, newMessage.getBytes("UTF-8"));
				}
		};
		channelToServer.basicConsume(FILA_ToServer, true, consumer);

	} 

	public static void populateMap(Map<String, Integer> Map) {
		Map.put("H",    1);
		Map.put("He",   2);
		Map.put("Li",   3);
		Map.put("Be",   4);
		Map.put("B",    5);
		Map.put("C",    6);
		Map.put("N",    7);
		Map.put("O",    8);
		Map.put("F",    9);
		Map.put("Ne",  10);
		Map.put("Na",  11);
		Map.put("Mg",  12);
		Map.put("Al",  13);
		Map.put("Si",  14);
		Map.put("P",   15);
		Map.put("S",   16);
		Map.put("Cl",  17);
		Map.put("Ar",  18);
		Map.put("K",   19);
		Map.put("Ca",  20);
		Map.put("Sc",  21);
		Map.put("Ti",  22);
		Map.put("V",   23);
		Map.put("Cr",  24);
		Map.put("Mn",  25);
		Map.put("Fe",  26);
		Map.put("Co",  27);
		Map.put("Ni",  28);
		Map.put("Cu",  29);
		Map.put("Zc",  30);
		Map.put("Ga",  31);
		Map.put("Ge",  32);
		Map.put("As",  33);
		Map.put("Se",  34);
		Map.put("Br",  35);
		Map.put("Kr",  36);
		Map.put("Rb",  37);
		Map.put("Sr",  38);
		Map.put("Y",   39);
		Map.put("Zr",  40);
		Map.put("Nb",  41);
		Map.put("Mo",  42);
		Map.put("Tc",  43);
		Map.put("Ru",  44);
		Map.put("Rh",  45);
		Map.put("Pd",  46);
		Map.put("Ag",  47);
		Map.put("Cd",  48);
		Map.put("In",  49);
		Map.put("Sn",  50);
		Map.put("Sb",  51);
		Map.put("Te",  52);
		Map.put("I",   53);
		Map.put("Xe",  54);
		Map.put("Cs",  55);
		Map.put("Ba",  56);
		Map.put("La",  57);
		Map.put("Ce",  58);
		Map.put("Pr",  59);
		Map.put("Nd",  60);
		Map.put("Pm",  61);
		Map.put("Sm",  62);
		Map.put("Eu",  63);
		Map.put("Gd",  64);
		Map.put("Tb",  65);
		Map.put("Dy",  66);
		Map.put("Ho",  67);
		Map.put("Er",  68);
		Map.put("Tm",  69);
		Map.put("Yb",  70);
		Map.put("Lu",  71);
		Map.put("Hf",  72);
		Map.put("Ta",  73);
		Map.put("W",   74);
		Map.put("Re",  75);
		Map.put("Os",  76);
		Map.put("Ir",  77);
		Map.put("Pt",  78);
		Map.put("Au",  79);
		Map.put("Hg",  80);
		Map.put("Tl",  81);
		Map.put("Pb",  82);
		Map.put("Bi",  83);
		Map.put("Po",  84);
		Map.put("At",  85);
		Map.put("Rn",  86);
		Map.put("Fr",  87);
		Map.put("Ra",  88);
		Map.put("Ac",  89);
		Map.put("Th",  90);
		Map.put("Pa",  91);
		Map.put("U",   92);
		Map.put("Np",  93);
		Map.put("Pu",  94);
		Map.put("Am",  95);
		Map.put("Cm",  96);
		Map.put("Bk",  97);
		Map.put("Cf",  98);
		Map.put("Es",  99);
		Map.put("Fm", 100);
		Map.put("Md", 101);
		Map.put("No", 102);
		Map.put("Lr", 103);
		Map.put("Rf", 104);
		Map.put("Db", 105);
		Map.put("Sg", 106);
		Map.put("Bh", 107);
		Map.put("Hs", 108);
		Map.put("Mt", 109);
		Map.put("Ds", 110);
		Map.put("Rg", 111);
		Map.put("Cn", 112);
		Map.put("Nh", 113);
		Map.put("Fl", 114);
		Map.put("Mc", 115);
		Map.put("Lv", 116);
		Map.put("Ts", 117);
		Map.put("Og", 118);
	}

	public static String decrypt(String traduzir, HashMap<String, Integer> MapaElementos) {
		int i;
		int round = 0;
		int multiplier = 0;
		String temp = "";
		String elemento = "";
		StringBuilder traduzida = new StringBuilder();

		for (i = 0; i < traduzir.length(); i++){

			// Se achamos uma letra maíuscula na string -- é um elemento
			if(Character.isUpperCase(traduzir.charAt(i))){

				// Se tem uma segunda letra minúscula 
				// Miguel: Poderia fazer isso diferente, visto que a segunda letra sempre será minuscula?

				// separar obter as duas letras
				if(Character.isLowerCase(traduzir.charAt(i + 1))){
					elemento = String.valueOf(traduzir.charAt(i));
					i += 1;
					temp = String.valueOf(traduzir.charAt(i));
					elemento += temp;
					
					traduzida.append((char) (MapaElementos.get(elemento) + 31));

				}else{ // Se não tem mais uma letra
					elemento = String.valueOf(traduzir.charAt(i));
					
					traduzida.append((char) (MapaElementos.get(elemento) + 31));
				}
			}else if(Character.isDigit(traduzir.charAt(i))){ // Se achamos um número

				multiplier = Character.getNumericValue(traduzir.charAt(i));   // multiplicador do caractere
				i += 2;
				round = Character.getNumericValue(traduzir.charAt(i)); // arredondamento
				// +2 caractere imediatamente depois do número é um ponto

				// Se depois do número tem um elemento com dois caracteres
				i += 1;
				if (Character.isLowerCase(traduzir.charAt(i + 1))) {
					elemento = String.valueOf(traduzir.charAt(i));
					i += 1;
					temp = String.valueOf(traduzir.charAt(i));
					elemento += temp;
					int temp1 = multiplier * MapaElementos.get(elemento);

					traduzida.append((char) (temp1 + 31 + round));
				}
				// Se tem apenas um caractere
				else {
					elemento = String.valueOf(traduzir.charAt(i));
					int temp1 = multiplier * MapaElementos.get(elemento);

					traduzida.append((char) (temp1 + 31 + round));
				}
			}
		}

		return traduzida.toString();
	}

}

