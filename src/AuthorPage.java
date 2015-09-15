import java.util.ArrayList;


public class AuthorPage {
	Author author;
	ArrayList<Post> posts;
	
	
	public AuthorPage(Author _author, ArrayList<Post> _posts ){
		author = _author;
		posts = _posts;
	}
	
	public AuthorPage(AuthorPage oldAuthorPage){
		author = oldAuthorPage.author;
		posts = oldAuthorPage.posts;
	}
	
	@Override
	public String toString() {
		String info = String.format("%s\n", author);
		for (Post post : posts) {
			info += String.format("%s\n", post);
		}
		return info;
	}
}
