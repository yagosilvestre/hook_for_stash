package versao.hook;

import versao.enuns.Branch;
import versao.enuns.Valor;
import versao.util.Constantes;

/*
 * faz a validação das informações e aplica a regra de bloqueio  relacionada
 */
public class AplicaRegra {

	private int aprovacao = Valor.CORRETO.getValor();

	// Constantes

	// Aplica regra conforme a branch de destino
	public int aplicarRegra(String fixVersion, String branch) {
		
		String compFix = fixVersion;
		String[] compBranch = branch.split(Constantes.SEPARADOR_BARRA);
		if (validarFix(compFix)) {
			aprovacao = Valor.CORRETO.getValor();
			if (compBranch[2].equals(Branch.BRANCH_1.getBranch()) || compBranch[2].equals(Branch.BRANCH_2.getBranch())
					|| compBranch[2].equals(Branch.BRANCH_3.getBranch())) {
				if (compBranch[2].equals(Branch.BRANCH_1.getBranch())
						|| compBranch[2].equals(Branch.BRANCH_2.getBranch())) {
					aprovacao = regraReleseHotfix(fixVersion, branch);
				} else if (compBranch[2].equalsIgnoreCase(compFix)) {
					aprovacao = Valor.CORRETO.getValor();
				} else {
					aprovacao = Valor.ERRO_DEVELOP.getValor();
				}
			}
		} else {
			aprovacao = Valor.SEM_FIX_VERSION.getValor();
		}
		return aprovacao;
	}

	// verifica se o valor não esta vazio
	private boolean validarFix(String compFix) {
		if (compFix.equals(Constantes.VAZIO)) {
			return false;
		}
		return true;
	}

	// regra de comparação para release e hotfix
	public int regraReleseHotfix(String fixVersion, String branch) {
		String compFix = fixVersion;
		String[] compBranch = branch.split(Constantes.SEPARADOR_BARRA);
		if (compBranch[3].equalsIgnoreCase(compFix)) {
			return aprovacao = Valor.CORRETO.getValor();
		} else {
			return aprovacao = Valor.ERRO_BRANCH.getValor();
		}

	}

}
