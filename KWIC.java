import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class KWIC {

    private static HashMap<Integer, String> characters   = new HashMap<>();
    private static ArrayList<Integer> index              = new ArrayList<>();
    private static ArrayList<Integer> alphabetizedIndex  = new ArrayList<>();

    public static void main(String[] args) {

        try {

            input();
            circularShift();
            alphabetizer();
            output();

        } catch (IOException e) {

            System.err.println("Error al leer el archivo: "+ e.getMessage());

        }

    }

    public static void input() throws IOException {
        
        Scanner scanner = new Scanner(new FileReader("resources/ejemplo.txt"));

        int lineIndex = 0;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            characters.put(lineIndex, line);

            lineIndex++;

        }

        scanner.close();

    }

    public static void circularShift() {

        int finalIndex = characters.size();

        for (int i = 0; i < finalIndex; i++) {

            String line = characters.get(i);

            String[] words = line.split("\\s+");

            index.add(i);

            for (int j = 1; j < words.length; j++) {

                int wordIndex = line.indexOf(words[j]);

                String lineCircularShifted = line.substring(wordIndex) + " " + line.substring(0, wordIndex);

                index.add(characters.size());
                characters.put(characters.size(), lineCircularShifted);

            }

        }

    }

    public static void alphabetizer() {

        alphabetizedIndex.add(index.get(0));
        
        for(int i = 1; i < index.size(); i++) {

            for (int j = 0; j < i; j++) {

                if (characters.get(index.get(i)).toUpperCase().compareTo(characters.get(alphabetizedIndex.get(j)).toUpperCase()) < 0) {

                    alphabetizedIndex.add(j, index.get(i));
                    break;

                }
                else 
                    if (j == i - 1) {

                        alphabetizedIndex.add(index.get(i));
                        break;

                    }

            }

        }

    }

    public static void output() {

        for (int i = 0; i < alphabetizedIndex.size(); i++) {

            System.out.println(characters.get(alphabetizedIndex.get(i)));

        }

    }

}
