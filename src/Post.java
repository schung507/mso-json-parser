import java.util.ArrayList;

public class Post {
	String title,
		content,
		url,
		excerpt,
		date;
	Author author;
//	String[] categories;
	ArrayList<String> categories;
	ArrayList<String> tags;
	
	public Post() {
		
	}
	
	public Post(
			String _title, String _content, String _url, String _excerpt, String _date,
			ArrayList<String> _categories, ArrayList<String> _tags){
		title = _title;
		content = _content;
		url = _url;
		date = _date;
		excerpt = _excerpt;
		categories = _categories;
		tags = _tags;
	}
	
	public Post(Post oldPost){
		this.title = oldPost.title;
		this.content = oldPost.content;
		this.url = oldPost.url;
		this.date = oldPost.date;
		this.excerpt = oldPost.excerpt;
		this.author = oldPost.author;
		this.categories = new ArrayList<String>(oldPost.categories);
		this.tags = new ArrayList<String>(oldPost.tags);
	}
	
	
	@Override
	public String toString(){
		String info = String.format("title: %s\nurl: %s\nauthor: %s\nexcerpt: %s\ndate: %s \ncategories: %s \ntags: %s \n\n", title, url, author, excerpt, date, categories, tags);
		return info;
	}
}
