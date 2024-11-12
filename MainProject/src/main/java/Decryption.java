import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decryption {
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;
    
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
	

	public static String decrypt(String password, String in) throws Exception {
		SecretKey secretKey = generateKey(password);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        File inputFile = new File(in);
        String out = "Decoded"+in;
        File outputFile = new File(out);
        
		try (FileInputStream inputStream = new FileInputStream(inputFile);
			 FileOutputStream outputStream = new FileOutputStream(outputFile)) {
			byte[] iv = new byte[IV_SIZE];
			inputStream.read(iv);
			IvParameterSpec ivParams = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] output = cipher.update(buffer, 0, bytesRead);
				if (output != null) {
					outputStream.write(output);
				}
			}
			byte[] outputBytes = cipher.doFinal();
			if (outputBytes != null) {
				outputStream.write(outputBytes);
			}
		}
		return out;
	}

	private static SecretKey generateKey(String password) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] keyBytes = Arrays.copyOf(digest.digest(password.getBytes()), 16);
		return new SecretKeySpec(keyBytes, ALGORITHM);
	}
}
