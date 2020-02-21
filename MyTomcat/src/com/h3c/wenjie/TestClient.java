package com.h3c.wenjie;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) throws IOException {

        Socket socket= null;
        OutputStream ops = null;
        InputStream is = null;

        try {
            socket = new Socket("www.itcast.cn",80);
            ops = socket.getOutputStream();
            is = socket.getInputStream();

            ops.write("GET /subject/about/index.html HTTP/1.1\n".getBytes());
            ops.write("Host: www.itcast.cn\n".getBytes());

            //ops.write("Connection: keep-alive\n".getBytes());
            ops.write("\n".getBytes());


            int i = is.read();
            while (i != -1){
                System.out.print((char)i);
                i = is.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(null != ops){
                ops.close();
                ops = null;
            }

            if(null != is){
                is.close();
                is = null;
            }

            if(null != socket){
                socket.close();
                socket = null;
            }

        }

    }
}
