import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class HelloWorld {
    public static void main(String[] args) {
        System.out.println("What's your name ?");
        java.io.InputStreamReader isr = new java.io.InputStreamReader(System.in);
        java.io.BufferedReader br = new java.io.BufferedReader(isr);
        try {
            String firstName = br.readLine();
            System.out.println("Hello, " + firstName);
        }
        catch(IOException e) {
            System.err.println("Something went wrong : " + e.getMessage());
        }
    }
}