

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
	
	public void printPost(){
		System.out.println("title: " + title + "\nURL: " + URL + "\nexcerpt: " + excerpt + "\ndate: " + date + "\n\n");
	}
}
