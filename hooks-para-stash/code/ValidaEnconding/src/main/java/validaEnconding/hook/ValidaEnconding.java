package validaEnconding.hook;

//importações usadas
import com.atlassian.sal.api.component.ComponentLocator;
import com.atlassian.stash.commit.CommitService;
import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeCallback;
import com.atlassian.stash.content.ChangeContext;
import com.atlassian.stash.content.ChangeSummary;
import com.atlassian.stash.content.ChangesRequest;
import com.atlassian.stash.content.ContentService;
import com.atlassian.stash.hook.*;
import com.atlassian.stash.hook.repository.*;
import com.atlassian.stash.io.TypeAwareOutputSupplier;
import com.atlassian.stash.repository.*;

import validaEnconding.util.Constantes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * {@link https://developer.atlassian.com/static/javadoc/stash/3.1.1/api/reference/com/atlassian/stash/content/ChangesRequest.html}
 * {@link https://developer.atlassian.com/static/javadoc/stash/3.1.1/api/reference/com/atlassian/stash/commit/CommitService.html}
 * {@link https://developer.atlassian.com/static/javadoc/stash/3.1.1/api/reference/com/atlassian/stash/content/ContentService.html}
 * {@link https://developer.atlassian.com/static/javadoc/stash/3.1.1/api/reference/com/atlassian/stash/content/ChangeCallback.html}
 * {@link https://developer.atlassian.com/static/javadoc/stash/3.1.1/api/reference/com/atlassian/stash/io/TypeAwareOutputSupplier.html}
 */
/**
 * @since Classes geradas pela API da atlassian
 *
 */
public class ValidaEnconding implements PreReceiveRepositoryHook {
	

	static CommitService commitService = ComponentLocator.getComponent(CommitService.class);
	static ContentService contentService = ComponentLocator.getComponent(ContentService.class);
	// classe
	Funcao funcao = new Funcao();
	ComandoLinux linux = new ComandoLinux();
	Regra regra = new Regra();
	// Constantes
	private static final String SQL = "sql";
	private static final String XML = "xml";
	//private static final String JAVA = "java";
	private static final String PROPRIEDADE = "properties";
	// VARIAVEIS STATIC
	static String caminhoArquivo = ""; // Nome do Arquivo que est� sendo enviado
	static boolean validador = true; // valida��o do enconding
	static String formato = ""; // Exten��o do arquivo que est� sendo enviado para servidor
	static String mudanca = "";
	static String nomeArquivo = ""; // nome do arquivo que sera criado para verifica��o

	public boolean onReceive(RepositoryHookContext context, Collection<RefChange> refChanges,
			final HookResponse hookResponse) {
		final Repository repo = context.getRepository();
		validador = true;
		// percorre as modifica��es realizadas
		for (final RefChange refChange : refChanges) {
			// retorna mudan�as
			final ChangesRequest changeReq = new ChangesRequest.Builder(context.getRepository(), refChange.getToHash())
					.sinceId(refChange.getFromHash()).build();
			// interface ChangeCallback retorna mudan�as
			ChangeCallback cc = new ChangeCallback() {

				// metodos da interface que n�o implementado
				public void onStart(ChangeContext context) throws IOException {
				}

				public void onEnd(ChangeSummary summary) throws IOException {
				}

				// metodo que pega cada mudana�a realizada no commit
				public boolean onChange(Change change) throws IOException {
					formato = change.getPath().getExtension();
					mudanca = changeReq.getUntilId();

					// nomeArquivo = caminhoArquivo;//.concat(refChange.getToHash());

					// compara os arquivos para validar o enconding
					if (formato.equals(SQL)) {
						caminhoArquivo = change.getPath().toString();
						nomeArquivo = funcao.gerarNome(caminhoArquivo, refChange);
						// Classe retorna OutputStream, usando para criar o arquivo de compara��o
						TypeAwareOutputSupplier supplier = new TypeAwareOutputSupplier() {
							// metodo que gera o arquivo com seu nome na pasta /tmp do servidor
							public OutputStream getStream(String arg0) throws IOException {
								FileOutputStream arquivo = new FileOutputStream(new File(Constantes.CAMINHO_TEMP + nomeArquivo));
								return arquivo;
							}
						};
						// classe retorna arquivo
						contentService.streamFile(repo, mudanca, caminhoArquivo, supplier);

						String[] enconding = linux.retornarEnconding(nomeArquivo);

						// compara o enconding do arquivo. se o arquivo for diferente ascii e recusado
						if (regra.isRegraSql(enconding[1])) {
							// envia uma messagem para o usuario
							funcao.msgSQL(hookResponse, caminhoArquivo);
							validador = false;
						}

						// exclui arquivo gerado para compara��o
						linux.deletarArquivo(nomeArquivo);
					} else if (formato.equals(XML) || formato.equals(PROPRIEDADE)) {
						caminhoArquivo = change.getPath().toString();
						nomeArquivo = funcao.gerarNome(caminhoArquivo, refChange);
						// Classe retorna OutputStream, usando para criar o arquivo de compara��o
						TypeAwareOutputSupplier supplier = new TypeAwareOutputSupplier() {
							// metodo que gera o arquivo com seu nome na pasta /tmp do servidor
							public OutputStream getStream(String arg0) throws IOException {
								FileOutputStream arquivo = new FileOutputStream(new File(Constantes.CAMINHO_TEMP + nomeArquivo));
								return arquivo;
							}
						};
						// classe retorna arquivo
						contentService.streamFile(repo, mudanca, caminhoArquivo, supplier);

						String[] enconding = linux.retornarEnconding(nomeArquivo);

						// compara o enconding do arquivo. se o arquivo for diferente ascii e recusado
						if (regra.isRegraJava(enconding[1])) {
							// envia uma messagem para o usuario
							funcao.msgJAVA(hookResponse, caminhoArquivo);
							validador = false;
						}
						// exclui arquivo gerado para compara��o
						linux.deletarArquivo(nomeArquivo);
					}
					return validador;
				}
			};
			// classe com as informa��es do commit
			commitService.streamChanges(changeReq, cc);
		}
		return validador;
	}

}