import java.util.Base64;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;


public class DigitalSignatureSolution {

    public static void main(String[] args) throws Exception {
            //Read the text file and save to String data
            String fileName = "shorttext.txt";
            String data = "";
            String line;
            BufferedReader bufferedReader = new BufferedReader( new FileReader(fileName));
            while((line= bufferedReader.readLine())!=null){
                data = data +"\n" + line;
            }
            System.out.println("Original content: "+ data);

            //TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair keyPair = keyGen.generateKeyPair();
            Key publicKey = keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();

            //TODO: Calculate message digest, using MD5 hash function
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(data.getBytes());

            //TODO: print the length of output digest byte[], compare the length of file shorttext.txt and longtext.txt
            System.out.println(digest.length);

            //TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            //TODO: encrypt digest message
            byte[] output = cipher.doFinal(digest);
            System.out.println(output.length);

            //TODO: print the encrypted message (in base64format String using Base64)
            String base64format = Base64.getEncoder().encodeToString(output);
            System.out.println(base64format);

            //TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.
            Cipher dcipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            dcipher.init(Cipher.DECRYPT_MODE, publicKey);

            //TODO: decrypt message
            byte[] output2 = dcipher.doFinal(output);
            //TODO: print the decrypted message (in base64format String using Base64), compare with origin digest
            String base64format2 = Base64.getEncoder().encodeToString(output2);
            String base64digest = Base64.getEncoder().encodeToString(digest);
            System.out.println(base64format2);
            if(base64format2.equals(base64digest)){
                System.out.println("same");
            }else{
                System.out.println("different");
            }
    }

}
