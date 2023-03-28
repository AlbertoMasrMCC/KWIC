import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class KWIC {

    private static HashMap<Integer, String> characters  = new HashMap<>();
    private static ArrayList<Integer> index             = new ArrayList<>();
    private static ArrayList<Integer> alphabetizedIndex = new ArrayList<>();

    private static HashMap<Integer, ArrayList<Integer>> charactersPages  = new HashMap<>();

    public static void main(String[] args) {

        try {

            input();
            alphabetizer();
            output();

        } catch (IOException e) {

            System.err.println("Error al leer el archivo: "+ e.getMessage());

        }

    }

    public static void input() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Escribe el nombre del archivo de palabras claves que desea leer: ");
        String nombreArchivo = scanner.nextLine();

        if(!nombreArchivo.contains(".txt")) {

            System.out.println("El archivo no es válido");
            System.exit(0);

        }

        scanner = new Scanner(new FileReader("resources/"+ nombreArchivo));

        int indexLine = 0;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if(line.isEmpty() || characters.containsValue(line))
                continue;

            characters.put(indexLine, line);
            charactersPages.put(indexLine, new ArrayList<>());
            index.add(indexLine);
            indexLine++;

        }

        scanner = new Scanner(System.in);
        System.out.print("Escribe el nombre del archivo que quieres leer: ");
        nombreArchivo = scanner.nextLine();

        if(!nombreArchivo.contains(".pdf") && !nombreArchivo.contains(".docx")) {

            System.out.println("El archivo no es válido");
            System.exit(0);

        }

        PdfReader reader = new PdfReader("resources/"+ nombreArchivo);
        int numPages = reader.getNumberOfPages();

        for (int i = 1; i <= numPages; i++) {

            String page = PdfTextExtractor.getTextFromPage(reader, i);

            for(int j = 0; j < characters.size(); j++) {

                if(page.contains(characters.get(j))) {

                    charactersPages.get(j).add(i);

                }

            }
            
        }

        scanner.close();
        reader.close();

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

        System.out.println("\nPalabras claves encontradas: ");
        System.out.println("----------------------------");

        for(int i = 0; i < alphabetizedIndex.size(); i++) {

            System.out.println(characters.get(alphabetizedIndex.get(i)) + " - " + charactersPages.get(alphabetizedIndex.get(i)));

        }

    }

}
