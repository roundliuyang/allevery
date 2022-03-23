//package com.yly.commons.lang3.CollectionUtils;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.Predicate;
//import org.apache.commons.collections4.Transformer;
//import org.junit.Test;
//
//import java.util.*;
//
//import static org.junit.Assert.*;
//
///**
// * 简而言之，Apache CollectionUtils为常见操作提供了实用方法，这些方法涵盖了广泛的用例并有助于避免编写样板代码。
// * 该库面向较旧的 JVM 版本，因为目前 Java 8 的Stream API提供了类似的功能。
// */
//public class CollectionUtilsTest {
//
//    static  Customer customer1 = new Customer(1, "Daniel", "locality1", "city1");
//    static  Customer customer2 = new Customer(2, "Fredrik", "locality2", "city2");
//    static  Customer customer3 = new Customer(3, "Kyle", "locality3", "city3");
//    static  Customer customer4 = new Customer(4, "Bob", "locality4", "city4");
//    static  Customer customer5 = new Customer(5, "Cat", "locality5", "city5");
//    static  Customer customer6 = new Customer(6, "John", "locality6", "city6");
//
//    static  List<Customer> list1 = Arrays.asList(customer1, customer2, customer3);
//    static  List<Customer> list2 = Arrays.asList(customer4, customer5, customer6);
//    static  List<Customer> list3 = Arrays.asList(customer1, customer2);
//
//    static  List<Customer> linkedList1 = new LinkedList<>(list1);
//
//
//
//    //让我们来看看Apache Commons CollectionUtils类中一些最常用的方法。
//    //仅添加非空元素
//    //我们可以使用CollectionUtils 的 addIgnoreNull方法将非空元素添加到提供的集合中。
//    //这个方法的第一个参数是我们想要添加元素的集合，第二个参数是我们想要添加的元素：
//    @Test
//    public void collectionUtilsTest() {
//
//        CollectionUtils.addIgnoreNull(list1, null);
//        assertFalse(list1.contains(null));
//        //请注意，null未添加到列表中。
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//    //转化对象
//    //我们可以使用transform方法将A类的对象转化为B类的不同对象。这个方法接收一个A类对象的列表和一个转化器作为参数。
//    //这个操作的结果是一个B类对象的列表。
//    @Test
//    public void collectionUtilsTest2(){
//        Collection<Address> addressCol = CollectionUtils.collect(list1,
//                new Transformer<Customer, Address>() {
//                    public Address transform(Customer customer) {
//                        return customer.getAddress();
//                    }
//                });
//
//        List<Address> addressList = new ArrayList<>(addressCol);
//        assertTrue(addressList.size() == 3);
//        assertTrue(addressList.get(0).getLocality().equals("locality1"));
//
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//
//    //整理列表
//    //我们可以使用 collate 方法来整理两个已经排序的列表。这个方法将我们想要合并的两个列表作为参数，并返回一个单一的排序列表。
//    @Test
//    public void collectionUtilsTest3() {
//        List<Customer> sortedList = CollectionUtils.collate(list1,list2);
//
//        assertEquals(6, sortedList.size());
//        assertTrue(sortedList.get(0).getName().equals("Bob"));
//        assertTrue(sortedList.get(2).getName().equals("Daniel"));
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//
//    //筛选对象
//    //使用过滤器，我们可以从一个列表中移除不满足给定条件的对象。该方法将列表作为第一个参数，将一个谓词作为第二个参数。
//    //filterInverse方法的作用正好相反。当 Predicate 返回 true 时，它将从列表中删除对象。
//    //如果输入的列表被修改，即至少有一个对象被从列表中过滤掉，那么 filter 和 filterInverse 都返回 true。
//
//    @Test
//    public void collectionUtilsTest4() {
//
//        boolean isModified = CollectionUtils.filter(linkedList1,
//                new Predicate<Customer>() {
//                    public boolean evaluate(Customer customer) {
//                        return Arrays.asList("Daniel","Kyle").contains(customer.getName());
//                    }
//                });
//
//        assertTrue(linkedList1.size() == 2);
//        //如果我们希望返回结果列表而不是一个布尔标志，我们可以使用select和selectRejected。
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//
//    // 检查非空
//    // 当我们想要检查列表中是否至少有一个元素时，isNotEmpty 方法非常方便。检查相同的另一种方法是：
//    // boolean isNotEmpty = (list != null && list.size() > 0);
//    //虽然上面这行代码做了同样的事情，但 CollectionUtils.isNotEmpty 让我们的代码更简洁：
//    @Test
//    public void collectionUtils5() {
//        assertTrue(CollectionUtils.isNotEmpty(list1));
//
//
//        //isEmpty 的作用正好相反。它检查给定的列表是否为空或者列表中是否有零个元素
//        List<Customer> emptyList = new ArrayList<>();
//        List<Customer> nullList = null;
//
//        assertTrue(CollectionUtils.isEmpty(nullList));
//        assertTrue(CollectionUtils.isEmpty(emptyList));
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//
//    //检查包含
//    //我们可以使用 isSubCollection 来检查一个集合是否包含在另一个集合中。 isSubCollection 将两个集合作为参数，如果第一个集合是第二个集合的子集合，则返回 true：
//    @Test
//    public void collectionUtils6() {
//        assertTrue(CollectionUtils.isSubCollection(list3, list1));
//        //如果某个对象在第一个集合中出现的次数小于或等于它在第二个集合中出现的次数，则该集合是另一个集合的子集合。
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//
//    //集合的相交
//    //我们可以使用CollectionUtils.intersection方法来获得两个集合的交叉点。这个方法接收两个集合，并返回一个在两个输入集合中都有的元素的集合。
//    @Test
//    public void collectionUtils7() {
//        Collection<Customer> intersection = CollectionUtils.intersection(list1, list3);
//        assertTrue(intersection.size() == 2);
//        //一个元素在结果集合中出现的次数是它在每个给定集合中出现次数的最小值。
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//    //对集合进行减法
//    //CollectionUtils.tongract将两个集合作为输入，并返回一个包含在第一个集合中存在但不在第二个集合中的元素的集合。
//    @Test
//    public void collectionUtils8() {
//        Collection<Customer> result = CollectionUtils.subtract(list1, list3);
//        assertFalse(result.contains(customer1));
//        //一个集合在结果中出现的次数是它在第一个集合中出现的次数减去它在第二个集合中出现的次数。
//    }
//    //------------------------------------------------------------------------------------------------
//
//
//
//    //集合的联合
//    //CollectionUtils.union是两个集合的联合，并返回一个集合，其中包含第一个或第二个集合中的所有元素。
//    @Test
//    public void collectionUtils9() {
//        Collection<Customer> union = CollectionUtils.union(list1, list2);
//
//        assertTrue(union.contains(customer1));
//        assertTrue(union.contains(customer4));
//        //元素在结果集合中出现的次数是它在每个给定集合中出现的次数的最大值。
//    }
//}
