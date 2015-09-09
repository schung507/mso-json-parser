public class URLParse {
	public static final String BASE_URL = "http://morningsignout.com/?json=";
	
	public static String URLforAuthorPost(String author_slug){
		return BASE_URL + "get_author_posts&author_meta=email&author_slug=" + author_slug;
	}
	
	public static String URLforSinglePage(String post_slug){
		return BASE_URL + "get_post&post_slug=" + post_slug;
	}
	
	public static String URLforDatePosts(String date){
		//modify date string if needed?
		
		return BASE_URL + "get_date_posts&date=" + date;
	}
	
	public static String URLforTagPost(String tag){
		return BASE_URL + "get_tag_posts&tag_slug=" + tag;
	}
	
	public static String URLforCategoryPost(String category){
		return BASE_URL + "get_category_posts&category_slug=" + category;
	}
	
	public static String URLforSearchQuery(String query){
		//if string is more than one word
		query.replaceAll(" ", "+");
		return BASE_URL + "get_search_results&search=" + query;
	}

}
