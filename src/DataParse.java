import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

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
	

	public static Author getAuthorPosts(String url) throws IOException{
		JsonReader JSONReader = readUrl(url);
		JSONReader.beginObject();
		Author author = null;
		Post[] posts = null;
		int count;
		int pages;
		
		while (JSONReader.hasNext()) {
			
			String key = JSONReader.nextName(); 
			
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

	public static Author parseAuthor(JsonReader JSONReader) throws IOException{
		
		JSONReader.beginObject();
		
		String name = null, 
			position = null, 
			email = null, 
			description = null;
		
		while (JSONReader.hasNext()) {
			
			String key = JSONReader.nextName(); 
			
			if (key.equals("name")){
				name = JSONReader.nextString();
			}
			else if (key.equals("nickname")){
				position = JSONReader.nextString();
			}
			else if(key.equals("description")) {
		        description = JSONReader.nextString(); 
		     }
			else if(key.equals("email")){
				email = JSONReader.nextString();
			}
			else{
				JSONReader.skipValue();
			}

		}
		JSONReader.endObject();
		Author author = new Author(name, position, email,description);
		System.out.println(author);
		return author;
		
	}
	
	public static Post parsePost(JsonReader JSONReader) throws IOException{
		String title= null,
			content = null,
			URL = null,
			excerpt = null,
			date = null;
		String[] categories = null;
		
			JSONReader.beginObject();

			while(JSONReader.hasNext()){
				
				String key = JSONReader.nextName(); 
				if (key.equals("title")){
					title = JSONReader.nextString();
				}
				else if (key.equals("content")){
					content = JSONReader.nextString();
				}
				else if(key.equals("url")) {
			        URL = JSONReader.nextString(); 
			     }
				else if(key.equals("excerpt")){
					excerpt = JSONReader.nextString();
				}
				else if(key.equals("date")){
					date = JSONReader.nextString();
				}
				else{
					JSONReader.skipValue();
				}
			}
		JSONReader.endObject();	
		Post post = new Post(title, content, URL, excerpt, date);
		System.out.println(post);
		return post;
	}
	/*
	public static String[] parseTagsOrCategories(JsonReader JSONReader) throws IOException{
		ArrayList<String> tagsOrCategories = new ArrayList<String>();
		String[] newTagsOrCategories;
		
		JSONReader.beginArray();
		
		while(JSONReader.hasNext()){
			JSONReader.beginObject();
			while(JSONReader.hasNext()){
				String key = JSONReader.nextName(); 
				if(key.equals("title")){
					System.out.println(JSONReader.nextString());
					tagsOrCategories.add(JSONReader.nextString());
				}
				else{
					System.out.println("ugh");
				}
			}
			JSONReader.endObject();			
		}
		JSONReader.endArray();
		newTagsOrCategories = tagsOrCategories.toArray(new String[tagsOrCategories.size()]);
		return newTagsOrCategories;
	}*/
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		System.out.println(readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung"));
	    JsonObject json = readUrl("http://morningsignout.com/?json=get_author_posts&author_slug=willycheung");
	    System.out.println(getAuthorPosts(json));*/
		getAuthorPosts("http://morningsignout.com/?json=get_author_posts&author_meta=email&author_slug=willycheung");
	}

}
