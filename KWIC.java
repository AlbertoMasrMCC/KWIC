import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class KWIC {

    private static ArrayList<String> keyWords               = new ArrayList<>();
    private static HashMap<Integer, String> characters      = new HashMap<>();

    private static ArrayList<Integer> index                 = new ArrayList<>();
    private static ArrayList<Integer> alphabetizedIndex     = new ArrayList<>();

    public static void main(String[] args) {

        try {

            input();
            seekKeyWords();
            // alphabetizer();
            // output();

        } catch (IOException e) {

            System.err.println("Error al leer el archivo: "+ e.getMessage());

        }

    }

    public static void input() throws IOException {

        // Leemos las palabras claves
        Scanner scanner = new Scanner(new FileReader("resources/palabrasClaves.txt"));

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if(line.isEmpty())
                continue;

            keyWords.add(line);

        }
        
        // Leemos el pdf o docx
        PdfReader reader = new PdfReader("resources/libro.pdf");
        int numPages = reader.getNumberOfPages();

        int lineIndex = 0;

        for (int i = 1; i <= numPages; i++) {

            String page = PdfTextExtractor.getTextFromPage(reader, i);

            String lines[] = page.split("\n");

            for (String line : lines) {

                if(line.isEmpty())
                    continue;

                characters.put(lineIndex, line +"#"+ i);
                lineIndex++;

            }
            
        }

        reader.close();

    }

    public static void seekKeyWords() {

        for(String keyWord : keyWords) {
                
            keyWord = keyWord.toUpperCase();

            for(int i = 0; i < characters.size(); i++) {

                String line = characters.get(i).toUpperCase();

                if (line.contains(keyWord)) {

                    index.add(i);
                    break;

                }

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