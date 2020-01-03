package versao.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/*
 * le o arquivo propreties com as mensagens
 */

public class Propriedades {

	// le o arquivo de propriedade das mensagem
	public static Properties getPropMensagem() throws IOException {
		Properties props = new Properties();
		java.io.FileReader file = new java.io.FileReader(Constantes.ARQ_MENSAGEM);
		props.load(file);
		return props;
	}
	
	// Le o arquivo de propriedade da api
	public static Properties getPropApi()  throws IOException{
		Properties props = new Properties();
		java.io.FileReader file = new java.io.FileReader(Constantes.ARQ_API);
		props.load(file);
		return props;
	}
	
	// Le o arquivo de propriedade da canexao
		public static Properties getPropConexao()  {
			Properties props = new Properties();
			java.io.FileReader file = null;
			try {
				file = new java.io.FileReader(Constantes.ARQ_CONEXAO);
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
