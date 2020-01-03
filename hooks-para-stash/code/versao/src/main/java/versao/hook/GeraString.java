package versao.hook;

import com.atlassian.stash.hook.repository.RepositoryMergeRequestCheckContext;

import versao.util.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/*
//classe responsavel por gerar URL da API 
*/
public class GeraString {

	private  Properties prop = null;

	// metodo responsavel por gerar parte URL da API do Stash
	public URL gerarStringStash(RepositoryMergeRequestCheckContext context) throws MalformedURLException {
		// id do pullRequest
		getProperties();		
		Long pullRecID = context.getMergeRequest().getPullRequest().getId();
		// nome do repositorip
		String repoName = context.getMergeRequest().getPullRequest().getToRef().getRepository().getName();
		// chave do Projeto
		String projeto = context.getMergeRequest().getPullRequest().getFromRef().getRepository().getProject().getKey();

		// link

		URL stashAPI = new URL( prop.getProperty(Constantes.STASH)+ projeto + Constantes.REPOSITORIO + repoName + Constantes.PULL_REQUEST + pullRecID);
		return stashAPI;
		
		
	}

	// metodo responsavel por gerar parte URL da API do jira
	public URL gerarStringJira() throws MalformedURLException {
		// link
		getProperties();
		URL jiraAPI = new URL(prop.getProperty(Constantes.JIRA));
		return jiraAPI;
	}

	// Json com ver��es do projeto =key = chave do projeto
	public URL gerarStringPorjetoJira(String key) throws MalformedURLException {
		getProperties();
		URL projetoJira = new URL(prop.getProperty(Constantes.PROJETO) + key + Constantes.VERSAO);
		return projetoJira;
	}

	
	public void getProperties() {
		try {
			prop = Propriedades.getPropApi();
		} catch (IOException e) {
			LogErro.registarErro("erro ao ler o arquivo mensagem.proprietes");
		}
	}
	
}
