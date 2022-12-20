package encryptdecrypt;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
public class CryptAndDecryptFile {
	
	private static final String KEY="djfkcfirkedfgkcv";


    public static void main(String[] args) {
        // Crea la finestra principale e imposta il layout
        JFrame frame = new JFrame("CryptDecryptProgram");
        frame.setLayout(new FlowLayout());

        // Crea i pulsanti per criptare e decriptare
        JButton btnCrypt = new JButton("Crypt");
        JButton btnDecrypt = new JButton("Decrypt");

        // Aggiunge i pulsanti alla finestra
        frame.add(btnCrypt);
        frame.add(btnDecrypt);

        // Imposta le dimensioni della finestra e rendila visibile
        frame.setSize(200, 100);
        frame.setVisible(true);

        // Crea un oggetto ActionListener per il pulsante di criptazione
        btnCrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Carica la lista di stringhe dal file di input
                List<String> inputList = loadStringsFromFile("input.txt");

                // Cripta la lista di stringhe
                List<String> outputList = cryptStrings(inputList);

                // Scrive la lista di stringhe criptate nel file di output
                writeStringsToFile(outputList, "output.txt");
            }
        });

        // Crea un oggetto ActionListener per il pulsante di decriptazione
        btnDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Carica la lista di stringhe criptate dal file di input
                List<String> inputList = loadStringsFromFile("output.txt");

                // Decripta la lista di stringhe criptate
                List<String> outputList = decryptStrings(inputList);

                // Scrive la lista di stringhe decriptate nel file di output
                writeStringsToFile(outputList, "output.txt");
            }
        });
    }
    
    
    // Metodo per caricare una lista di stringhe da un file di testo
    public static List<String> loadStringsFromFile(String fileName) {
        List<String> stringList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    // Metodo per scrivere una lista di stringhe in un file di testo
    public static void writeStringsToFile(List<String> stringList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String s : stringList) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Metodo per criptare una lista di stringhe utilizzando l'algoritmo AES
    public static List<String> cryptStrings(List<String> stringList) {
        List<String> cryptedStringList = new ArrayList<>();
        try {
            // Crea un oggetto Cipher per criptare i dati utilizzando l'algoritmo AES
            Cipher cipher = Cipher.getInstance("AES");

            // Crea una chiave per l'algoritmo AES
            Key key = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            // Inizializza il Cipher per la criptazione
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Cripta ogni stringa nella lista di stringhe
            for (String s : stringList) {
                // Converte la stringa in un array di byte
                byte[] plainText = s.getBytes("UTF-8");

                // Cripta l'array di byte utilizzando il Cipher
                byte[] cipherText = cipher.doFinal(plainText);

                // Converte l'array di byte criptato in una stringa in formato esadecimale
                String cryptedString = bytesToHex(cipherText);
                cryptedStringList.add(cryptedString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cryptedStringList;
    }
    
    // Metodo per decriptare una lista di stringhe criptate utilizzando l'algoritmo AES
    public static List<String> decryptStrings(List<String> cryptedStringList) {
        List<String> decryptedStringList = new ArrayList<>();
        try {
            // Crea un oggetto Cipher per decriptare i dati utilizzando l'algoritmo AES
            Cipher cipher = Cipher.getInstance("AES");

            // Crea una chiave per l'algoritmo AES
            Key key = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            // Inizializza il Cipher per la decriptazione
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Decripta ogni stringa nella lista di stringhe criptate
            for (String s : cryptedStringList) {
                // Converte la stringa criptata in formato esadecimale in un array di byte
                byte[] cipherText = hexToBytes(s);

                // Decripta l'array di byte utilizzando il Cipher
                byte[] plainText = cipher.doFinal(cipherText);

                // Converte l'array di byte decriptato in una stringa
                String decryptedString = new String(plainText, "UTF-8");
                decryptedStringList.add(decryptedString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedStringList;
    }
    
    // Metodo per convertire un array di byte in una stringa in formato esadecimale
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Metodo per convertire una stringa in formato esadecimale in un array di byte
    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
