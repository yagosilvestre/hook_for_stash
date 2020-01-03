package versao.hook;

import java.io.IOException;
import java.util.Properties;

import versao.util.Constantes;
import versao.util.LogErro;
import versao.util.Propriedades;

/*
 * //imprime as msg de erro para o usuario 
 */

public class PrintErro {

	private String msg = null;
	private Properties prop = null;

	// constantes
	private static final String MSG_TITULO = "MERGE.TITULO";
	private static final String MSG_ERRO_1 = "MERGE.ERRO.VALOR1";
	private static final String MSG_ERRO_2 = "MERGE.ERRO.VALOR2";
	private static final String MSG_ERRO_3 = "MERGE.ERRO.VALOR3";
	private static final String MSG_ERRO_DEFAULT = "MERGE.ERRO.DEFAULT";

	public String msgTitulo() {
		getProperties();
		return prop.getProperty(MSG_TITULO);
	}

	public String msgTexto(int valida) {
		getProperties();
		switch (valida) {

		case 1:
			msg = prop.getProperty(MSG_ERRO_1).concat(Constantes.ESPACO+RegistraGira.imprimirJiras());
			// msg = "O fixVersion preenchido nos jiras relacionados não corresponde ao
			// branch de destino. \nMais detalhes:
			// http://xwiki/xwiki/bin/view/GCC/Bloqueios+relativos+a+Push+e+Merge";
			break;
		case 2:
			msg = prop.getProperty(MSG_ERRO_2).concat(Constantes.ESPACO+RegistraGira.imprimirJiras());
			// msg = "O fixVersion referende ao develop pode estar duplicado ou preenchido
			// incorretamente, por favor verificar as versões no menu Releases no jira.
			// \nMais detalhes:
			// http://xwiki/xwiki/bin/view/GCC/Bloqueios+relativos+a+Push+e+Merge";
			break;
		case 3:
			msg = prop.getProperty(MSG_ERRO_3).concat(Constantes.ESPACO+RegistraGira.imprimirJiras()) ;
			// msg = "FixVersion não preenchido nos jiras relacionados. \nMais detalhes:
			// http://xwiki/xwiki/bin/view/GCC/Bloqueios+relativos+a+Push+e+Merge";
			break;
		default:
			msg = prop.getProperty(MSG_ERRO_DEFAULT).concat(Constantes.ESPACO+RegistraGira.imprimirJiras() );
			// msg = "Validar os fixVersion preenchido nos jiras relacionados. \nMais
			// detalhes:
			// http://xwiki/xwiki/bin/view/GCC/Bloqueios+relativos+a+Push+e+Merge";
			break;
		}
		return msg;
	}

	//retorna informações do arquivo properties
	public void getProperties() {
		try {
			prop = Propriedades.getPropMensagem();
		} catch (IOException e) {
			LogErro.registarErro("erro ao ler o arquivo mensagem.proprietes");
		}
	}
}
