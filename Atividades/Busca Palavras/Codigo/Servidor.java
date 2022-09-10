import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

class Servidor {

	public static String findWords(String sentence, List<String> targets){
		// removendo a assinatura do cliente da mensagem
		String result = "0";
		Integer ultimaOcorrencia = 0;
		Integer total = 0;
		HashMap<String, Integer> ocorrencias = new HashMap<String, Integer>();
		
		for(String alvo: targets){
			total = 0;
			while(ultimaOcorrencia != -1) {
			
				ultimaOcorrencia = sentence.indexOf(alvo, ultimaOcorrencia);
			
				if (ultimaOcorrencia != -1) {
					total++;
					ultimaOcorrencia += alvo.length();
				}
			}
			ocorrencias.put(alvo, total);
		}
		return result;
	}

	public static void main(String argv[]) throws Exception {
		String clientSentence = "1";
		Integer cont=0;
		List<String> palavrasAlvo = new ArrayList<String>();
		// abrindo uma socket para comunicar com o cliente
		ServerSocket welcomeSocket = new ServerSocket(4242);

		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()){
			// quando tenho que começar a ler o texto
			if(sc.nextLine() == "##Servidor##;"){
				// quando retornou essa "##Clientes##;", quer dizer que já passou por ela
				// logo não preciso me preocupar com ela estar em texto
				while(sc.nextLine() != "##Clientes##;"){
					// lê o texto até achar o marcador que acabou
					palavrasAlvo.add(sc.nextLine());
				}
			}
			else{
				continue;
			}
		}

		System.out.println("Servidor leu:");
		for(String s: palavrasAlvo){
			System.out.println("Palavras que o servidor quer:" + s);
		}

		while (true) {
			// configurando como vai receber do cliente
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient
					= new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient
					= new DataOutputStream(connectionSocket.getOutputStream());

			// recebendo do cliente
			clientSentence = inFromClient.readLine();
			// printando o que o servidor leu
			System.out.println("Servidor recebeu("+cont+"): "+clientSentence);
			clientSentence
					= findWords(clientSentence, palavrasAlvo) + '\n';
			// enviando resposta para o cliente
			outToClient.writeBytes(clientSentence);

			cont++;
			if (cont==1) {
				// Servidor fecha depois de processar uma linha
				// não sei se isso é do exemplo ou se é pra ser assim no final mesmo
				System.out.println("Servidor finalizado com sucesso!");
				System.exit(0);
			}
		}
	}
}
