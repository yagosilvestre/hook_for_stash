package validaEnconding.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogErro {

	private static final Logger log = LoggerFactory.getLogger(LogErro.class);

	public static void registarErro(String Log) {
		log.info(Log);
	}
}
