/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class EncriptaDecriptaRSA {

    public static final String ALGORITHM = "RSA";

    /**
     * Local da chave privada no sistema de arquivos.
     */
    public static final String PATH_CHAVE_PRIVADA = "src/keys/private.key";

    /**
     * Local da chave pública no sistema de arquivos.
     */
    public static final String PATH_CHAVE_PUBLICA = "src/keys/public.key";

    /**
     * Gera a chave que contém um par de chave Privada e Pública usando 1025
     * bytes. Armazena o conjunto de chaves nos arquivos private.key e
     * public.key
     */
    public static void geraChave() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);

            final KeyPair key = keyGen.generateKeyPair();

            File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA);
            File chavePublicaFile = new File(PATH_CHAVE_PUBLICA);

            // Cria os arquivos para armazenar a chave Privada e a chave Publica
            if (chavePrivadaFile.getParentFile() != null) {
                chavePrivadaFile.getParentFile().mkdirs();
            }

            chavePrivadaFile.createNewFile();

            if (chavePublicaFile.getParentFile() != null) {
                chavePublicaFile.getParentFile().mkdirs();
            }

            chavePublicaFile.createNewFile();

            // Salva a Chave Pública no arquivo
            ObjectOutputStream chavePublicaOS = new ObjectOutputStream(
                    new FileOutputStream(chavePublicaFile));
            chavePublicaOS.writeObject(key.getPublic());
            chavePublicaOS.close();

            // Salva a Chave Privada no arquivo
            //  System.out.println("KEY: "+new String(key.getPublic().getAlgorithm()));   
            ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(
                    new FileOutputStream(chavePrivadaFile));
            chavePrivadaOS.writeObject(key.getPrivate());
            chavePrivadaOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Verifica se o par de chaves Pública e Privada já foram geradas.
     */
    public static boolean verificaSeExisteChavesNoSO() {

        File chavePrivada = new File(PATH_CHAVE_PRIVADA);
        File chavePublica = new File(PATH_CHAVE_PUBLICA);
//        System.out.println(chavePrivada.getAbsoluteFile());
//        System.out.println(chavePublica.getAbsoluteFile());
        if (chavePrivada.exists() && chavePublica.exists()) {
            return true;
        }

        return false;
    }

    /**
     * Criptografa o texto puro usando chave pública.
     */
    public static byte[] criptografa(String texto, PublicKey chave) {
        byte[] cipherText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            System.out.println("Tamanho: " + texto.getBytes().length);
            cipherText = cipher.doFinal(texto.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    static String toHex(byte[] texto) {
        if (texto == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : texto) {
            sb.append(String.format("%02X", b));
        }
        String Hex = sb.toString();
        return Hex;
    }

    static String Hes(byte[] texto) {
        if (texto == null) {
            return null;
        }
        byte[] encode = Base64.getEncoder().encode(texto);
        return new String(encode);

    }

    /**
     * Decriptografa o texto puro usando chave privada.
     */
    public static String decriptografa(byte[] texto, PrivateKey chave) {
        byte[] dectyptedText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Decriptografa o texto puro usando a chave Privada
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    /**
     * O Algoritmo
     */
    public String GerarAssinatura(String data_doc, String data_hora, String num_Doc, Double Total, String hash) {

        try {

            // Verifica se já existe um par de chaves, caso contrário gera-se as chaves..
            if (!verificaSeExisteChavesNoSO()) {
       // Método responsável por gerar um par de chaves usando o algoritmo RSA e
                // armazena as chaves nos seus respectivos arquivos.
////                        geraChave();
                JOptionPane.showMessageDialog(null, "Documento sem assinatura.", "Erro de assinatura.", JOptionPane.ERROR_MESSAGE);
                return null;
            }
//            System.out.println("total_doc="+Total+"0");
            NumberFormat formatter = new DecimalFormat("#0.00");
            String valor = formatter.format(Total);
            System.out.println(valor);
            final String msgOriginal = data_doc+";"+data_hora+";"+num_Doc+";"+valor+";";
            ObjectInputStream inputStream = null;

            // Criptografa a Mensagem usando a Chave Pública
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            final PublicKey chavePublica = (PublicKey) inputStream.readObject();
            byte[] encoded = chavePublica.getEncoded();
            final byte[] textoCriptografado = criptografa(msgOriginal, chavePublica);

            // Decriptografa a Mensagem usando a Chave Pirvada
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
            final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
            final String textoPuro = decriptografa(textoCriptografado, chavePrivada);

      // Imprime o texto original, o texto criptografado e
            // o texto descriptografado.
//            System.out.println("Mensagem Original: " + msgOriginal);
//            System.out.println("Mensagem Criptografada: " + textoCriptografado.toString());
//            System.out.println("Mensagem Decriptografada: " + textoPuro);
//            System.out.println("--------------");
//            System.out.println("CPUB: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWUhVxsOkqF9P5KJj9ln2KpqyIbFKcMiEhTlSnANFkcaun/9iVYgrDhcwPeEd2CpFV15KVYIDOSbwdE7iti6pZwItpB24cHYWqyTp0pF03KkoPouICFetXHptEgBuVcOGYs8R/9eR08XpESwLku0vcYW+H2MkylxorsfWt/U+0YQIDAQAB");
            System.out.println("CPUB: " + Hes(chavePublica.getEncoded()));
            System.out.println("CPRI: " + Hes(chavePrivada.getEncoded()));

            return Hes(textoCriptografado) + hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
