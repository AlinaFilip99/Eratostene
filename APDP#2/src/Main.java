
public class Main {
	static final int NPTHREADS = 10;
	static final int NR = 4000000;
	
	static Producator pThreads[] = new Producator[NPTHREADS];
	static Eratostene buffer = new Eratostene(NR);
	
	public static void main(String[] args) {
		int i, x, low, high;
		long startTime = System.nanoTime();
		
		buffer.initiate();
		
		for (i=0;i<NPTHREADS;i++) {
			
			x = NR / NPTHREADS;
			int dif = ((NPTHREADS - NR % NPTHREADS) * (x + 1)) - ((NPTHREADS - NR % NPTHREADS) * x);
			if (NR % NPTHREADS != 0) {
				if (i >= NPTHREADS - NR % NPTHREADS) {
					low = i * (x + 1) - dif;
					high = low + (x + 1);
				}else { //fiecare proces calculeaza low and high
					low = i * x;
					high = low + x;
				}
			}else {
				low = i * x;
				high = low + x;
			}
			if(low<=1) {
				low=2;
			}
			//System.out.println("For process "+i+" low: "+low+" high: "+high);
			
			pThreads[i] = new Producator(buffer, low, high);
			//pThreads[i].start();
		}
		for(int j=2;j<NR;j++) {
			if(buffer.getMark(j)==true) {
				//System.out.println("Prim gasit: "+j);
				for(int k=0;k<NPTHREADS;k++) {
					//System.out.println("Prime to "+k+" :"+i);
					pThreads[k].run(j);
				}
				for (int k=0;k<NPTHREADS;k++) {
					try { 
						pThreads[k].join(); 
					}catch(InterruptedException e) {}
				}
			}
		}
		
		/*for (i=0;i<NPTHREADS;i++) {
			try { 
				pThreads[i].join(); 
			}catch(InterruptedException e) {}
		}*/
		
		long stopTime = System.nanoTime();
		//buffer.printPrime();
		System.out.println("Producator - Consumator finished --- time: "+(stopTime-startTime)+"ns");
	}

}
