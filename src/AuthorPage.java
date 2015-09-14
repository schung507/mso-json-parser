import java.util.ArrayList;


public class AuthorPage {
	Author author;
	ArrayList<Post> posts;
	
	
	public AuthorPage(Author _author, ArrayList<Post> _posts ){
		author = _author;
		posts = _posts;
	}
	
	public AuthorPage(AuthorPage oldAuthorPage){
		this.author = oldAuthorPage.author;
		this.posts = oldAuthorPage.posts;
	}
}
