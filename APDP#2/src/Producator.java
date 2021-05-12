
public class Producator extends Thread{
	Eratostene buffer;
	public Producator(Eratostene b) {
		this.buffer=b;
	}
	public void run() {
		boolean a=true;
		while(a==true) {
			a=buffer.append();
		}
		//System.out.println("Producer finished!");
	}

}
