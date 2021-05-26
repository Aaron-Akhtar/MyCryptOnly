package me.aaronakhtar.mycryptonly;

import me.aaronakhtar.crypto.AesCrypt;
import me.aaronakhtar.mycryptonly.crypto.Aes;

import javax.crypto.SecretKey;
import java.io.File;
import java.util.Scanner;
import java.util.StringJoiner;

public class Input {

    private static final String getPrefix(){
        return "@" + App.name.getVal() + " /> ";
    }

    protected static final void input(){
        final Scanner scanner = new Scanner(System.in);
        while(MyCryptOnly.running){
            System.out.print(getPrefix());
            process(scanner.nextLine());
        }
    }

    private static void process(String i){
        final String[] args = i.split(" ");
        switch (args[0].toLowerCase()){

            case "encstr":{
                // encstr <enc-key> <text ...>
                if (args.length < 3){
                    System.out.println("Invalid Args: encryption key, text . . .");
                    break;
                }

                final StringJoiner joiner = new StringJoiner(" ");
                for (int x = 2; x < args.length; x++){
                   joiner.add(args[x]) ;
                }

                System.out.println("Starting Encryption...");

                try {
                    final String encstr = Aes.encrypt(joiner.toString(), args[1]);
                    System.out.println("Your Encrypted String: ");
                    System.out.println(encstr);
                }catch (Exception e){
                    System.out.println("Error During Encryption: " + e.getMessage());
                }
                break;
            }

            case "decstr":{
                // decstr <enc-key> <text ...>
                if (args.length < 3){
                    System.out.println("Invalid Args: decryption key, text . . .");
                    break;
                }

                final StringJoiner joiner = new StringJoiner(" ");
                for (int x = 2; x < args.length; x++){
                    joiner.add(args[x]) ;
                }

                System.out.println("Starting Decryption...");

                try {
                    final String decstr = Aes.decrypt(joiner.toString(), args[1]);
                    System.out.println("Your Decrypted String: ");
                    System.out.println(decstr);
                }catch (Exception e){
                    System.out.println("Error During Decryption: " + e.getMessage());
                }
                break;
            }

            case "enc":{
                // enc file outputfile
                if (args.length != 3){
                    System.out.println("Incorrect Args: file, output file");
                    break;
                }
                try {
                    final File fileToEncrypt = new File(args[1]);
                    if (!fileToEncrypt.exists()) {
                        System.out.println("File To Encrypt doesn't exist...");
                        break;
                    }
                    final File outputFile = new File(args[2]);
                    //final String encryptedFileName = Aes.encrypt(fileToEncrypt.getName(), args[3]);

                    System.out.println("Encrypting File [" + fileToEncrypt.getName() + "]...");

                    final SecretKey secretKey = AesCrypt.makeKey(256);

                    try {
                        AesCrypt.encryptFile(secretKey, fileToEncrypt, outputFile);
                    }catch (Exception e){
                        System.out.println("Error During Encryption: " + e.getMessage());
                        break;
                    }

                    if (!fileToEncrypt.delete()){
                        System.out.println("Error: Failed to delete the non-encrypted file...");
                        break;
                    }

                    System.out.println("Your Encryption Key: " + AesCrypt.encodeKey(secretKey));
                    System.out.println("To Encrypt your Key String, use 'encstr <encryption-key> <output-file> <text>'");
                }catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            }
            case "dec":{
                // dec encfile decfile decrypt_key
                if (args.length != 4){
                    System.out.println("Incorrect Args: encrypted file, decrypted file output, decryption/encryption key");
                    break;
                }
                try {
                    final SecretKey secretKey = AesCrypt.rebuildKey(args[3]);
                    final File fileToDecrypt = new File(args[1]);
                    if (!fileToDecrypt.exists()) {
                        System.out.println("File To Decrypt doesn't exist...");
                        break;
                    }
                    final File outputFile = new File(args[2]);
                    //final String encryptedFileName = Aes.encrypt(fileToEncrypt.getName(), args[3]);

                    System.out.println("Decrypting File [" + fileToDecrypt.getName() + "]...");

                    try {
                        AesCrypt.decryptFile(secretKey, fileToDecrypt, outputFile);
                    }catch (Exception e){
                        System.out.println("Error During Decryption: " + e.getMessage());
                        break;
                    }

                    System.out.println("Decryption Successful.");
                    System.out.println("The previously encrypted file is still there.'");
                }catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            }
        }
        System.out.println();
    }

}
