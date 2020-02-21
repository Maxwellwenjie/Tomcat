package com.h3c.tomcatv2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Servlet {
    //初始化
    public void init();
    //服务
    public void service(InputStream is, OutputStream ops) throws IOException;
    //销毁
    public void destroy();
}
