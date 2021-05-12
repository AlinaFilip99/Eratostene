
public class Main {
	static final int NPTHREADS = 10;
	
	static Producator pThreads[] = new Producator[NPTHREADS];
	static Eratostene buffer = new Eratostene(77536);
	public static void main(String[] args) {
	int i;
	long startTime = System.nanoTime();
	buffer.initiate();
	buffer.initiateMarks();
	System.out.println("Producator - Consumator started");
	for (i=0;i<NPTHREADS;i++) {
	pThreads[i] = new Producator(buffer);
	pThreads[i].start();
	}
		for (i=0;i<NPTHREADS;i++) {
		try { pThreads[i].join(); }
		catch(InterruptedException e) {}
		}
		long stopTime = System.nanoTime();
		//buffer.printPrime();
		System.out.println("Producator - Consumator finished --- time: "+(stopTime-startTime)+"ns");
		}

}
