package auxiliary;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator{

    //Determina quais os caracteres que podem ser usadas para gerar a palavra-chave
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    //Reinforce Password
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final String PASSWORD_ALLOW_BASE_SHUFFLE = stringAleatorio(PASSWORD_ALLOW_BASE);
    private static final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

    private static SecureRandom random = new SecureRandom();

    public PasswordGenerator()
    {   	
    }
    
    //Create and return of a password with a certain number of chars
    public String generateRandomPassword(int charNum) {
        if (charNum < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(charNum);
        for (int i = 0; i < charNum; i++) {

            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);

            sb.append(rndChar);
        }
        System.out.println(sb);
        return sb.toString();
        
    }

    //Função que permite aumentar a aleatoriedade na criação da palavra-chave
    public static String stringAleatorio(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        
        return letters.stream().collect(Collectors.joining());
    }        
}