package validaEnconding.hook;

/*
 * classe responsavel por definir regra de bloqueio do push
 */
public class Regra {
	// Constantes 
	private static final String ANSI = "iso-8859-1";
	private static final String ASCII = "us-ascii";
	private static final String UTF8 = "utf-8";
	
	// Regra aplicada aos arquivos SQL e Java
	public boolean isRegraSql(String enconding) {
			
		if (enconding.equals(ANSI) || enconding.equals(UTF8) || enconding.equals(ASCII)) {
			return false;
		}
		return true;
	}
	
	// Regra aplicada aos arquivos Java e proprietes 
	public boolean isRegraJava (String enconding) {
		
		if(enconding.equals(UTF8) || enconding.equals(ASCII)) {
			return false;
		}
		return true;
	}
	
}
