package versao.hook;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import versao.util.Constantes;
/*
 * percorre as releases cadastradas no projeto verificando qual delas e o develop verificando se ha duplicidade
 */

public class ParametroDevelop {

//	private int checkDevolop = 0;
	private String projeto = null;
	private ArquivoDados call = new ArquivoDados();
	private GeraString link = new GeraString();
	private String arqProjeto = null;
	private java.io.FileReader reader = null;
	private String fixVersion = null;
	private DeletaArquivo del = new DeletaArquivo();

	// ATRIBUTOS DO JSON
	public static final String CHAVE = "key";
	public static final String PROJETO = "project";
	public static final String DESCRICAO = "description";
	

	public boolean retornarDevelop(JSONObject aux) throws MalformedURLException, IOException, ParseException {

		JSONObject array = (JSONObject) aux.get(PROJETO);
		projeto = array.get(CHAVE).toString();

		JSONArray arrayFix = (JSONArray) aux.get(Constantes.FIX);
		Iterator<?> i = arrayFix.iterator();
		while (i.hasNext()) {
			JSONObject fix = (JSONObject) i.next();
			fixVersion = fix.get(Constantes.NOME).toString();
		}
		arqProjeto = call.projetoJson(link.gerarStringPorjetoJira(projeto));

		reader = new java.io.FileReader(arqProjeto);
		org.json.simple.parser.JSONParser parser = new JSONParser();

		
		org.json.simple.JSONArray arrayProjeto = (JSONArray) parser.parse(reader);

//////////////////////////////////////////////////////////////////////////////////////////////////		
//	                PARTE QUE VALIDA SE EXISTE MAIS DE UM DEVELOP							/////
//																							/////
//		checkDevolop = 0;																	/////
//		i = arrayProjeto.iterator();														/////
//		// verifica se tem mais de um develop preenchido									////
//		while (i.hasNext()) {																///
//			JSONObject versao = (JSONObject) i.next();										////
//			if (versao.toJSONString().contains(DESCRICAO)) {								////
//				if (versao.get(DESCRICAO).toString().equalsIgnoreCase(DEVELOP)) {			////
//					checkDevolop = checkDevolop + 1;										///
//				}																			////
//			}																				////
//		}																					/////
//		if (checkDevolop >= 2) {															////
//			del.removerArquivo(arqProjeto);													////
//			return false;																	////
//		}																					////
////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// valida se a versão jira está marcada com develop
		i = arrayProjeto.iterator();
		while (i.hasNext()) {
			JSONObject versao = (JSONObject) i.next();
			if (fixVersion.equalsIgnoreCase(versao.get(Constantes.NOME).toString())) {
				if (versao.toJSONString().contains(DESCRICAO)) {
					if (versao.get(DESCRICAO).toString().equalsIgnoreCase(Constantes.DEVELOP)) {
						del.removerArquivo(arqProjeto);
						return true;
					}
				}
			}
		}

		del.removerArquivo(arqProjeto);
		return false;
	}
}
