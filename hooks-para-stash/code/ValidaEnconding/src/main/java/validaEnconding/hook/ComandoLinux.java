package validaEnconding.hook;

import java.io.IOException;
import java.util.Scanner;

import validaEnconding.util.Constantes;
/*
 * Classe executa os comandos do sistema operacional
 */
public class ComandoLinux {

	//constantes 
	private final static String COMANDO_FILE = "\n\nfile --mime-encoding /tmp/";
	private final static String COMANDO_RM = "rm -rf ";
	private final static String SEPARADOR =" ";
	private final static String DELIMITADOR = "$$";
	
	private Runtime r = Runtime.getRuntime();
	private Scanner scanner;
	private String[] enconding = null;
	
	public String[] retornarEnconding(String nomeArquivo) throws IOException {
		
		// varivel recebe o retorno dos comandos executados
		Process p = r.exec( COMANDO_FILE + nomeArquivo);
		scanner = new Scanner(p.getInputStream());
		String resultado = scanner.useDelimiter(DELIMITADOR).next();
		// recebe o valor do enconding
		enconding = resultado.split(SEPARADOR);
		System.out.println("resultado e "+ enconding[1]);
		return enconding;

	}
	// dela o arquivo crido
	public void deletarArquivo(String nomeArquivo) throws IOException {
		r.exec(COMANDO_RM+Constantes.CAMINHO_TEMP+nomeArquivo);
		
		return;
		
	}
	
}
