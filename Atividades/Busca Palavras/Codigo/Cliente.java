import java.io.*;
import java.net.*;
import java.util.*;

class Cliente {

	public static void main(String argv[]) throws Exception {
		String serverResponse;
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
		sc.close();

		// for(String s: texto){
		// 	System.out.println("Li "+ s);
		// }

		// abrindo uma socket p/ o servidor
		Socket clientSocket = new Socket("localhost", 4242);
		// configurando para mandar p/ o servidor
		DataOutputStream outToServer
				= new DataOutputStream(
						clientSocket.getOutputStream());

		for(String s: texto){
			// mandando o texto p/ o servidor
			outToServer.writeBytes(s + '\n');							
		}

		outToServer.writeBytes("acabou\n");

		// configurando para receber do servidor
		BufferedReader inFromServer
				= new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
		// lendo o que o servidor respondeu
		serverResponse = inFromServer.readLine();
		clientSocket.close();

		serverResponse.replace("$", "\n");

		System.out.println("##RELATORIO##;");
		System.out.println(serverResponse);
		System.out.println("###;");
		System.exit(0);
	}
}
