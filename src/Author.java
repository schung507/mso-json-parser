

public class Author {
	String name, 
		position, 
		email, 
		description;
		
	public Author(String _name, String _position, String _email, String _description){
		name = _name;
		position = _position;
		email = _email;
		description = _description;
	}

	public void printAuthor(){
		System.out.println("name: " + name + "\nposition: " + position + "\nemail: " + email + "\ndescription: " + description + "\n\n");
	}

}
