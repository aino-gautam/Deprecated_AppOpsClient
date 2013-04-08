package in.appops.client.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

public class TokenHelper {

	private static String ENCRYPTIONKEY;
	
	public HashMap<String, String> tokenToParam(String param) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		String[] parameters = param.split("##");
		for(int i = 0; i < parameters.length; i++) {
			String params = parameters[i];
			String[] nameValueArr = params.split("=");
			String name = nameValueArr[0];
			String value = nameValueArr[1];
			paramMap.put(name, value);
		}
		return paramMap;
	}
	
	public String paramToToken(HashMap<String, String> paramMap) {
		String separator = "##";
		if(!paramMap.isEmpty()) {
			StringBuffer buffer = new StringBuffer();
			Set<String> keySet = paramMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			int count = 0;
			while(iterator.hasNext()) {
				count++;
				String key = iterator.next();
				String value = paramMap.get(key);
				if(count == 1) {
					buffer.append(key + "=" + value);
				} else {
					buffer.append(separator);
					buffer.append(key + "=" + value);
				}
			}
			return buffer.toString();
		} else {
			return null;
		}
	}
	
	public String encryptToken(String value) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(ENCRYPTIONKEY.getBytes());
		String enc = null;
		try {
		  enc = cipher.encrypt(value);
		  return enc;
		} catch (DataLengthException e1) {
		  e1.printStackTrace();
		} catch (IllegalStateException e1) {
		  e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
		  e1.printStackTrace();
		}
		return null;
	}
	
	public String decryptToken(String enc) {
		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(ENCRYPTIONKEY.getBytes());
		String dec ="";
		try {
		  dec = cipher.decrypt(enc);
		  return dec;
		} catch (DataLengthException e) {
		    e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void generateKey() {
		int length = 16;
		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		
		char[] text = new char[length];
		for(int i = 0; i < length; i++) {
			text[i] = characters.charAt(random.nextInt(characters.length()));
		}
		ENCRYPTIONKEY = new String(text);
	}
}
