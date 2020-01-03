package versao.enuns;

/*
 * CLASSE COM ENUNS REFERENTES USADOS NO APLICA_REGRA 
 */
public enum Valor {
	CORRETO(0), ERRO_BRANCH(1), ERRO_DEVELOP(2), SEM_FIX_VERSION(3);
	int valor;

	Valor(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
}
