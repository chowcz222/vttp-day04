package fileapp;

import java.net.Socket;
import java.io.*;

public class FileClient {

     public static void main(String[] args) throws IOException {

        int port = 3000;
        if (args.length > 0)
           port = Integer.parseInt(args[0]);

        File file = new File(args[1]);

        System.out.println("Connecting to the server");
        Socket sock = new Socket("localhost", port);
        System.out.println("Connected!");
        
        InputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        String fileName = file.getName();
        Long fileSize = file.length();

        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        dos.writeUTF(fileName);
        dos.writeLong(fileSize);

        byte[] buff = new byte[4*1024];
        int bytesRead = 0;
        int sendBytes = 0;
        int idx = 0;

        while ((bytesRead = bis.read(buff)) > 0) {
         dos.write(buff, 0, bytesRead);
         sendBytes += bytesRead;
         System.out.printf("%d > %d Send %d of %d\n", idx, bytesRead, sendBytes, fileSize);
         idx++;
        }

        dos.flush();
        os.flush();
        dos.close();
        os.close();
        sock.close();
        bis.close();


     }
    
}
