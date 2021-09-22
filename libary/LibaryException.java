package libary;
import java.util.ArrayList;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

ArrayList<Book> ListeBook = new ArrayList<Book>();
String sql= "SELECT id, title, autor, year FROM Book";
ResultSet rs = stmt.executeQuery(sql);


class getRegister {
    while(rs.next()){
    int id = rs.getInt("id");
			   String title = rs.getString("Titre");
			   String autor = rs.getString("Auteur");
			   int year = rs.getInt("Année");
    
               Book p1 = new book(id,title,autor,year);
			   p1.afficher();
			   ListeBook.add(p1);
	}
}

class getId {

}