import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class DataParse {
	
	//returns json reader with URL as input
	public static JsonReader readUrl(String urlString) throws IOException {
		
	    URL url= new URL(urlString); //just a string
	    Reader jsonReader = new InputStreamReader(url.openStream());
	    JsonReader JSONReader = new JsonReader(jsonReader);
	    return JSONReader;
	    
	} 
	
	//Returns Author page 
	public static AuthorPage getAuthorPosts(String url) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		JsonReader JSONReader = readUrl(url);
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
		int pages = parsed.get("pages").getAsInt();
		int count = 0;

		JsonObject authorObject = parsed.get("author").getAsJsonObject();
		Author author = parseAuthorJsonObject(authorObject);
		ArrayList<Post> posts = new ArrayList<Post>();
		
		int pageCounter = 1;
		while (pageCounter <= pages) {
			JSONReader = readUrl(String.format("%s&page=%d", url, pageCounter));
			JsonObject parsedPage = parser.parse(JSONReader).getAsJsonObject();
			JsonArray postArray = parsedPage.get("posts").getAsJsonArray();
			for (JsonElement post : postArray) {
				JsonObject obj = post.getAsJsonObject();
				Post newpost = parsePostJsonObject(obj);
				posts.add(newpost);
				count += 1;
			}
			pageCounter += 1;
		}

		author.setNumposts(count);
		AuthorPage authorPage = new AuthorPage(author, posts);
		System.out.println(authorPage);
		return authorPage;
	}

	public static Author parseAuthor(JsonReader JSONReader) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		JSONReader.beginObject();
		Author author = new Author();
		
		while (JSONReader.hasNext()) {
			String key = JSONReader.nextName();
			if (getDeclaredFieldNames(author).contains(key)) { // key is "name", "position", "email", or "description"
				String val = JSONReader.nextString();
				author.getClass().getDeclaredField(key).set(author, val);
			} else {
				JSONReader.skipValue();
			}
		}
		JSONReader.endObject();
		return author;
		
	}
	
	public static Post parsePost(JsonReader JSONReader) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Post post = new Post();
		JSONReader.beginObject();

		while(JSONReader.hasNext()){
			String key = JSONReader.nextName();
			
			if (getDeclaredFieldNames(post).contains(key)) {
				String fieldTypeName = post.getClass().getDeclaredField(key).getType().getSimpleName();
				Object val = null;
				
				if (fieldTypeName.equals("String")) { // key is "title", "content", "url", "excerpt", or "date"
					val = Jsoup.parse(JSONReader.nextString()).text();
				}
				else if (fieldTypeName.equals("ArrayList")) { // key is "categories" or "tags"
					val = parseTagsOrCategories(JSONReader);
				} else if (fieldTypeName.equals("Author")){
					val = parseAuthor(JSONReader);
				}
				post.getClass().getDeclaredField(key).set(post, val); 
			} else {
				JSONReader.skipValue();
			}
		}
		JSONReader.endObject();
		return post;
	}
//	/*
	public static ArrayList<String> parseTagsOrCategories(JsonReader JSONReader) throws IOException{
		ArrayList<String> tagsOrCategories = new ArrayList<String>();
		JSONReader.beginArray();
		
		while(JSONReader.hasNext()){
			JSONReader.beginObject();
			while(JSONReader.hasNext()){
				String key = JSONReader.nextName(); 
				if(key.equals("title")){
					String title = Jsoup.parse(JSONReader.nextString()).text();
					tagsOrCategories.add(title);
				}
				else {
					JSONReader.skipValue();
				}
			}
			JSONReader.endObject();			
		}
		JSONReader.endArray();
		return tagsOrCategories;
	}
//	*/
	
	private static ArrayList<String> getDeclaredFieldNames(Object obj) {
		ArrayList<String> names = new ArrayList<String>();
		for (Field field : obj.getClass().getDeclaredFields()) {
			names.add(field.getName());
		}
		return names;
		
	}
	
	private static SearchPage getSearchPage(String query) throws IOException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		String url = URLParse.URLforSearchQuery(query);
		JsonReader JSONReader = readUrl(url);
		
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
		int total = parsed.get("count_total").getAsInt();
		int pages = parsed.get("pages").getAsInt();
		System.out.println(total);
		System.out.println(pages);
		
		ArrayList<Post> posts = new ArrayList<Post>();
		JsonArray postArray = parsed.get("posts").getAsJsonArray();
		for (JsonElement post : postArray) {
			JsonObject obj = post.getAsJsonObject();
			Post newpost = parsePostJsonObject(obj);
			System.out.println(newpost);
			posts.add(newpost);
		}
		
		SearchPage searchPage = new SearchPage(query, total, posts);
		System.out.println(searchPage);
		return searchPage;
	}
	
	static Post parsePostJsonObject(JsonObject obj) {
		String url = obj.get("url").getAsString();
		String title = Jsoup.parse(obj.get("title").getAsString()).text();
		String content = obj.get("content").getAsString();
		String excerpt = obj.get("excerpt").getAsString();
		String date = obj.get("date").getAsString();
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<String> tags = new ArrayList<String>();
		JsonArray categoryArray = obj.get("categories").getAsJsonArray();
		JsonArray tagArray = obj.get("tags").getAsJsonArray();
		for (JsonElement category : categoryArray) {
			categories.add(category.getAsJsonObject().get("title").getAsString());
		}
		for (JsonElement tag : tagArray) {
			tags.add(tag.getAsJsonObject().get("title").getAsString());
		}
		JsonObject authorObject = obj.get("author").getAsJsonObject();
		Author author = parseAuthorJsonObject(authorObject);
		Post post = new Post(title, content, url, excerpt, date, categories, tags, author);
		return post;
	}
	
	static Author parseAuthorJsonObject(JsonObject obj) {
		String name = obj.get("name").getAsString();
		String position = obj.get("nickname").getAsString();
		String email = "";
		String description = obj.get("description").getAsString();
		int numposts = 0;
		Author author = new Author(name, position, email, description, numposts);
		return author;
	}
	
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		/*
		System.out.println(readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung"));
	    JsonObject json = readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung");
//	    System.out.println(getAuthorPosts(json));*/
//		String s = "Goose Bumps &#8211; My Hair&#8217;s Doing What?";
//		System.out.println(Jsoup.parse(s).text());
		getAuthorPosts("http://morningsignout.com/?json=get_author_posts&author_meta=email&author_slug=willycheung");
//		getSearchPage("DNA");
	}
}
