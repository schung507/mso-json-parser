import java.util.ArrayList;


public class ArticlePage {
	Post post;
	String older;
	String newer;
	
	public ArticlePage( Post _post, String _older, String _newer){
		post = _post;
		older = _older;
		newer = _newer;
	}
	
	@Override
	public String toString(){
		String info = String.format("%s\nOlder: %s\nNewer: %s\n\n", post, older, newer);
		return info;
	}
	
}
