import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

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
		JSONReader.beginObject();
		Author author = new Author();
		ArrayList<Post> posts = new ArrayList<Post>();
		
		
		while (JSONReader.hasNext()) {
			
			String key = JSONReader.nextName();
//			System.out.println(key);
			
			if(key.equals("author")) {
		        author = parseAuthor(JSONReader); 
		     }
			else if(key.equals("posts")){
				
				JSONReader.beginArray();
				while(JSONReader.hasNext()){
					posts.add(parsePost(JSONReader));
					
				}
				JSONReader.endArray();
			}
			else{
				JSONReader.skipValue();
			}

		}
		
		AuthorPage authorPage = new AuthorPage(author, posts);
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
		//System.out.println(author);
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
					val = JSONReader.nextString();
				} else if (fieldTypeName.equals("ArrayList")) { // key is "categories" or "tags"
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
		//System.out.println(post);
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
					String title = JSONReader.nextString();
					tagsOrCategories.add(title);
				}
				else{
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
		
		JSONReader.beginObject();
		
		ArrayList<Post> posts = new ArrayList<Post>();
		
		
		while (JSONReader.hasNext()) {
			
			String key = JSONReader.nextName();
//			System.out.println(key);
			
			if(key.equals("posts")){
				
				JSONReader.beginArray();
				while(JSONReader.hasNext()){
					posts.add(parsePost(JSONReader));
					
				}
				JSONReader.endArray();
			}
			else{
				JSONReader.skipValue();
			}

		}
		
		SearchPage searchPage = new SearchPage(query, posts);
		return searchPage;
		
		
		
		
	}
	
	
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		
		SearchPage health = getSearchPage("health");
		System.out.println(health);
	}
}
