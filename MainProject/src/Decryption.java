import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Decryption {

	public static String decryption(String password, String filename) throws FileNotFoundException, 
	IOException, NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String keyString = password;
    	String line = "";
    	Vector<byte[]> cipherLines = new Vector<byte[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        	while ((line = reader.readLine()) != null) {
        		byte[] decodedBytes = Base64.getDecoder().decode(line);
                cipherLines.add(decodedBytes); 
             }
        }
    	
        byte[] keyStrict = keyString.getBytes();
        keyStrict = Arrays.copyOf(keyStrict, 16);
        SecretKey key = new SecretKeySpec(keyStrict, "AES");
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < cipherLines.size(); i++) {
            byte[] decryptedLine = cipher.update(cipherLines.get(i));
            if (decryptedLine != null && decryptedLine.length != 0) {
            decryptedText.append(new String(decryptedLine));
            }
        }
        byte[] finalDecryptedLine = cipher.doFinal();
        decryptedText.append(new String(finalDecryptedLine));
        
        String KeyValueString = "([A-Za-z]{1,}\s{0,}[=]\s{0,}[0-9]{1,}[.]?[0-9]{0,})";
        Pattern pattern = Pattern.compile(KeyValueString);
        Matcher matcher = pattern.matcher(decryptedText);
        StringBuilder resStr = new StringBuilder();
        String remainingText = decryptedText.toString().replaceAll(KeyValueString, "").trim();
        if (!remainingText.isEmpty()) {
           resStr.append(remainingText).append('\n');
        }
        while (matcher.find()) {
        	resStr.append(matcher.group()).append('\n');
        }
        
        String newFilename = "Input.txt";
        try (FileWriter writer = new FileWriter(newFilename)) {
            writer.write(resStr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFilename;
	}
}
