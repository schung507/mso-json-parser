public class URLParse {
	public static final String BASE_URL = "http://morningsignout.com/?json=";
	
	public static String URLforAuthorPost(String author_slug){
		return BASE_URL + "get_author_posts&author_meta=email&date_format=F-j,Y&author_slug=" + author_slug;
	}
	
	public static String URLforSinglePage(String post_slug){
		return BASE_URL + "get_post&date_format=F-j,Y&post_slug=" + post_slug;
	}
	
	public static String URLforDatePosts(String date){
		//modify date string if needed?
		
		return BASE_URL + "get_date_posts&date_format=F-j,Y&date=" + date;
	}
	
	public static String URLforTagPost(String tag){
		return BASE_URL + "get_tag_posts&date_format=F-j,Y&tag_slug=" + tag;
	}
	
	public static String URLforCategoryPost(String category){
		return BASE_URL + "get_category_posts&date_format=F-j,Y&category_slug=" + category;
	}
	
	public static String URLforSearchQuery(String query){
		//if string is more than one word
		query.replaceAll(" ", "+");
		return BASE_URL + "get_search_results&date_format=F-j,Y&search=" + query;
	}

}
