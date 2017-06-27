import java.util.*;

/**
 * Created by Artem on 27.06.2017.
 */
public class VigenereCipher {
    public static void main(String args[]) {
        String key = "32154";
        Scanner sc = new Scanner(System.in);
        String alphabet = "abcdefghiklmnopqrstuvwxyz";
        String phrase = "toomanyactualparameters";
        String keyWord = "program";
        String chiper = "fdbqrliougcrncofpqxushk";
        phrase = deleteSpace(phrase);
        System.out.println("1.Шифровка, 2.Дешифровка");
        int a = sc.nextInt();
        String[][] Mass;
        String line = "";
        switch (a) {
            case 1:
                System.out.println("Viginere Table");
                Mass = tableVigenere(alphabet, key);
                for (int i = 0; i < 25; i++) {
                    for (int j = 0; j < 25; j++) {
                        System.out.print(Mass[j][i]);
                    }
                    System.out.println();
                }
                System.out.println();
                String[] massPhrase = phrase.split("");
                String[] massKey = Filling(phrase, keyWord);
                Integer[] massValue =  interpAlphabet(massPhrase, alphabet);
                Integer[] massValue2 =  interpAlphabet(massKey, alphabet);
                String[] encrypt = Cipher(massValue, massValue2, Mass);
                System.out.println("Зашифрованое сообщение: ");
                for (int i = 0; i < encrypt.length; i++){
                    System.out.print(encrypt[i]);
                }
                break;
            case 2:
                Mass = tableVigenere(alphabet, key);
                String[] massPhrase2 = chiper.split("");
                String[] massPhrase3 = new String[chiper.length()];
                for (int i = 1; i < massPhrase2.length; i++){
                    massPhrase3[i - 1] = massPhrase2[i];
                }
                int[] decryptValue = new int[massPhrase3.length];
                for (int i = 0; i < 25; i++) {
                    line += Mass[0][i];
                }
                String[] massKey2 = Filling(chiper, keyWord);
                Integer[] massValue3 = interpAlphabet(massKey2, alphabet);
                for (int i = 0; i < massValue3.length; i++) {
                    decryptValue[i] = Decrypt(massValue3[i], massPhrase3[i], line);
                }
                String finaldencrypt = interpDecrypt(alphabet, decryptValue);
                System.out.println("Расшифрованое соощение: ");
                System.out.println(finaldencrypt);
                break;
        }
    }
    public static String deleteSpace(String a){
        String phrase = "";
        String[] delSpace = a.split("");
        List<String> listDelSpace = new ArrayList<String>();
        Collections.addAll(listDelSpace, delSpace);
        for (int i = 0; i < listDelSpace.size(); ++i) {
            if (listDelSpace.get(i).equals(" ")) {
                listDelSpace.remove(i);
            }
        }
        for (int i = 0; i < listDelSpace.size(); ++i) {
            phrase += listDelSpace.get(i);
        }
        return phrase;
    }
    public static String interpDecrypt(String phrase, int[] decryptValue){
        String[] alphabet = phrase.split("");
        String[] alphabet2 = new String[alphabet.length - 1];
        String encrypt = "";
        for (int i = 1; i < alphabet.length; i++){
            alphabet2[i - 1] = alphabet[i];
        }
        for (int i = 0; i < decryptValue.length; i++){
            encrypt += alphabet2[decryptValue[i]];
        }
        return encrypt;
    }
    public static int Decrypt(Integer key, String cipher, String stroka){
        int decrypt = 0;
        String[] mass;
        String[] Line = stroka.split("");
        String[] Line2 = new String[Line.length - 1];
        for (int i = 1; i < Line.length; i++){
            Line2[i - 1] = Line[i];
        }
        int key2 = key - 1;
        mass = shiftArr(Line2, 25 - key2);
        for (int j = 0; j < Line2.length; j++) {
            if (cipher.equals(mass[j])) {
                decrypt = j;
            }
        }
        return decrypt;
    }
    public static String[][] tableVigenere(String phrase, String key){
        String p ="";
        StringBuilder instKey = new StringBuilder(key);
        StringBuilder instSentance = new StringBuilder(phrase);
        char[] keyMass = new char[instKey.length()];
        char[][] table = new char[key.length()][phrase.length()];
        char[][] newTable = new char[key.length()][phrase.length()];
        for (int i = 0, ii = 0; i < key.length(); i++) {
            for (int j = 0; j < key.length(); j++) {
                table[i][j] = instSentance.charAt(ii);
                ii++;
            }
        }
        for (int i = 0; i < instKey.length(); i++) {
            keyMass[i] = instKey.charAt(i);
        }
        //Сортировка алфавита по ключю
        Arrays.sort(keyMass);
        for (int j = 0; j < instKey.length(); j++) {
            for (int y = 0; y < instKey.length(); y++) {
                if ((int) keyMass[j] == (int) instKey.charAt(y)) {
                    for (int i = 0; i < key.length(); i++) {
                        newTable[j][i] = table[i][y];
                    }
                }
            }
        }
        //Вывод результата
        for (int i = 0; i < key.length(); i++) {
            for (int j = 0; j < key.length(); j++) {
                p += newTable[j][i];
            }
        }
        String sortAlphabet[] = p.split("");
        String[] sortAlphabet2 = new String[p.length()];
        for (int i = 0; i < sortAlphabet2.length; i++){
            sortAlphabet2[i] = sortAlphabet[i + 1];
        }
        String[][] Mass = new String[25][25];
        for (int j = 0; j < sortAlphabet2.length; j++){
            Mass[0][j] = sortAlphabet2[j];
        }
        for (int i = 1; i < sortAlphabet2.length; i++){
            sortAlphabet2 = shiftArr(sortAlphabet2, 24);
            for (int j = 0; j < sortAlphabet2.length; j++){
                Mass[i][j] = sortAlphabet2[j];
            }
        }
        return Mass;
    }
    public static String[] shiftArr(String[] inArr, int shift) {
        while(shift > 0) {
            String lastVar = inArr[inArr.length-1];
            for(int counter = 0;counter<inArr.length;counter++) {
                String curVal = inArr[counter];
                inArr[counter] = lastVar;
                lastVar = curVal;
            }
            shift--;
        }
        return inArr;
    }
    public static String[] Filling(String a, String b){
        int c = a.length() % b.length() + 1;
        int d = a.length() / b.length();
        String phrase = "";
        for (int i = 0; i < d; i++){
            phrase += b;
        }
        String[] mass = b.split("");
        for (int i = 0; i < c; i++){
            phrase += mass[i];
        }
        String[] fullPhrase = phrase.split("");
        return fullPhrase;
    }
    public static Integer[] interpAlphabet(String[] a, String phrase){
        String[] alphabet = phrase.split("");
        Integer[] value = new Integer[a.length];
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < alphabet.length; j++){
                if (a[i].equals(alphabet[j])){
                    value[i] = j;
                }
            }
        }
        Integer[] value2 = new Integer[a.length - 1];
        for (int i = 0; i < value2.length; i++){
            value2[i] = value[i + 1];
        }
        return value2;
    }
    public static String[] Cipher(Integer[] a, Integer[] b, String[][] c){
        String[] cipher = new String[a.length];
        for (int i = 0; i < a.length; i++){
            int d = a[i];
            int j;
            for (j = 0; j < b.length;){
                int e = b[i];
                cipher[i] = c[d - 1][e - 1];
                break;
            }
            j++;
        }
        return cipher;
    }
}
