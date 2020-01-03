package validaEnconding.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;



/*
 * le o arquivo propreties com as mensagens
 */

public class Propriedades {

	// HML
	private final static String ARQ_MENSAGEM = "/opt/atlassian/stash/3.11.2/conf/mensagem.properties";
	
	// PROD
	// private final static String ARQ_MENSAGEM = ; 
	

	// Le o arquivo de propriedade da canexao
		public static Properties getPropMensagem()  {
			Properties props = new Properties();
			java.io.FileReader file = null;
			try {
				file = new java.io.FileReader(ARQ_MENSAGEM);
			} catch (FileNotFoundException e) {
				LogErro.registarErro("Erro ao Abrir o arquivo");
			}
			try {
				props.load(file);
			} catch (IOException e) {
				LogErro.registarErro("Erro ao Abrir o arquivo");
			}
			return props;
		}
	
}
