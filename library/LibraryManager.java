package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    static final Path LIBRARY_PATH = Path.of("./library.csv");
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
            List<String> bookLines = Files.readAllLines(LIBRARY_PATH);
            for(int i = 0; i < bookLines.size(); i++){
                try{
                    Book b = new Book(bookLines.get(i));
                    books.add(b);
                }
                catch(Exception e){
                    System.err.println("Book line is malformed : " + bookLines.get(i));
                    System.err.println("Reason : " + e.getMessage());
                }
                
            }
        }
        catch(IOException e){
            System.err.println("Could not read library file. " + e.getMessage());
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
            saveBook(book,books.size()-1);
        }
        catch(Exception e){
            System.err.println("Something went wrong while typing : " + e.getMessage());
        }
    }

    static void saveBook(Book book, int lineNb){
        try{
            if(!Files.exists(LIBRARY_PATH)){
                Files.createFile(LIBRARY_PATH);
            }
            List<String> lines = Files.readAllLines(LIBRARY_PATH);
            if(lines.size() <= lineNb){
                Files.writeString(LIBRARY_PATH,book.toCsvLine(),StandardOpenOption.APPEND);
            }
            else {
                lines.set(lineNb,book.toCsvLine());
                Files.writeString(LIBRARY_PATH, String.join("\n",lines));
            }
        }
        catch(Exception e){
            System.err.println("Something went wrong while saving book : " + e.getMessage());
        } 
    }

    static void borrowBook(){
        try {
            int id = askForBookId();
            Book book = books.get(id - 1);
            book.Borrow();
            saveBook(book, id - 1);
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
            saveBook(book, id - 1);
        }
        catch(Exception e){
            System.err.println("Could not restitute book : " + e.getMessage());
        }
    }

    static int askForBookId() throws LibraryException{
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
