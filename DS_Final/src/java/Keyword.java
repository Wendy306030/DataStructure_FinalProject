public class Keyword {
	public String name;
    public int count;
    public int weight;
    
    public Keyword(String name, int weight){
		this.name = name;
		this.count = 0;
		this.weight = weight;
    }
    
    @Override
    public String toString(){
    	return "["+name+","+count+","+weight+"]";
    }

    public int getCount()
    {
    	return count;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public int getWeight()
    {
    	return weight;
    }
    
    public void setCount(int count) {
    	this.count=count;
    }
}