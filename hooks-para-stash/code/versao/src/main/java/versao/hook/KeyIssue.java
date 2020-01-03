package versao.hook;

import versao.util.Constantes;

/*
 * recebe o objeto json com nome da chave no jira e remove os caracteres
 * indesejados
 */
public class KeyIssue {

	private String key;	
	
	//gera chave 
	public String gerarKey(String object) {

		if (object.contains(Constantes.SEPARADOR_VIRGULA)) {
			String[] jiras = object.split(Constantes.SEPARADOR_VIRGULA);
			int comp = jiras.length;
			for (int x = 0; x < comp; x++) {
				String nomeIssue = jiras[x];
				if (x == 0) {
					key = (nomeIssue.substring(2, nomeIssue.length() - 1));
				} else if (x == comp - 1) {
					key = (nomeIssue.substring(1, nomeIssue.length() - 2));
				} else {
					key = (nomeIssue.substring(1, nomeIssue.length() - 1));
				}
			}
		} else {
			key = (object.substring(2, object.length() - 2));
		}
		return key;
	}
}
