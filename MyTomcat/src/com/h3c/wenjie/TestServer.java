package com.h3c.wenjie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        OutputStream ops = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8080);
            while (true){
                socket = serverSocket.accept();
                ops = socket.getOutputStream();

                ops.write("HTTP/1.1 200 ok\n".getBytes());
                ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                ops.write("Server:Apache-Coyote/1.1\n".getBytes());
                ops.write("\n\n".getBytes());
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append("<head><title>I am title</title></head>");
                buffer.append("<body>");
                buffer.append("<h1>I am Header 1</h1>");
                buffer.append("<a href='http://www.baidu.com'>Baidu</a>");
                buffer.append("</body>");
                buffer.append("</html>");
                ops.write(buffer.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (null != ops){
                ops.close();
            }

            if (null != socket){
                socket.close();
            }
        }

    }
}
