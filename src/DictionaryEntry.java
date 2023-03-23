import java.util.Comparator;
public class DictionaryEntry implements Comparable {

    // instance variables
    private String wordOrPhrase;
	private String definition;
    private String[] arr = new String[2];

    // constructors
    public DictionaryEntry() {}

    public DictionaryEntry(String wop, String def) {
        this.wordOrPhrase = wop;
		this.definition = def;
    }

	// returns a String array with the wordOrPhrase at location zero and definition 
	// at location 1
    public String [] getData() {
        arr[0] = wordOrPhrase;
        arr[1] = definition;
        return arr;
    }

	// accepts a String array with the wordOrPhrase at location zero and the definition
	// of the wordOrPhrase at location 1 and sets the variables accordingly
    public void setData(String [] data) {
        arr[0] = data[0];
        arr[1] = data[1];
    }

    public String getWordOrPhrase() {
        return wordOrPhrase;
    }
    public void setWordOrPhrase(String wordOrPhrase) {
        this.wordOrPhrase = wordOrPhrase;
    }

    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }
	// Define a Comparator method that can be used to sort an ArrayList of Nodes in Lexically
	// Ascending order - that is, from A to Z, according to the wordOrPhrase
	// Note that you must keep the exact method signature provided here
	
	@Override
	public int compareTo(Object o) {
        return (this.wordOrPhrase.compareTo(((DictionaryEntry) o).wordOrPhrase));
    }

}