package com.yly.optional;

import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Author: yly
 * Date: 2021/10/16 10:20
 */
public class OptionalUnitTest {


    /**
     *  1.创建 Optional 对象
     */

    //创建一个空的Optional对象
    @Test
    public void whenCreatesEmptyOptional() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    //通过静态of方法创建Optional对象
    @Test
    public void whenCreatesNonNullable() {
        String name = "baeldung";
        Optional<String> opt = Optional.of(name);
        assertTrue(opt.isPresent());
    }

    //但是，传递给of()方法的参数不能为空。否则，我们会得到一个NullPointerException：
//    @Test(expected = NullPointerException.class)
//    public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
//        String name = null;
//        Optional.of(name);
//    }

    //但是如果我们期望一些空值，我们可以使用ofNullable()方法
    @Test
    public void givenNonNull_whenCreatesNullable() {
        String name = "baeldung";
        Optional<String> opt = Optional.ofNullable(name);
        assertTrue(opt.isPresent());
    }

    //通过这样做，如果我们传入一个空引用，它不会抛出异常而是返回一个空的Optional对象
    @Test
    public void givenNull_whenCreatesNullable() {
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);
        assertFalse(opt.isPresent());
    }



    /**
     *  2.检查值是否存在：isPresent()和isEmpty()
     */

    // 当我们有一个从方法返回或由我们创建的Optional对象时，我们可以使用isPresent() 方法检查其是否有值
    @Test
    public void givenOptional_whenIsPresentWorks() {
        Optional<String> opt = Optional.of("Baeldung");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    //此外，从 Java 11 开始，我们可以使用isEmpty 方法执行相反的操作 ：
//    @Test
//    public void givenAnEmptyOptional_thenIsEmptyBehavesAsExpected() {
//        Optional<String> opt = Optional.of("Baeldung");
//        assertFalse(opt.isEmpty());
//
//        opt = Optional.ofNullable(null);
//        assertTrue(opt.isEmpty());
//    }

    //
    @Test
    public void givenOptional_whenIfPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        opt.ifPresent(name -> System.out.println(name.length()));
    }

    //orElse() 方法用于检索包装在Optional中的值。它接受一个参数，作为默认值。如果存在，则 orElse() 方法返回包装值，否则返回其参数
    @Test
    public void whenOrElseWorks() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("john");
        assertEquals("john", name);
    }

    //orElseGet()方法与orElse()类似。然而，如果Optional值不存在，它不是取一个值来返回，而是取一个 supplier 功能接口，它被调用并返回调用的值
    @Test
    public void whenOrElseGetWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
        assertEquals("john", name);
    }


    /**
     *  3.rElse和orElseGet() 的区别。两者之间有一个微妙但非常重要的区别，如果没有很好地理解，它会极大地影响我们代码的性能。
     */

    //让我们在测试类中创建一个名为getMyDefault()的方法，它不接受任何参数并返回一个默认值：
    public String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }

    //让我们看两个测试并观察它们的副作用，以确定orElse() 和orElseGet()重叠的地方和不同的地方：
    @Test
    public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;

        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Default Value", defaultText);

        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Default Value", defaultText);
    }

    //在上面的示例中，我们将空文本包装在Optional对象中，并尝试使用两种方法中的每一种来获取包装的值。
    //副作用是：
    // Getting default value...
    // Getting default value...
    //所述getMyDefault（）方法被调用时在每种情况下。碰巧的是，当包装的值不存在时，orElse()和orElseGet() 的工作方式完全相同。
    //现在让我们运行另一个存在值的测试，理想情况下，甚至不应该创建默认值：
    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";

        System.out.println("Using orElseGet:");
        String defaultText
                = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Text present", defaultText);
    }
    //在上面的示例中，我们不再包装空值，其余代码保持不变。
    //现在让我们来看看运行这段代码的副作用：
    //Using orElseGet:
    //Using orElse:
    //Getting default value...
    //请注意，当使用orElseGet()检索包装值时，甚至不会调用getMyDefault()方法，因为包含的值存在。但是，在使用orElse() 时，无论包装值是否存在，都会创建默认对象。所以在这种情况下，我们刚刚创建了一个从未使用过的冗余对象。
    //在这个简单的例子中，创建一个默认对象并没有太大的成本，因为 JVM 知道如何处理这样的问题。但是，当诸如getMyDefault() 之类的方法必须进行 Web 服务调用甚至查询数据库时，成本就变得非常明显。

    /**
     *  4.orElseThrow()异常
     */

    //所述orElseThrow（）方法 follow from OrElse运算（）和orElseGet（） ，并增加了一个新的方法，用于处理一个缺席值。
    //当包装的值不存在时，它不会返回默认值，而是抛出异常：
//    @Test(expected = IllegalArgumentException.class)
//    public void whenOrElseThrowWorks_thenCorrect() {
//        String nullName = null;
//        String name = Optional.ofNullable(nullName).orElseThrow(
//                IllegalArgumentException::new);  //Java 8 中的方法引用在这里派上用场，用于传入异常构造函数。
//    }


    /**
     *  5.使用get()返回值
     */

    //检索包装值的最后一种方法是get()方法：
    @Test
    public void givenOptional_whenGetsValue_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        String name = opt.get();
        assertEquals("baeldung", name);
    }
    //但是，与前三种方法不同，get()只能在包装对象不为null 时返回一个值；否则，它会抛出一个 no such element 异常：
//    @Test(expected = NoSuchElementException.class)
//    public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
//        Optional<String> opt = Optional.ofNullable(null);
//        String name = opt.get();
//    }
    //这是get()方法的主要缺陷。理想情况下，Optional应该可以帮助我们避免此类不可预见的异常。因此，这种方法违背了Optional的目标，并且可能会在未来的版本中被弃用。
    //因此，建议使用其他变体，使我们能够准备并显式处理null情况。


    /**
     * 6.使用filter()条件返回
     */

    //我们可以使用filter方法对包装的值运行内联测试。它接受一个谓词作为参数并返回一个Optional对象。如果包装的值通过谓词的测试，则Optional原样返回。
    //但是，如果谓词返回false，那么它将返回一个空的Optional：
    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2016;
        Optional<Integer> yearOptional = Optional.of(year);
        boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
        assertTrue(is2016);
        boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
        assertFalse(is2017);
    }

    //让我们看另一个有意义的例子
    //然后我们将这些对象提供给一些代码，其唯一目的是检查调制解调器价格是否在我们的预算范围内。
    //现在让我们看看没有Optional的代码：
    public boolean priceIsInRange1(Modem modem) {
        boolean isInRange = false;

        if (modem != null && modem.getPrice() != null
                && (modem.getPrice() >= 10
                && modem.getPrice() <= 15)) {

            isInRange = true;
        }
        return isInRange;
    }
    //注意我们必须编写多少代码才能实现这一点，尤其是在if条件下。if条件中对应用程序至关重要的唯一部分是最后的价格范围检查；其余的检查是防御性的：
    @Test
    public void whenFiltersWithoutOptional_thenCorrect() {
        assertTrue(priceIsInRange1(new Modem(10.0)));
        assertFalse(priceIsInRange1(new Modem(9.9)));
        assertFalse(priceIsInRange1(new Modem(null)));
        assertFalse(priceIsInRange1(new Modem(15.5)));
        assertFalse(priceIsInRange1(null));
    }

    //现在让我们看一个带有Optional#filter的变体：
    public boolean priceIsInRange2(Modem modem2) {
        return Optional.ofNullable(modem2)
                .map(Modem::getPrice)
                .filter(p -> p >= 10)
                .filter(p -> p <= 15)
                .isPresent();
    }
    //该map调用简单地用于一个值转换为其他值。请记住，此操作不会修改原始值。
    //首先，如果将空对象传递给此方法，我们不希望有任何问题。
    //其次，我们在其主体中编写的唯一逻辑正是方法名称所描述的——价格范围检查。Optional负责其余的工作：
    @Test
    public void whenFiltersWithOptional_thenCorrect() {
        assertTrue(priceIsInRange2(new Modem(10.0)));
        assertFalse(priceIsInRange2(new Modem(9.9)));
        assertFalse(priceIsInRange2(new Modem(null)));
        assertFalse(priceIsInRange2(new Modem(15.5)));
        assertFalse(priceIsInRange2(null));
    }


    /**
     *  6.使用map()转换值
     */

    //我们可以使用类似的语法通过map()方法转换Optional值：
    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList(
                "paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional
                .map(List::size)
                .orElse(0);
        assertEquals(6, size);
    }
    //在这个例子中，我们将一个字符串列表包装在一个Optional对象中，并使用其map方法对包含的列表执行操作。我们执行的操作是检索列表的大小。
    //该map方法返回包裹内的计算的结果可选。然后我们必须对返回的Optional调用适当的方法来检索其值。
    //请注意，filter方法仅对值执行检查，并仅在与给定谓词匹配时才返回描述此值的Optional。否则返回一个空的 Optional。然而，map方法采用现有值，使用该值执行计算，并返回包装在Optional对象中的计算结果：
    @Test
    public void givenOptional_whenMapWorks_thenCorrect2() {
        String name = "baeldung";
        Optional<String> nameOptional = Optional.of(name);

        int len = nameOptional
                .map(String::length)
                .orElse(0);
        assertEquals(8, len);
    }

    //我们可以将map和filter 链接在一起来做一些更强大的事情。
    //假设我们要检查用户输入的密码的正确性。我们可以使用map transformation清理密码并使用filter检查其正确性
    @Test
    public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt.filter(
                pass -> pass.equals("password")).isPresent();
        assertFalse(correctPassword);

        correctPassword = passOpt
                .map(String::trim)
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertTrue(correctPassword);
    }
    //正如我们所看到的，如果不先清理输入，它就会被过滤掉——但用户可能认为前导和尾随空格都构成输入是理所当然的。
    //因此，在过滤掉不正确的密码之前，我们先用map将一个脏的密码转化为一个干净的密码。


    /**
     *  7.使用map()转换值
     */

    //就像map()方法一样，我们也有flatMap()方法作为转换值的替代方法。不同之处在于map仅在解包时转换值，而flatMap接受一个被包装的值并在转换之前将其解包。
    //之前，我们创建了简单的String和Integer对象来包装在Optional实例中。然而，我们经常会从复杂对象的访问者那里收到这些对象。
    //为了更清楚地了解差异，让我们看一个Person对象，它获取一个人的详细信息，例如姓名、年龄和密码：
    //我们通常会创建这样一个对象并将其包装在一个Optional对象中，就像我们对 String 所做的那样。
    //或者，它可以通过另一个方法调用返回给我们：
    Person person = new Person("john", 26);
    Optional<Person> personOptional = Optional.of(person);

    //现在请注意，当我们包装一个Person对象时，它将包含嵌套的Optional实例：
    @Test
    public void givenOptional_whenFlatMapWorks_thenCorrect2() {
        Person person = new Person("john", 26);
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper
                = personOptional.map(Person::getName);
        Optional<String> nameOptional
                = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("");
        assertEquals("john", name1);

        String name = personOptional
                .flatMap(Person::getName)
                .orElse("");
        assertEquals("john", name);
    }
    //在这里，我们尝试检索Person对象的 name 属性以执行断言。
    //请注意我们如何在第三条语句中使用map()方法实现这一点，然后注意我们如何使用flatMap()方法实现这一点。
    //该人::的getName方法引用类似于字符串::装饰调用我们在上一节中有清理密码。
    //唯一的区别是getName()返回一个Optional而不是像trim()操作一样的 String 。这一点，再加上映射转换将结果包装在Optional对象中的事实，导致嵌套的Optional。
    //因此，在使用map()方法时，我们需要在使用转换后的值之前添加一个额外的调用来检索值。这样，Optional包装器将被删除。使用flatMap时会隐式执行此操作。

    /**
     *  8.Chaining Optionals in Java 8
     */






























}
