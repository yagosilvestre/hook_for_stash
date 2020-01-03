package versao.hook;

import java.util.ArrayList;
import versao.util.Constantes;

public class RegistraGira {

	private static ArrayList<String> chaveJira = new ArrayList<String>();

	public static void iniciarArray() {
		chaveJira.removeAll(chaveJira);
		chaveJira.clear();
	}

	public static void setChaveJira(String jira) {
		String[] chave = jira.split(Constantes.SEPARADOR_BARRA);
		chaveJira.add(chave[2]);
	}

	public static String imprimirJiras() {
		String Lista = Constantes.ESPACO;
		for (String chave : chaveJira) {
			Lista += chave + Constantes.ESPACO;
		}
		return Lista;
	}

}
