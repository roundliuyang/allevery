package com.yly.agent;



import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


/**
 * ClassFileTransformer接口是Java编程语言中的一部分，它允许在类加载时转换类文件的字节码。
 * 当目标应用程序加载一个类时，系统会调用transform方法来转换类的字节码。
 */
public class MyClassFileTransformer implements ClassFileTransformer {
    /**
     * 在transform方法的实现中，首先通过比较className参数是否等于"com/example/aop/agent/MyTest"来确定是否需要对该类进行字节码转换。
     * 如果相等，将使用ASM框架进行字节码转换。
     *
     * 在这个例子中，使用了ASM框架来修改目标类的字节码。ClassReader用于读取目标类的字节码，ClassWriter用于写入修改后的字节码，
     * 而ClassVisitor则是ASM框架的一个访问者，用于访问并修改类的字节码。
     *
     * @param loader 加载目标类的类加载器
     * @param className 目标类的全限定名
     * @param classBeingRedefined 如果是被重新定义的类，则为被重新定义的类；如果是新增加的类，则为null
     * @param protectionDomain 目标类的保护域
     * @param classfileBuffer 目标类的字节码数组
     * @return
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if ("com/yly/agent/MyTest".equals(className)) {
            // 使用ASM框架进行字节码转换
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new TimeStatisticsVisitor(Opcodes.ASM7, cw);
            cr.accept(cv, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
            return cw.toByteArray();
        }
        return classfileBuffer;

    }
}