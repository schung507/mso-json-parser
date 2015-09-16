import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.jsoup.Jsoup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class DataParse {
	
	//returns json reader with URL as input
	static JsonReader readUrl(String urlString) throws IOException {
		
	    URL url= new URL(urlString); //just a string
	    Reader jsonReader = new InputStreamReader(url.openStream());
	    JsonReader JSONReader = new JsonReader(jsonReader);
	    return JSONReader;
	    
	} 
	

	// Get list of tag names / category names
	static ArrayList<String> parseTagOrCategoryJsonArray(JsonArray jsonArray) {
		ArrayList<String> tagsOrCategories = new ArrayList<String>();
		for (JsonElement item : jsonArray) {
			JsonObject obj = item.getAsJsonObject();
			String title = parseJsonObject(obj, "title");
			tagsOrCategories.add(title);
		}
		return tagsOrCategories;
	}
	
	// Get list of posts
	static ArrayList<Post> parsePostJsonArray(JsonArray postArray) {
		ArrayList<Post> posts = new ArrayList<Post>();
		for (JsonElement postElem : postArray) {
			JsonObject obj = postElem.getAsJsonObject();
//			for (Entry<String, JsonElement> key : obj.entrySet()) {
//				System.out.println(key.getKey());
//			}
//			System.out.println();
//			System.out.println(obj.get("thumbnail"));
//			System.out.println()
			Post post = parsePostJsonObject(obj);
			posts.add(post);
		}
		return posts;
	}
	
	//Loads all pages and returns full post array
	static ArrayList<Post> parsePages(int pages, String url, ArrayList<Post> posts, JsonReader JSONReader, JsonParser parser) throws IOException{
		
		int pageCounter = 1;
		
		while (pageCounter <= pages) {
			System.out.println(pageCounter);
			JSONReader = readUrl(String.format("%s&page=%d", url, pageCounter));
			JsonObject parsedPage = parser.parse(JSONReader).getAsJsonObject();
			JsonArray postArray = parsedPage.get("posts").getAsJsonArray();
			
			ArrayList<Post> pagePosts = parsePostJsonArray(postArray);
			posts.addAll(pagePosts);
			pageCounter += 1;
		}
		return posts;
	}
	
	static Post parsePostJsonObject(JsonObject obj) {
		
		String url = parseJsonObject(obj, "url");
		String title = parseJsonObject(obj, "title");
		String content = parseJsonObject(obj, "content");
		String excerpt = parseJsonObject(obj, "excerpt");
		String date = parseJsonObject(obj, "date").replace('-',' ');
		String thumbnailUrl = parseJsonObject(obj, "thumbnail");

		JsonObject authorObject = obj.get("author").getAsJsonObject();
		Author author = parseAuthorJsonObject(authorObject);
		
		JsonArray categoryArray = obj.get("categories").getAsJsonArray();
		JsonArray tagArray = obj.get("tags").getAsJsonArray();

		ArrayList<String> categories = parseTagOrCategoryJsonArray(categoryArray);
		ArrayList<String> tags = parseTagOrCategoryJsonArray(tagArray);
		
		Post post = new Post(title, content, url, excerpt, date, thumbnailUrl, categories, tags, author);
		return post;
	}
	
	static Author parseAuthorJsonObject(JsonObject obj) {
		String name = parseJsonObject(obj, "name");
		String position = parseJsonObject(obj, "nickname");
		String email = parseJsonObject(obj, "email");
		String description = parseJsonObject(obj, "description");
		int numposts = 0;
		Author author = new Author(name, position, email, description, numposts);
		return author;
	}
	
	private static String parseJsonObject(JsonObject obj, String field) {
		JsonElement elem = obj.get(field);
		try {
			String str = "";
			if(!elem.isJsonNull()){
				str = elem.getAsString();
			}
			return Jsoup.parse(str).text();
		} catch (NullPointerException e) {
			return null;
		}
	}

	//AUTHOR PAGE
	static AuthorPage getAuthorPosts(String author_slug) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		String url = URLParse.URLforAuthorPost(author_slug);
		JsonReader JSONReader = readUrl(url);
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
//		for (Entry<String, JsonElement> key : parsed.entrySet()) {
//			System.out.println(key.getKey());
//		}
		
		int pages = parsed.get("pages").getAsInt();

		JsonObject authorObject = parsed.get("author").getAsJsonObject();
		Author author = parseAuthorJsonObject(authorObject);
		ArrayList<Post> posts = new ArrayList<Post>();
		
		int pageCounter = 1;
		while (pageCounter <= pages) {
			JSONReader = readUrl(String.format("%s&page=%d", url, pageCounter));
			JsonObject parsedPage = parser.parse(JSONReader).getAsJsonObject();
			JsonArray postArray = parsedPage.get("posts").getAsJsonArray();

			ArrayList<Post> pagePosts = parsePostJsonArray(postArray);
			posts.addAll(pagePosts);
			pageCounter += 1;
		}
		
		int count = posts.size();
		author.setNumposts(count);
		AuthorPage authorPage = new AuthorPage(author, posts);
		System.out.println(authorPage);
		return authorPage;
	}
	
	//SEARCH PAGE
	static SearchPage getSearchPage(String query) throws IOException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		String url = URLParse.URLforSearchQuery(query);
		JsonReader JSONReader = readUrl(url);
		
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
		int total = parsed.get("count_total").getAsInt();
		int pages = parsed.get("pages").getAsInt();
		//System.out.println(total);
		//System.out.println(pages);
		
		ArrayList<Post> posts = new ArrayList<Post>();
		
		posts = parsePages(pages, url, posts, JSONReader, parser);
		
		SearchPage searchPage = new SearchPage(query, total, posts);
		System.out.println(searchPage);
		return searchPage;
	}
	
	//TAG PAGE
	static TagPage getTagPage(String tag) throws IOException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		String url = URLParse.URLforTagPost(tag);
		JsonReader JSONReader = readUrl(url);
		
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
		int pages = parsed.get("pages").getAsInt();
		//System.out.println(pages);
		
		ArrayList<Post> posts = new ArrayList<Post>();
		
		posts = parsePages(pages, url, posts, JSONReader, parser);
		
		TagPage TagPage = new TagPage(tag, posts);
		System.out.println(TagPage);
		return TagPage;
	}
	
	//DATE PAGE
	static DatePage getDatePage(String date)throws IOException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		String url = URLParse.URLforDatePosts(date);
		JsonReader JSONReader = readUrl(url);
		
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
		int pages = parsed.get("pages").getAsInt();
		//System.out.println(pages);
		
		ArrayList<Post> posts = new ArrayList<Post>();
		
		posts = parsePages(pages, url, posts, JSONReader, parser);
		
		DatePage datePage = new DatePage(date, posts);
		System.out.println(datePage);
		return datePage;
	}

	//ARTICLE PAGE
	static ArticlePage getArticlePage(String post_slug)throws IOException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		String url = URLParse.URLforSinglePage(post_slug);
		JsonReader JSONReader = readUrl(url);
		JsonParser parser = new JsonParser();
		JsonObject parsed = parser.parse(JSONReader).getAsJsonObject();
		Post post = parsePostJsonObject(parsed.get("post").getAsJsonObject());
		String older = parseJsonObject(parsed, "previous_url");
		String newer = parseJsonObject(parsed, "next_url");
		
		ArticlePage articlePage = new ArticlePage(post, older, newer);
		System.out.println(articlePage);
		return articlePage;
	}
	
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		/*
		System.out.println(readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung"));
	    JsonObject json = readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung");
//	    System.out.println(getAuthorPosts(json));*/

//		getAuthorPosts("willycheung");
//		getSearchPage("DNA");
//		getTagPage("drug-resistant");
//		getArticlePage("the-devastating-effect-of-all-nighters-how-one-night-alters-your-genes");
		getDatePage("2015/08");
	}
}
