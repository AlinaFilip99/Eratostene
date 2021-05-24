import java.util.ArrayList;

public class Eratostene {
	
	ArrayList<Boolean> marks= new ArrayList<Boolean>();//lista de marcaje
	int numar;//numarul pana la care se vor determina numerele
	
	Eratostene(int n) {
		numar=n;
	}
	
	public void initiate() {//initializeaza lista de numere cu numere de la 2 la n inclusiv
		for(int i=0;i<numar;i++) {//avem n-2 elemente in lista
			marks.add(true);
		}
	}
	
	public void startDet() {
		for(int i=2;i<numar;i++) {//pentru fiecare numar din lista
			if(marks.get(i)==true){//daca numarul este prim si nu a fost marcat cu 1
				startMark(i);//incepe marcajul multiplilor
			}
		}
	}
	
	public void startMark(int prim) {//marcam multipli numarului prim gasit
		for(int i=prim+1;i<numar;i++) {//pentru toate numerele din lista 
			if(i%prim==0) {
				marks.set(i, false);//setam la fals
			}
		}
	}
	
	public void printPrime() {
		System.out.println("Numerele prime gasite pentru numarul "+numar+":");
		for(int i=2;i<numar;i++) {//printeaza lista de numere prime
			if(marks.get(i)==true) {
				System.out.print(i+" ");
			}
		}
		System.out.println("\n");
	}
	
}
