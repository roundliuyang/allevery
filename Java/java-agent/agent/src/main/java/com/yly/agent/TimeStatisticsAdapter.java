package com.yly.agent;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * AdviceAdapter是ASM框架中的一个适配器类，它继承自MethodVisitor类，用于访问和修改方法的字节码。
 * TimeStatisticsAdapter通过继承AdviceAdapter来扩展其功能。
 */
public class TimeStatisticsAdapter extends AdviceAdapter {

    /**
     * 这段代码定义了一个名为TimeStatisticsAdapter的类，它是一个扩展AdviceAdapter的适配器类。它在方法的字节码中通过重写onMethodEnter和onMethodExit方法，
     * 插入了时间统计的代码，以在方法的进入和退出时调用相应的静态方法。
     *
     * @param api 使用的ASM版本
     * @param methodVisitor 表示要被扩展的目标MethodVisitor
     * @param access 正在访问的方法的访问标志
     * @param name 正在访问的方法的方法名
     * @param descriptor 正在访问的方法的方法描述符
     */
    protected TimeStatisticsAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }

    /**
     * onMethodEnter方法在方法进入时被调用，在这个方法中，通过调用super.visitMethodInsn方法，插入了一条字节码指令，
     * 用于调用TimeStatistics类的静态方法start来开始计时。
     */
    @Override
    protected void onMethodEnter() {
        // 进入函数时调用TimeStatistics的静态方法start
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/yly/agent/TimeStatistics", "start", "()V", false);
        super.onMethodEnter();
    }

    /**
     * onMethodExit方法在方法退出时被调用，在这个方法中，首先调用super.onMethodExit方法，确保在方法正常退出时执行原始的方法字节码。
     * 然后，通过调用super.visitMethodInsn方法，插入了一条字节码指令，用于调用TimeStatistics类的静态方法end来结束计时。
     * @param opcode
     */
    @Override
    protected void onMethodExit(int opcode) {
        // 退出函数时调用TimeStatistics的静态方法end
        super.onMethodExit(opcode);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/yly/agent/TimeStatistics", "end", "()V", false);
    }
}