import java.util.ArrayList;

public class SearchPage {

	String query;
	ArrayList<Post> posts;
	int total;
	
	public SearchPage( String _query, int _total, ArrayList<Post> _posts){
		query = _query;
		total = _total;
		posts = _posts;
	}
	
	@Override
	public String toString(){
		String info = String.format("Search query: %s\nTotal: %d\n\n", query, total);
		for (Post post : posts) {
			info += String.format("%s\n", post);
		}
		return info;
	}

}
