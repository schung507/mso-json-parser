import java.util.ArrayList;


public class TagPage {
	String tag;
	ArrayList<Post> posts;

	public TagPage( String _tag, ArrayList<Post> _posts){
		tag = _tag;
		posts = _posts;
	}
	
	@Override
	public String toString(){
		String info = String.format("Tag: %s\n\n", tag);
		for (Post post : posts) {
			info += String.format("%s\n", post);
		}
		return info;
	}
}
