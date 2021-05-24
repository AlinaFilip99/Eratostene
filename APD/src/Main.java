
public class Main {

	public static void main(String[] args) {
		
		int numar=5000000;
		if(numar<=1) {//daca numarul pana la care trebuie sa determinam numerele prime este <=1
			System.out.println("Ati dat un numar <=1, deci nu sunt numere prime");
		} else {
			Eratostene e=new Eratostene(numar);
			long startTime = System.nanoTime();//citim timpul la care a inceput executia algoritmului
			e.initiate();//initializam lista de numere de la 2 la n inclusiv
			e.startDet();//incepem determinarea numerelor prime
			long stopTime = System.nanoTime();//citim timpul la care a incetat executia algoritmului
			//e.printPrime();//printeaza numerele prime
			//e.printPrimeFile(i,stopTime-startTime);//scrie numerele prime in fisier
			System.out.println("Timp: "+(stopTime-startTime)+" ns");
		}
	}
	
}
