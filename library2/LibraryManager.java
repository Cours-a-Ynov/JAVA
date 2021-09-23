package library;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

public class LibraryManager {
    static final Path LIBRARY_PATH = Path.of("./library.dat");
    static ArrayList<Book> books = new ArrayList<Book>();
    public static void main(String[] args) {
        int input = 0;
        readSavedBooks();
        do{
            input = menu();
            switch(input){
                case 1:
                listBooks();
                break;
                case 2:
                createBook();
                break;
                case 3:
                borrowBook();
                break;
                case 4:
                restituteBook();
                break;
            }
        }
        while(input != 5);
        System.out.println("Goodbye!");
    }

    static void readSavedBooks(){
        try{
            if(!Files.exists(LIBRARY_PATH)){
                return;
            }
            byte[] bookData = Files.readAllBytes(LIBRARY_PATH);
            ByteArrayInputStream bais = new ByteArrayInputStream(bookData);
            ObjectInputStream inputStream = new ObjectInputStream(bais);
            try{
                // Optionnel : Passer par un tableau simple plutôt qu'un arraylist. Voire commentaire plus bas.
                // Serialisez et désérialisez directement l'array list fonctionnera en revanche malgré le warning.
                Book[] bookArray = (Book[]) inputStream.readObject();
                books.addAll(Arrays.asList(bookArray));
            }
            catch(Exception e){
                System.err.println("Something went wrong while reading library file. " + e.getMessage());
            }
        }
        catch(IOException e){
            System.err.println("Could not read library file.");
        }
        

    }

    static void saveBooks(){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(baos);
            // Optionnel : transformer l'ArrayList en Array pour éviter un warning.
            // Serialisez directement l'array list fonctionnera en revanche malgré le warning.
            Book[] bookArray = new Book[books.size()];
            books.toArray(bookArray);
            outputStream.writeObject(bookArray);
            byte[] data = baos.toByteArray();
            Files.write(LIBRARY_PATH, data, StandardOpenOption.CREATE);
        }
        catch(Exception e){
            System.err.println("Something went wrong while saving book : " + e.getMessage());
        } 
    }

    public static int menu(){
        System.out.println("Please state what you want to do:");
        System.out.println("1. Print list of books");
        System.out.println("2. Input a new book");
        System.out.println("3. Borrow a book");
        System.out.println("4. Restitute a book");
        System.out.println("5. Quit");
        try {
            int input = Integer.parseInt(getStringFromConsole("Please input your choice's number : "));
            if(input < 1 || input > 5){
                throw new LibraryException("Bad Number.");
            }
            return input;
        }
        catch(Exception e){
            System.err.println("Please input a valid number : ");
            return menu();
        }
    }

    static void listBooks(){
        for(int i = 0; i < books.size(); i++){
            System.out.print(" " + (i + 1) + ". ");
            books.get(i).display();
            System.out.print("\n");
        }
    }
    static void createBook(){
        try {
            Book book = new Book();
            books.add(book);
            saveBooks();
        }
        catch(Exception e){
            System.err.println("Something went wrong while typing : " + e.getMessage());
        }
    }



    static void borrowBook(){
        try {
            int id = askForBookId();
            Book book = books.get(id - 1);
            book.Borrow();
            saveBooks();
        }
        catch(Exception e){
            System.err.println("Could not borrow book : " + e.getMessage());
        }

    }


    static void restituteBook(){
        try {
            int id = askForBookId();
            Book book = books.get(id - 1);
            book.Restitute();
            saveBooks();
        }
        catch(Exception e){
            System.err.println("Could not restitute book : " + e.getMessage());
        }
    }

    static int askForBookId() throws Exception{
        try {
            int id = Integer.parseInt(getStringFromConsole("Which book ?"));
            if(id < 1 || id > books.size()){
                throw new ArrayIndexOutOfBoundsException("Id does not exist");
            }
            return id;
        }
        catch(Exception e){
            throw new LibraryException("Could not retrieve book : " + e.getMessage());
        }

    }

    static String getStringFromConsole(String message) throws IOException{
        System.out.println(message);
        System.out.print(">");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String input = br.readLine();
        if(input == ""){
            throw new IOException("Input cannot be empty.");
        }
        return input;
    }
}
