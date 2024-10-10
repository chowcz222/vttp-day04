package fileapp;


import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class FileServer {

     public static void main(String[] args) throws IOException {

        int port = 3000;
        if (args.length > 0)
         port = Integer.parseInt(args[0]);

      // Create a server port, TCP
        ServerSocket server = new ServerSocket(port);

        while (true) {
            System.out.printf("Waiting for connection on port %d\n", port);

            Socket sock = server.accept();

            System.out.println("Got a new connection");
            
            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            String fileName = dis.readUTF();
            Long fileSize = dis.readLong();
            System.out.printf("Receiving file %s with a size of %d", fileName, fileSize);

            OutputStream os = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(os);

    

            byte[] buff = new byte[4 * 1024];
            int bytesRead = 0;
            int bytesRecv = 0;
            int idx = 0;

            while (bytesRecv < fileSize) {
                // Number of bytes read
                bytesRead = dis.read(buff);
                bytesRecv += bytesRead;
                
                // Write to local file
                bos.write(buff, 0, bytesRead);
                
                System.out.printf("%d> %d Recv %d of %d\n", idx, bytesRead, bytesRecv, fileSize);
                idx++;
             }
            bos.close();
            server.close();
       
        }

    
    }
    
}
