import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Eratostene {

	ArrayList<Integer> lista= new ArrayList<Integer>();//lista de numere
	ArrayList<Integer> marks= new ArrayList<Integer>();//lista de marcaje
	int numar;//numarul pana la care se vor determina numerele
	ArrayList<Integer> prime= new ArrayList<Integer>();//lista de numere prime
	
	Eratostene(int n) {
		numar=n;
	}
	
	public void initiate() {//initializeaza lista de numere cu numere de la 2 la n inclusiv
		for(int i=0;i<numar-2;i++) {//avem n-2 elemente in lista
			lista.add(i+2);
		}
	}
	
	public void initiateMarks() {//initializam lista de marcaje
		for(int i=0;i<numar-2;i++) {//pentru n-1 numere 
			marks.add(0);//cu 0; 0-numar nemarcat, 1-numar marcat
		}
	}
	
	public void startDet() {
		for(int i=0;i<numar-2;i++) {//pentru fiecare numar din lista
			if(prim(lista.get(i))==true && marks.get(i)==0){//daca numarul este prim si nu a fost marcat cu 1
				prime.add(lista.get(i));//adauga numarul in lista de numere prime
				startMark(lista.get(i));//incepe marcajul multiplilor
			}
		}
	}
	
	public boolean prim(int nr) {//verifica daca numarul este prim 
		boolean yes=true;//presupunem numarul prim
		
		if(nr<=1) {//daca este cel mult 1
			yes=false;//nu este prim
		}
		
		for(int i=2;i<nr;i++) {//cautam un numar care sa se imparta la numarul pe care il verificam
			if(nr%i==0) {//daca se imparte
				yes=false;//nu este prim
			}
		}
		
		return yes;//returnam valoarea
	}
	
	public void startMark(int prim) {//marcam multipli numarului prim gasit
		for(int i=0;i<numar-2;i++) {//pentru toate numerele din lista
			if(lista.get(i)%prim==0) {//daca numarul din lista se imparte la numarul prim gasit
				marks.set(i, 1);//seteaza mark la pozitia numarului cu 1;
			}
		}
	}
	
	public void printPrime() {
		System.out.println("Numerele prime gasite pentru numarul "+numar+":");
		for(int i=0;i<prime.size();i++) {//printeaza lista de numere prime
			System.out.print(prime.get(i)+" ");
		}
		System.out.println("\n");
	}
	
	public void printPrimeFile(int nrFile, long time) {
		
		File f2=new File("Output"+nrFile+".txt");
		
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(f2));
			
			bw.write("Numerele prime gasite pentru numarul "+String.valueOf(numar)+":");
			bw.newLine();
			//se scrie lista de numere prime in fisierul aferent inputului n
			for(int i=0;i<prime.size();i++) {
				bw.write(String.valueOf(prime.get(i))+" ");
			}
			bw.write("\nTimpul total de executie: "+String.valueOf(time)+" nanosecunde");
			bw.write("\nNumarul de numere gasite: "+String.valueOf(prime.size()));
			bw.close();//la finalul fisieruli este scris timpul de executie pentru numarul curent
			//si cate numere prime s-au gasit
			}catch(IOException e) {
				e.printStackTrace();
			}
	}
	
}
