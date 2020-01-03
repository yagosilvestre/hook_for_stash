package versao.hook;

import com.atlassian.stash.hook.repository.*;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.setting.*;

import versao.enuns.Valor;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class MyMergeCheckHook implements RepositoryMergeRequestCheck, RepositorySettingsValidator {

	private Regra arq = new Regra();
	private int valida = Valor.CORRETO.getValor();
	private PrintErro print = new PrintErro();

	public void check(RepositoryMergeRequestCheckContext context) {

		try {
			try {
				// chama classe responsavel por validar se tudo esta no padrão que definido.
				valida = arq.regra(context);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// if responsavel pelo bloqueio do botão "Merge"
		if (valida != 0) {
			String msgTexto = print.msgTexto(valida);
			String msgTitulo = print.msgTitulo();
			context.getMergeRequest().veto(msgTitulo, msgTexto);
			valida = Valor.CORRETO.getValor();
		}

	}

	// função gerada automaticamente pela SDK
	public void validate(Settings settings, SettingsValidationErrors errors, Repository repository) {
		/*
		 * String numReviewersString = settings.getString("reviewers", "0").trim(); if
		 * (!NUMBER_PATTERN.matcher(numReviewersString).matches()) {
		 * errors.addFieldError("reviewers", "Enter a number"); } else if
		 * (Integer.parseInt(numReviewersString) <= 0) {
		 * errors.addFieldError("reviewers",
		 * "Number of reviewers must be greater than zero"); }
		 */
	}
}
