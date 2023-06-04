package com.yly.attach;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * 这段代码的作用是将指定的代理程序加载到目标Java虚拟机进程中执行。代理程序可以用于监控、调试或修改目标虚拟机的行为。
 * 使用jps查询到PrintNumTest的进程id，再用下面的命令执行MyAttachMain类
 * // 运行命令java -cp "c:\Program Files\Java\jdk1.8.0_211\lib\tools.jar;d:\allevery\allevery\Java\java-agent\attach\target\attach-1.0-SNAPSHOT-jar-with-dependencies.jar" com.yly.attach.MyAttachMain 17712
 */
public class MyAttachMain {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        // 通过调用VirtualMachine类的attach方法，将程序attach到指定的Java虚拟机进程上。args[0]表示传递给程序的第一个命令行参数
        VirtualMachine virtualMachine = VirtualMachine.attach(args[0]);
        try {
            // 使用VirtualMachine对象的loadAgent方法加载一个代理程序（Agent）。该方法接受一个代理程序的路径作为参数，这个代理程序是一个JAR文件，它包含了需要在目标Java虚拟机中执行的代码
            virtualMachine.loadAgent("d:\\allevery\\allevery\\Java\\java-agent\\attach\\target\\attach-1.0-SNAPSHOT-jar-with-dependencies.jar");
        } finally {
            // virtualMachine.detach();：在执行完代理程序之后，使用detach方法将程序从目标Java虚拟机上分离
            virtualMachine.detach();
        }
    }
}
