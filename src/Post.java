import java.util.ArrayList;

public class Post {
	String title,
		content,
		url,
		excerpt,
		date,
		thumbnailUrl;
	Author author;

	ArrayList<String> categories;
	ArrayList<String> tags;
	
	public Post() {
		
	}
	
	// add author param
	public Post(
			String _title, String _content, String _url,
			String _excerpt, String _date, String _thumbnailUrl,
			ArrayList<String> _categories, ArrayList<String> _tags, Author _author){
		title = _title;
		content = _content;
		url = _url;
		date = _date;
		excerpt = _excerpt;
		categories = _categories;
		tags = _tags;
		author = _author;
		thumbnailUrl = _thumbnailUrl;
	}
	
	@Override
	public String toString(){
		String info = String.format(
				"title: %s\nurl: %s\nauthor: %s\nexcerpt: %s\ndate: %s\ncategories: %s\ntags: %s\nthumbnail: %s\n",
				title, url, author.name, excerpt, date, categories, tags, thumbnailUrl);
		return info;
	}
}
