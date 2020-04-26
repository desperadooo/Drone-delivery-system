import java.io.FileNotFoundException;
import java.io.PrintStream;
public class Out {

    public Out() {
        try {
            //Output the information to file
            PrintStream print = new PrintStream("C:\\test.txt");  //写好输出位置文件；Ecrire l'emplacement du fichier de sortie
            System.setOut(print);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

