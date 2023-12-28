
public class Link {
	public String title;
	public String url;
	public int score;
	
	public Link(String title, String url) {
		this.title=title;
		this.url=url;
		score=0;
	}
	
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
	
	public void setScore(int score) {
		this.score+=score;
	}
	
	

}
