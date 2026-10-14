import java.io.*;
import java.net.*;
public class URLTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.w3.org");
            BufferedReader in =
                    new BufferedReader (new InputStreamReader(url.openStream()));
            boolean more = true;
            while (more) {
                String line = in.readLine();
                if (line == null)
                    more = false;
                else
                    System.out.println(line);
            }
        } catch (IOException e) { System.out.println("Error"+e); }
    }
}