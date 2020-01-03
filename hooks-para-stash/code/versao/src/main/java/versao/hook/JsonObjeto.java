package versao.hook;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import versao.util.Constantes;

/*
 * Classe responsavel por recuperar as informações do json
 */
public class JsonObjeto {

	private KeyIssue key = new KeyIssue();
	private Vector<String> issuePura = new Vector<String>();
	private Vector<String> issue = new Vector<String>();
	private Vector<String> fixVersion = new Vector<String>();
	private DeletaArquivo del = new DeletaArquivo();
	private ParametroDevelop dev = new ParametroDevelop();
	
	//ATRIBUTOS DO JSON - Constantes
	private static final String CHAVE_JIRA = "jira-key";
	private static final String VALOR = "values";
	private static final String DESTINO = "toRef";
	private static final String ID = "id";
	private static final String CAMPOS = "fields";
	private static final String ATRIBUTO = "attributes";
	
	// metodo retorna as jiras relacionados
	public Vector<String> retornarIssue(String arqCommit) throws IOException, ParseException {
		issuePura.clear();
		issue.clear();

		java.io.FileReader reader = new java.io.FileReader(arqCommit);
		org.json.simple.parser.JSONParser parser = new JSONParser();
		org.json.simple.JSONObject objectTOTAL = (JSONObject) parser.parse(reader);
		JSONArray array = (JSONArray) objectTOTAL.get(VALOR);
		Iterator<?> i = array.iterator();

		while (i.hasNext()) {
			JSONObject commit = (JSONObject) i.next();
			JSONObject arrayAtributo = (JSONObject) commit.get(ATRIBUTO);
			if (!issue.contains(arrayAtributo.get(CHAVE_JIRA).toString())) {
				issue.add(arrayAtributo.get(CHAVE_JIRA).toString());
			}
		}

		for (String object : issue) {
			issuePura.add(key.gerarKey(object));
		}
		del.removerArquivo(arqCommit);
		return issuePura;
	}

	// metodo retorna nome do branch de destino do pullResquest
	public String retornarBranch(String arqMerge) throws IOException, ParseException {
		String nomeTotalBranch = null;

		java.io.FileReader reader = new java.io.FileReader(arqMerge);
		org.json.simple.parser.JSONParser parser = new JSONParser();
		org.json.simple.JSONObject objectTOTAL = (JSONObject) parser.parse(reader);
		JSONObject array = (JSONObject) objectTOTAL.get(DESTINO);

		nomeTotalBranch = (String) array.get(ID);
		del.removerArquivo(arqMerge);
		return nomeTotalBranch;
	}

	// metodo retorna fixVersion preenchido nos jiras
	public Vector<String> retornarFix(Vector<String> arqIssue, String branch) throws IOException, ParseException {
		fixVersion.clear();
		String[] compBranch = branch.split(Constantes.SEPARADOR_BARRA);
		//limpa arrayList que guarda os jiras
		RegistraGira.iniciarArray();
		
		for (String issue : arqIssue) {
			//guarda chave dos jiras
			RegistraGira.setChaveJira(issue);
			//
			java.io.FileReader reader = new java.io.FileReader(issue);
			org.json.simple.parser.JSONParser parser = new JSONParser();
			org.json.simple.JSONObject objectTOTAL = (JSONObject) parser.parse(reader);
			JSONObject aux = (JSONObject) objectTOTAL.get(CAMPOS);
			if (Constantes.VAZIO.equals(aux.get(Constantes.FIX).toString())) {
				fixVersion.add(Constantes.VAZIO);
			} else if (compBranch[2].equals(Constantes.DEVELOP)) {
				fixVersion.add(retornarFixDevelop(aux));	
			} else {
				JSONArray array = (JSONArray) aux.get(Constantes.FIX);
				Iterator<?> i = array.iterator();
				while (i.hasNext()) {
					JSONObject fix = (JSONObject) i.next();
					fixVersion.add(fix.get(Constantes.NOME).toString());
				}
			}

		}

		for (String issue : arqIssue) {
			del.removerArquivo(issue);
		}
		return fixVersion;
	}
	
	
	//retorna o fix do develop
	public String retornarFixDevelop(JSONObject aux) throws MalformedURLException, IOException, ParseException{
		if (dev.retornarDevelop(aux)) {
			return Constantes.DEVELOP;
		}
		return Constantes.NOME;
	}
	

}
