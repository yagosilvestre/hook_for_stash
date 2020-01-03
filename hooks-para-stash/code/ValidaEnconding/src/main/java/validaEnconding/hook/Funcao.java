package validaEnconding.hook;

import java.util.Properties;

import com.atlassian.stash.hook.HookResponse;
import com.atlassian.stash.repository.RefChange;
import validaEnconding.util.Propriedades;

/*
 * classe exibe a mensagem de erro para o usuario e retorna nome do arquivo
 */

public class Funcao {

	// constantes
	private static final String SEPARADOR = "/";
	private static final String INICIO = "PUSH.VALOR.PADAO.INICIO";
	private static final String FIM = "PUSH.VALOR.PADAO.FIM";
	private static final String GCC = "PUSH.MSG.GCC";
	private static final String JAVA = "PUSH.ERRO.JAVA";
	private static final String SQL = "PUSH.ERRO.SQL";

	Properties prop = Propriedades.getPropMensagem();

	// Metodos para imprimir erro
	//SQL
	public void msgSQL(HookResponse hookResponse, String Arquivo) {
		hookResponse.out().println(prop.getProperty(INICIO) + Arquivo + prop.getProperty(SQL));
		hookResponse.out().println(prop.getProperty(FIM));
		hookResponse.out().println(prop.getProperty(GCC));
		return;
	}
	//JAVA
	public void msgJAVA(HookResponse hookResponse, String Arquivo) {
		hookResponse.out().println(prop.getProperty(INICIO) + Arquivo + prop.getProperty(JAVA));
		hookResponse.out().println(prop.getProperty(FIM));
		hookResponse.out().println(prop.getProperty(GCC));
		return;
	}

	// Metodo para gerar o nome do arquivo
	public String gerarNome(String caminhoArquivo, RefChange refChange) {
		String nome;
		String aux[];
		aux = caminhoArquivo.split(SEPARADOR);
		nome = aux[aux.length - 1];
		nome = nome.concat(refChange.getFromHash().substring(0, 3));

		return nome;
	}

//	 public static void sleep() {
//	 try {
//	 Thread.currentThread();
//	 Thread.sleep(10000000);
//	 } catch (InterruptedException e) {
//	 // TODO Auto-generated catch block
//	 e.printStackTrace();
//	 }
//	 }

}
