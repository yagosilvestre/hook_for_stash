package versao.hook;

import java.io.IOException;
import java.util.Vector;
import org.json.simple.parser.ParseException;
import com.atlassian.stash.hook.repository.RepositoryMergeRequestCheckContext;

import versao.enuns.Valor;


/*
 * Classe responsavel por gerar e retornar se o valor de bloqueio do hook
 */
public class Regra {

	//Classes e variaveis 
	private Vector<String> nJira;
	private ArquivoDados call = new ArquivoDados();
	private GeraString links = new GeraString();
	private JsonObjeto info = new JsonObjeto();
	private Vector<String> caminhoIssue = new Vector<String>();
	private String caminhoMerge, arqCommit;
	private AplicaRegra valida = new AplicaRegra();
	private int aprovacao = Valor.CORRETO.getValor();
	

	public int regra(RepositoryMergeRequestCheckContext context) throws ParseException, IOException {
		

		// metodo responsavel por retornar e criar caminho do Json do pullrequest
		try {
			caminhoMerge = call.pullJson(links.gerarStringStash(context));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// metodo responsavel por retornar e criar caminho do Json do commits
		// relacionados no pullrequest
		try {
			arqCommit = call.commitJson(links.gerarStringStash(context));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// recebe o nome dos jiras relaciondos no commit
		nJira = info.retornarIssue(arqCommit);

		// metodo responsavel por retornar e criar caminho do Json do jiras relacionados
		// no commits
		try {
			caminhoIssue = call.jiraJson(links.gerarStringJira(), nJira);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// recupera o nome do branch e fixVersion
		String branch = info.retornarBranch(caminhoMerge);
		Vector<String> fix = info.retornarFix(caminhoIssue,branch);
		aprovacao = Valor.CORRETO.getValor();
		// valida se o fixVersion e branch estão nos padrões definidos
		for (String fixVersion : fix) {
			if (aprovacao == 0) {
				aprovacao = valida.aplicarRegra(fixVersion, branch);
			} else {
				return aprovacao;
			}
		}
		return aprovacao;
	}

}
