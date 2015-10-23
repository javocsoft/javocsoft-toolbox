package es.javocsoft.android.lib.toolbox.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.javocsoft.android.lib.toolbox.crypto.exception.SHA1EncodingException;

public class SHA1Encoding {

	private SHA1Encoding() {}
	
	
	/**
	 * Generates a SHA-1 hash from an string.
	 * 
	 * @param data	The string to get the SHA-1 from.
	 * @return
	 */
	public static String getSHA1(String data) throws SHA1EncodingException {
		
	    MessageDigest crypt;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
		    crypt.update(data.getBytes("UTF-8"));
		    
		    return new BigInteger(1, crypt.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new SHA1EncodingException("Error generating SHA-1 hash from string (NoSuchAlgorithmException).", e);
		} catch (UnsupportedEncodingException e) {
			throw new SHA1EncodingException("Error generating SHA-1 hash from string (UnsupportedEncodingException).", e);
		}
	}

}
