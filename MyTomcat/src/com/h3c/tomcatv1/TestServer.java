package com.h3c.tomcatv1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

    public static String WEB_ROOT = System.getProperty("user.dir")+"\\"+"WebContent";

    public static String url = "";

    public static void main(String[] args) throws IOException {
        //System.out.println(WEB_ROOT);
        ServerSocket serverSocket = null;
        InputStream ips = null;
        OutputStream ops = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8080);
            while (true){
                socket = serverSocket.accept();
                ips = socket.getInputStream();
                ops = socket.getOutputStream();

                parse(ips);
                sendStaticResource(ops);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=ips){
                ips.close();
            }
            if (null!=ops){
                ops.close();
            }
            if (null!=socket){
                socket.close();
            }
        }

    }

    private static void sendStaticResource(OutputStream ops) throws IOException {
        byte[] bytes = new byte[2048];
        FileInputStream fis = null;

        try {
            File file = new File(WEB_ROOT, url);
            if (file.exists()){
                ops.write("HTTP/1.1 200 ok\n".getBytes());
                ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                ops.write("Server:Apache-Coyote/1.1\n".getBytes());
                ops.write("\n".getBytes());

                fis = new FileInputStream(file);

                int ch = fis.read(bytes);
                while(ch!=-1){
                    ops.write(bytes, 0, ch);
                    ch = fis.read(bytes);
                }
            }else {
                ops.write("HTTP/1.1 404 not found\n".getBytes());
                ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                ops.write("Server:Apache-Coyote/1.1\n".getBytes());
                ops.write("\n".getBytes());
                String errorMessage = "file not found";
                ops.write(errorMessage.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis){
                fis.close();
            }

        }
    }

    /**
     * 获取请求部分http协议
     */
    private static void parse(InputStream ips) throws IOException {
        byte[] buf = new byte[2048];
        StringBuffer content = new StringBuffer(2048);
        int i = -1;
        //读取客户端发送的数据，将数据读取到字节数组buf中，i代表数据量的大小
        i = ips.read(buf);
        //这里为什么不能直接 buf 转String
        for (int j=0; j<i; j++){
            content.append((char) buf[j]);
        }
        System.out.println(content);
        parseUrl(content.toString());
    }

    private static void parseUrl(String content) {
        int index1,index2;
        index1 = content.indexOf(" ");
        if (index1!=-1){
            index2 = content.indexOf(" ",index1+1);
            if (index2 > index1){
                url = content.substring(index1+2, index2);
                System.out.println(url);
            }
        }
    }
}
