

public class Post {
	String title,
		content,
		URL,
		excerpt,
		date;
	String[] categories;
	
	public Post(String _title, String _content, String _URL, String _excerpt, String _date){
		title = _title;
		content = _content;
		URL = _URL;
		date = _date;
		excerpt = _excerpt;
	}
	
	@Override
	public String toString(){
		String info = String.format("title: %s\nURL: %s\nexcerpt: %s\ndate: %s\n\n", title, URL, excerpt, date);
		return info;
	}
}
