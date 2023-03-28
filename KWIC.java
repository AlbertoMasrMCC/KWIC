import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class KWIC {

    private static HashMap<Integer, String> characters  = new HashMap<>();
    private static ArrayList<Integer> index             = new ArrayList<>();
    private static ArrayList<Integer> alphabetizedIndex = new ArrayList<>();

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

        ArrayList<String> keyWords = new ArrayList<>();

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if(line.isEmpty() || keyWords.contains(line))
                continue;

            keyWords.add(line);

        }

        int indexLine = 0;

        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();

        for(int i = 0; i < keyWords.size(); i++) {

            for(int j = 0; j < listOfFiles.length; j++) {

                if(!listOfFiles[j].isFile())
                    continue;

                if(!listOfFiles[j].getName().contains(keyWords.get(i)))
                    continue;

                if(characters.containsValue(listOfFiles[j].getName()))
                    continue;

                characters.put(indexLine, listOfFiles[j].getName());
                index.add(indexLine);
                indexLine++;

            }

        }

        scanner.close();

    }

    public static void alphabetizer() {

        alphabetizedIndex.add(index.get(0));
        
        for(int i = 1; i < index.size(); i++) {

            for (int j = 0; j < i; j++) {

                if (characters.get(index.get(i)).toUpperCase().split("\\.")[0].compareTo(characters.get(alphabetizedIndex.get(j)).toUpperCase().split("\\.")[0]) < 0) {

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

            System.out.println(characters.get(alphabetizedIndex.get(i)));

        }

    }

}
