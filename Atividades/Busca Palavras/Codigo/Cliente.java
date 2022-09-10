import java.io.*;
import java.net.*;
import java.util.*;

class Cliente {

	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;
		BufferedReader br = null;
		FileReader fr = null;
		List<String> texto = new ArrayList<String>();

		Scanner sc = new Scanner(System.in);
		String s1 = "";
		while(sc.hasNextLine()){
			s1 = sc.nextLine();
			// só começo a ler quando acho as palavras do cliente
			if(s1.equals("##Clientes##;")){
				while(true){
					s1 = sc.nextLine();
					// e paro quando acho o delimitador
					if(s1.equals("###;")) break;
					texto.add(s1);
				}
			}else{
				continue;
			}
		}

		for(String s: texto){
			System.out.println("Palavras lidas pelo cliente:" + s);
		}

		for(String s: texto){

			BufferedReader inFromUser
						= new BufferedReader(
							new InputStreamReader(System.in));

			// abrindo uma socket p/ o servidor
			Socket clientSocket = new Socket("localhost", 4242);

			// configurando para mandar p/ o servidor
			DataOutputStream outToServer
					= new DataOutputStream(
							clientSocket.getOutputStream());
			
			// configurando para receber do servidor
			BufferedReader inFromServer
					= new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));

			// mandando o texto p/ o servidor
			outToServer.writeBytes(s + '\n');

			// lendo o que o servidor respondeu
			modifiedSentence = inFromServer.readLine();

			System.out.println("Servidor Respondeu:" + modifiedSentence);
			clientSocket.close();
		}
		System.out.println("##RELATORIO##;");
		// printando total de palavras
		System.out.println("###;");
		System.exit(0);
	}
}
