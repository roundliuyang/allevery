package com.yly.agent;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class TimeStatisticsVisitor extends ClassVisitor {

    /**
     * 在TimeStatisticsVisitor的构造函数中，接受了两个参数：api和classVisitor。
     * api参数表示使用的ASM版本，而classVisitor参数表示要被扩展的目标ClassVisitor。
     */
    public TimeStatisticsVisitor(int api, ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // 在visitMethod方法中，首先调用cv.visitMethod方法，该方法用于创建并返回一个MethodVisitor对象，用于访问和修改方法的字节码。
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        // 接下来，通过判断name参数是否等于"<init>"，即构造函数的方法名，如果是构造函数，则直接返回原始的MethodVisitor对象，不进行任何修改。
        if (name.equals("<init>")) {
            return mv;
        }
        // 如果不是构造函数，则创建一个TimeStatisticsAdapter对象，它是一个自定义的MethodVisitor扩展，用于在方法的字节码中插入时间统计的代码。
        return new TimeStatisticsAdapter(api, mv, access, name, descriptor);
    }
}