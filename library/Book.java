package library;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

class Book {
    String title = "";
    String author = "";
    int year = 1990;
    Date borrowedDate = null;
    boolean borrowed = false;

    static final DateFormat CSV_DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");
    
    public Book() throws IOException{
        System.out.println("New Book:");
        this.title = LibraryManager.getStringFromConsole("Title :");
        this.author = LibraryManager.getStringFromConsole("Author :");
        try {
            this.year = Integer.parseInt(LibraryManager.getStringFromConsole("Year :"));
        }
        catch(NumberFormatException e){
            throw new IOException("Year must be an integer.");
        }
    }

    public Book(String csvLine) throws Exception{
        String[] parts = csvLine.split(",");
        if(parts.length < 4){
            throw new LibraryException("Line is malformed.");
        }
        this.title = parts[0];
        this.author = parts[1];
        try{
            this.year = Integer.parseInt(parts[2]);
        }
        catch(NumberFormatException e){
            throw new Exception("Year is not an integer");
        }
        
        switch(parts[3]){
            case "Borrowed":
            this.borrowed = true;
            if(parts.length < 5){
                throw new LibraryException("Borrowed date is not available");
            }
            try {
                this.borrowedDate = CSV_DATE_FORMAT.parse(parts[4]);
            }
            catch(DateTimeParseException e){
                throw new Exception("Date is malformed");
            }
            break;
            case "Available":
            this.borrowed = false;
            break;
        }
    }

    public void display(){
        System.out.print(title + " by " + author + ": " + (borrowed ? "Borrowed" : "Available"));
    }

    public String toCsvLine(){
        String line = this.title + "," + this.author + "," + this.year + "," + (this.borrowed ? "Borrowed" : "Available");
        if(this.borrowedDate != null){
            line += "," + CSV_DATE_FORMAT.format(this.borrowedDate);
        }
        line += "\n";
        return line;
    }

    public void Borrow() throws LibraryException{
        if(borrowed){
            throw new LibraryException("Book is already borrowed!");
        }
        borrowed = true;
        borrowedDate = new Date();
    }

    public void Restitute() throws LibraryException{
        if(!borrowed){
            throw new LibraryException("Book is not borrowed!");
        }
        borrowed = false;
        borrowedDate = null;
    }


}
