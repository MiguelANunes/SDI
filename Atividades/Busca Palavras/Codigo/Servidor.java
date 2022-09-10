import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;


class Servidor {

	public static HashMap<String, Integer> findWords(String sentence, List<String> targets){
		Integer total = 0;
		HashMap<String, Integer> ocorrencias = new HashMap<String, Integer>();
	
		for(String alvo: targets){
			total = (sentence.length() - sentence.replace(alvo, "").length()) / alvo.length();
			ocorrencias.put(alvo, total);
		}
		return ocorrencias;
	}

	public static void main(String argv[]) throws Exception {
		String clientSentence = "1";
		List<String> palavrasAlvo = new ArrayList<String>();
		// abrindo uma socket para comunicar com o cliente
		ServerSocket welcomeSocket = new ServerSocket(50001);

		Scanner sc = new Scanner(System.in);
		String s1 = "";
		while(sc.hasNextLine()){
			s1 = sc.nextLine();
			// só começo a ler a partir da tag que marca as palavras do servidor
			if(s1.equals("##Servidor##;")){
				while(true){
					s1 = sc.nextLine();
					// e paro quando acho as palavras do cliente
					if(s1.equals("##Clientes##;")) break;
					// removo os ";" das palavras que o servidor quer
					palavrasAlvo.add(s1.replace(";", ""));
				}
			}else{
				continue;
			}
		}
		sc.close();

		// colocando o array de palavras alvo em ordem alfabética
		Collections.sort(palavrasAlvo);

		// configurando como vai receber do cliente
		Socket connectionSocket = welcomeSocket.accept();
		BufferedReader inFromClient
				= new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient
				= new DataOutputStream(connectionSocket.getOutputStream());

		// salvando em um hash map as palavras que quero encontrar e a qtd que encotrei
		HashMap<String, Integer> palavrasEncontradas = new HashMap<String, Integer>();
		for(String alvo: palavrasAlvo){
			// inicializando
			palavrasEncontradas.put(alvo, 0);
		}
		String returnString = "";
		while (true){
			// recebendo do cliente
			clientSentence = inFromClient.readLine();
			// se recebi o sinal para terminar, retorno pro cliente o resultado
			if(clientSentence.equals("acabou")){

				// juntando o resultado do processamento numa só string
				for(String alvo: palavrasAlvo){
					// "$" marca fim de linha, não posso colocar \n pois não tenho como
					// facilmente ler várias linhas no cliente
					returnString = returnString + alvo + "=" + palavrasEncontradas.get(alvo).toString() + "-";
				}
				// removendo o último $ pois ele não é necessário
				returnString = returnString.substring(0, returnString.length()-1);
				outToClient.writeBytes(returnString+"\n");
				break;
			}
			// se não, processo
			Integer novo = 0, velho = 0;
			for(String alvo: palavrasAlvo){
				velho = palavrasEncontradas.get(alvo);
				novo  = findWords(clientSentence, palavrasAlvo).get(alvo);
				palavrasEncontradas.put(alvo, novo + velho);
			}
		}

		welcomeSocket.close();
		System.exit(0);
	}
}
