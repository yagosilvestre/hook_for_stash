package versao.hook;

import java.io.IOException;

import versao.util.LogErro;

/*
 * classe reponsavel por deletar os arquivos criados
 */
public class DeletaArquivo {
	//Constantes
	private static final String COMANDO = "rm -rf ";
	
	public void removerArquivo(String caminho) {
		Runtime r = Runtime.getRuntime();
		try {
			r.exec(COMANDO + caminho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogErro.registarErro("FALHA AO EXCLUIR O ARQUIVO");
		}
	}

}
