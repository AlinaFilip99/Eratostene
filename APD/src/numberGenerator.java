import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class numberGenerator {

	public static void main(String[] args) {
		Random r = new Random();
		int nr;
		File f2=new File("Input.txt");//fisierul input
		
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(f2));
			
			for(int i=0;i<4;i++) {
				
				nr=r.nextInt(10000);//scriem 4 numere in intevalul 0-10000
				
				bw.write(String.valueOf(nr));
				bw.newLine();
			}
			
			for(int i=0;i<4;i++) {
				
				nr=r.nextInt(100000-10000)+10000;//scriem 4 numere in intervalul 10000-100000
				
				bw.write(String.valueOf(nr));
				bw.newLine();
			}
			
			for(int i=0;i<4;i++) {
				
				nr=r.nextInt(500000-100000)+100000;//scriem 4 numere in intervalul 100000-500000
				
				bw.write(String.valueOf(nr));
				bw.newLine();
			}
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

}
