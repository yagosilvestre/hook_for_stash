package versao.hook;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import versao.util.Constantes;
import versao.util.LogErro;
import versao.util.Propriedades;

/*
 * classe reponsavel por conectar na API e retornar o arquivo json 
 */
public class ArquivoDados {

	private static FileWriter file;
	private Vector<String> caminhoArquivos = new Vector<String>();
	private static Properties prop = Propriedades.getPropConexao();
	
	// constantes
	private static final String PASTA_TEMP = "/tmp/";
	private static final String ARQ_PROJETO = "projeto";
	private static final String ARQ_COMITS = "commits";
	private static final String ARQ_PULL = "pull";
	private static final int TEMPO = Integer.parseInt(prop.getProperty("TEMPO"));
	private static final String AUTORIZACAO = prop.getProperty("ATUTORIZACAO");
	private static final String TIPO_CONEXAO = prop.getProperty("BASICA");
	
	// abre a conexão com API
	public HttpURLConnection conectar(URL link) throws IOException {	
		HttpURLConnection conn2 = (HttpURLConnection) link.openConnection();
		conn2.setRequestMethod("GET");
		conn2.setRequestProperty("Content-length", "0");
		conn2.setUseCaches(false);
		conn2.setRequestProperty(AUTORIZACAO , TIPO_CONEXAO);
		conn2.setAllowUserInteraction(false);
		conn2.setConnectTimeout(TEMPO);
		conn2.setReadTimeout(TEMPO);
		conn2.connect();
		return conn2;
	}

	// criar um valor aleatorio para evitar que seja criado dois arquivos com mesmo
	// nome ao mesmo tempo
	private static int gerarId() {
		int id = 0;
		Random radom = new Random();
		id = radom.nextInt(10);
		return id;
	}

	// gera o arquivo json to pullRequest
	public String pullJson(URL link) throws IOException {

		String caminho = PASTA_TEMP + ARQ_PULL + gerarId();
		String grava;

		HttpURLConnection conn2 = conectar(link);

		BufferedReader buffer = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

		file = new FileWriter(caminho);

		while ((grava = buffer.readLine()) != null) {
			file.write(grava.toString());
		}
		file.flush();
		file.close();
		conn2.disconnect();

		return caminho;
	}

	// gera o arquivo json do commit
	public String commitJson(URL link) throws IOException {

		String aux = link.toString();
		String grava;

		aux = aux + Constantes.SEPARADOR_BARRA + ARQ_COMITS;

		URL linkCommit = new URL(aux);

		HttpURLConnection conn2 = conectar(linkCommit);

		BufferedReader buffer = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
		String caminho = PASTA_TEMP + ARQ_COMITS + gerarId();
		file = new FileWriter(caminho);

		while ((grava = buffer.readLine()) != null) {
			file.write(grava.toString());
		}
		file.flush();
		file.close();
		conn2.disconnect();

		return caminho;
	}

	// gera o arquivo json do jira
	public Vector<String> jiraJson(URL link, Vector<String> nJira) throws IOException {
		caminhoArquivos.clear();
		String aux = link.toString();
		String grava;

		for (String object : nJira) {

			String urlJira = aux + object;
			URL linkJira = new URL(urlJira);
			String caminho = PASTA_TEMP + object;

			HttpURLConnection conn2 = conectar(linkJira);

			BufferedReader buffer = null;
			try {
				buffer = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
				if (buffer != null) {
					file = new FileWriter(caminho);
					while ((grava = buffer.readLine()) != null) {
						file.write(grava.toString());
					}
					file.flush();
					file.close();
					caminhoArquivos.add(caminho);
					conn2.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogErro.registarErro("jira nao existe " + object);
			}
		}
		return caminhoArquivos;
	}

	// gera arquivo json do projeto
	public String projetoJson(URL link) throws IOException {

		String caminho = PASTA_TEMP + ARQ_PROJETO + gerarId();
		String grava;

		HttpURLConnection conn2 = conectar(link);

		BufferedReader buffer = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

		file = new FileWriter(caminho);

		while ((grava = buffer.readLine()) != null) {
			file.write(grava.toString());
		}
		file.flush();
		file.close();
		conn2.disconnect();

		return caminho;

	}

}
