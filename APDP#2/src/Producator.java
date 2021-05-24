
public class Producator extends Thread{
	Eratostene buffer;
	int low, high;
	public Producator(Eratostene b, int l, int h) {
		this.buffer=b;
		this.low=l;
		this.high=h;
	}
	public void run(int prime) {
		//System.out.println("Producator marks:"+prime);
		for(int i=low;i<high;i++) {
			if(i%prime==0 && i!=prime) {
				//System.out.println("Producator a marcat: "+i);
				buffer.setMark(i);
			}
		}
		//System.out.println("Producer finished!");
	}

}
