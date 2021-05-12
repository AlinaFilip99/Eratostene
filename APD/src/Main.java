import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int numar, i=0;
		
		try {
			Scanner scan = new Scanner(new File("Input.txt"));//fisierul cu numere generate random
			
			while(scan.hasNextInt()) {//cat timp se gasesc numere in fisier
				
				numar=scan.nextInt();//citim numerele
				
				if(numar<=1) {//daca numarul pana la care trebuie sa determinam numerele prime este <=1
					System.out.println("Ati dat un numar <=1, deci nu sunt numere prime");
				} else {
					Eratostene e=new Eratostene(numar);
					long startTime = System.nanoTime();//citim timpul la care a inceput executia algoritmului
					e.initiate();//initializam lista de numere de la 2 la n inclusiv
					e.initiateMarks();//initializam marcajele pentru fiecare dintre numerele din lista
					e.startDet();//incepem determinarea numerelor prime
					long stopTime = System.nanoTime();//citim timpul la care a incetat executia algoritmului
					//e.printPrime();//printeaza numerele prime
					e.printPrimeFile(i,stopTime-startTime);//scrie numerele prime in fisier
				}
				i++;//index pentru fisierele output
				
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		
		
		
	}
	
}
