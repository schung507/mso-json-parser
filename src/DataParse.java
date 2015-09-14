import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class DataParse {
	
	//returns json reader with URL as input
	public static JsonReader readUrl(String urlString) throws IOException {
		// TODO Auto-generated method stub
	    URL url= new URL(urlString); //just a string
	    Reader jsonReader = new InputStreamReader(url.openStream());
	    JsonReader JSONReader = new JsonReader(jsonReader);
	    return JSONReader;
	    
	    /*
	    // Connect to the URL using java's native library
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();

	    // Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
		return rootobj;*/
	} 
	

	public static Author getAuthorPosts(String url) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		JsonReader JSONReader = readUrl(url);
		JSONReader.beginObject();
		Author author = null;
		Post[] posts = null;
		int count;
		int pages;
		
		while (JSONReader.hasNext()) {
			
			String key = JSONReader.nextName();
//			System.out.println(key);
			
			if (key.equals("count")){
				count = JSONReader.nextInt();
			}
			else if (key.equals("pages")){
				pages = JSONReader.nextInt();
			}
			else if(key.equals("author")) {
		        author = parseAuthor(JSONReader); 
		     }
			else if(key.equals("posts")){
				
				JSONReader.beginArray();
				while(JSONReader.hasNext()){
					parsePost(JSONReader);
					
				}
				JSONReader.endArray();
			}
			else{
				JSONReader.skipValue();
			}

		}
		return author;
		
	}

	public static Author parseAuthor(JsonReader JSONReader) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		JSONReader.beginObject();
		Author author = new Author();
		
		while (JSONReader.hasNext()) {
			String key = JSONReader.nextName();
			if (getDeclaredFieldNames(author).contains(key)) {
				String val = JSONReader.nextString();
				author.getClass().getDeclaredField(key).set(author, val);
			} else {
				JSONReader.skipValue();
			}
		}
		JSONReader.endObject();
//		System.out.println(author);
		return author;
		
	}
	
	public static Post parsePost(JsonReader JSONReader) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		
		Post post = new Post();
		JSONReader.beginObject();

		while(JSONReader.hasNext()){
			String key = JSONReader.nextName();
			if (getDeclaredFieldNames(post).contains(key)) {
				String val = JSONReader.nextString();
				post.getClass().getDeclaredField(key).set(post, val);
			} else if (key.equals("categories")) {
				ArrayList<String> categories = parseTagsOrCategories(JSONReader);
//				System.out.println(categories);
				post.getClass().getDeclaredField(key).set(post, categories);
			} else if (key.equals("tags")) {
				ArrayList<String> tags = parseTagsOrCategories(JSONReader);
//				System.out.println(tags);
				post.getClass().getDeclaredField(key).set(post, tags);
			} else {
				JSONReader.skipValue();
			}
		}
		JSONReader.endObject();
		System.out.println(post);
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
//		System.out.println(tagsOrCategories);
		return tagsOrCategories;
	}
//	*/
	
	private static ArrayList<String> getDeclaredFieldNames(Object obj) {
		ArrayList<String> names = new ArrayList<String>();
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.getType().getSimpleName().equals("String")) {
				names.add(field.getName());
			}
		}
		return names;
		
	}
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		/*
		System.out.println(readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung"));
	    JsonObject json = readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung");
	    System.out.println(getAuthorPosts(json));*/
		getAuthorPosts("http://morningsignout.com/?json=get_author_posts&author_meta=email&author_slug=willycheung");
	}
}
