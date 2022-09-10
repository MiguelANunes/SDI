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
		while(sc.hasNextLine()){
			// quando tenho que começar a ler o texto
			if(sc.nextLine() == "##Clientes##;"){
				// quando retornou essa "##Clientes##;", quer dizer que já passou por ela
				// logo não preciso me preocupar com ela estar em texto
				while(sc.nextLine() != "###;"){
					// lê o texto até achar o marcador que acabou
					texto.add(sc.nextLine());
				}
			}
			else{
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
