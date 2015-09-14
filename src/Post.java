

public class Post {
	String title,
		content,
		url,
		excerpt,
		date;
//	String[] categories;
	
	public Post() {
		
	}
	
	public Post(String _title, String _content, String _url, String _excerpt, String _date){
		title = _title;
		content = _content;
		url = _url;
		date = _date;
		excerpt = _excerpt;
	}
	
	@Override
	public String toString(){
		String info = String.format("title: %s\nurl: %s\nexcerpt: %s\ndate: %s\n\n", title, url, excerpt, date);
		return info;
	}
}
