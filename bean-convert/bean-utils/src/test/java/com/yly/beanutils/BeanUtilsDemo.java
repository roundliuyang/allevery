package com.yly.beanutils;

public class BeanUtilsDemo {

    /*
        使用场景：
        一般当我们有两个具有很多相同属性的JavaBean实体类时，一个很常见的情况就是Struts里的PO对象（持久对象）和对应的ActionForm
        传统的方式对属性逐个赋值:依次的进行set,get但是这样的赋值方式，是非常麻烦的，而且重复代码量可以会很多,很冗余。而这时我们就可以使用BeanUtils.copyProperties()方法

        BeanUtils.copyProperties(Object source, Object target);
        => BeanUtils.copyProperties("转换前的类", "转换后的类");


        注意：
        BeanUtils.copyProperties(a, b);
            b中的存在的属性，a中一定要有，但是a中可以有多余的属性；
            a中与b中相同的属性都会被替换，不管是否有值；
            a、 b中的属性要名字相同，才能被赋值，不然的话需要手动赋值；
            Spring的BeanUtils的CopyProperties方法需要对应的属性有getter和setter方法；
            如果存在属性完全相同的内部类，但是不是同一个内部类，即分别属于各自的内部类，则spring会认为属性不同，不会copy；
            spring和apache的copy属性的方法源和目的参数的位置正好相反，所以导包和调用的时候都要注意一下。

     */
}
