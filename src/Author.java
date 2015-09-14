

public class Author {
	String name, 
		nickname, 
		email, 
		description;
	
	int numposts;
	
	public Author() {
		
	}
		
	public Author(String _name, String _position, String _email, String _description){
		name = _name;
		nickname = _position;
		email = _email;
		description = _description;
	}

	public Author(Author oldAuthor){
		this.name = oldAuthor.name;
		this.nickname = oldAuthor.nickname;
		this.email = oldAuthor.email;
		this.description = oldAuthor.description;
	}

	
	@Override
	public String toString() {
		String info = String.format("name: %s\nposition: %s\nemail: %s\ndescription: %s\n\n", name, nickname, email, description);
		return info;
	}

}
