package encryptdecrypt;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import encryptdecrypt.util.AesUtil;
/**
 * Classe che rappresenta un programma per criptare e decriptare file di testo utilizzando l'algoritmo di crittografia
 * a chiave simmetrica AES (Advanced Encryption Standard).
 */
public class CryptAndDecryptFile {
	

    public static void main(String[] args) {
        // Crea la finestra principale e imposta il layout
        JFrame frame = new JFrame("CryptDecryptProgram");
        frame.setLayout(new FlowLayout());
        
        // Crea un campo per inserire la chiave di criptazione
        JPasswordField keyField = new JPasswordField(25);
        frame.add(keyField);
        
        // Crea i pulsanti per criptare e decriptare
        JButton btnCrypt = new JButton("Crypt");
        JButton btnDecrypt = new JButton("Decrypt");

        // Aggiunge i pulsanti alla finestra
        
        frame.add(btnCrypt);
        frame.add(btnDecrypt);

        // Crea una label per l'output
        JLabel outputLabel = new JLabel(System.lineSeparator() + " ");
        frame.add(outputLabel);

 
        // Imposta le dimensioni della finestra e rendila visibile
        frame.setSize(350, 150);
        frame.setVisible(true);
        
        // Crea un oggetto ActionListener per il pulsante di criptazione
        btnCrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni la chiave di criptazione dal campo di inserimento
                char[] keyChars = keyField.getPassword();
                String key = new String(keyChars);

                // Carica la lista di stringhe dal file di input
                List<String> inputList = AesUtil.loadStringsFromFile("input.txt");

                // Cripta la lista di stringhe
                List<String> outputList = AesUtil.cryptStrings(inputList, key);

                // Scrive la lista di stringhe criptate nel file di output
                AesUtil.writeStringsToFile(outputList, "output.txt");

                // Aggiorna la label di output
                outputLabel.setText("File criptato con successo! Controlla il contenuto del txt!");
            }
        });

        btnDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni la chiave di criptazione dal campo di inserimento
                char[] keyChars = keyField.getPassword();
                String key = new String(keyChars);

                // Carica la lista di stringhe criptate dal file di input
                List<String> inputList = AesUtil.loadStringsFromFile("output.txt");

                // Decripta la lista di stringhe criptate
                List<String> outputList = AesUtil.decryptStrings(inputList, key);

                // Scrive la lista di stringhe decriptate nel file di output
                AesUtil.writeStringsToFile(outputList, "output.txt");

                // Aggiorna la label di output
                outputLabel.setText("File decriptato con successo! Controlla il contenuto del txt!");
            }
        });
    }

}
