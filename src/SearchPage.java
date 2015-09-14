import java.util.ArrayList;

public class SearchPage {

	String query;
	ArrayList<Post> posts;
	
	
	public SearchPage( String _query, ArrayList<Post> _posts){
		query = _query;
		posts = _posts;
	}
	
	@Override
	public String toString(){
		String info = String.format("Search query: %s\nPosts: %s\n", query, posts);
		return info;
	}

}
