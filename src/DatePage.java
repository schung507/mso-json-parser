import java.util.ArrayList;


public class DatePage {
	String date;
	ArrayList<Post> posts;
	
	public DatePage( String _date, ArrayList<Post> _posts){
		date = _date;
		posts = _posts;
	}
	
	@Override
	public String toString(){
		String info = String.format("Search date: %s\n\n", date);
		for (Post post : posts) {
			info += String.format("%s\n", post);
		}
		return info;
	}
	
}
