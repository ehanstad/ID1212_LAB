import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Server{

    public static void main(String[] args) {

        SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
        HttpsURLConnection.setDefaultSSLSocketFactory(sf);
        SSLSocket socket = null;
        String host = "smtp.kth.se";

        try{
            socket = (SSLSocket)sf.createSocket(host,587);
            String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
            socket.setEnabledCipherSuites(cipher);

            PrintWriter writer = null;
            BufferedReader reader = null;

            socket.startHandshake();
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String str;

            while( (str=reader.readLine()) != null)
                System.out.println(str);
            writer.print("HELO");
            while( (str=reader.readLine()) != null)
                System.out.println(str);
            writer.close();
            reader.close();
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
