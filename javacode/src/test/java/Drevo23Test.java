import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

class StringComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}

class IntegerComparator implements  Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}

public class Drevo23Test {
    private Drevo23<String> drevo23;
    private ArrayList<String> testing;

    @Before
    public void setUp() throws Exception {
        drevo23 = new Drevo23<>(new StringComparator());
        testing = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addOne() {
        drevo23.add("Test");
    }

    @Test
    public void addTwo() {
        drevo23.add("Test2");
        drevo23.add("Test1");
    }

    @Test
    public void addTwoCheckStructure() {
        drevo23.add("Test2");
        drevo23.add("Test1");

        testing.add("(Test1,Test2)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void addOneLevel() {
        drevo23.add("Test7");
        drevo23.add("Test1");
        drevo23.add("Test4");
        drevo23.add("Test2");

        testing.add("(Test4)");
        testing.add("(Test1,Test2)");
        testing.add("(Test7)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void addRotationCheckStructure() {
        drevo23.add("Test7");
        drevo23.add("Test1");
        drevo23.add("Test4");
        drevo23.add("Test0");
        drevo23.add("Test3");
        drevo23.add("Test2");
        drevo23.add("Test5");
        drevo23.add("Test9");

        testing.add("(Test4)");
        testing.add("(Test1)");
        testing.add("(Test0)");
        testing.add("(Test2,Test3)");
        testing.add("(Test7)");
        testing.add("(Test5)");
        testing.add("(Test9)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testSplitting() {
        drevo23.add("Test7");
        drevo23.add("Test1");
        drevo23.add("Test4");
        drevo23.add("Test0");
        drevo23.add("Test3");
        drevo23.add("Test2");
        drevo23.add("Test5");
        drevo23.add("Test9");

        testing.add("(Test4)");
        testing.add("(Test1)");
        testing.add("(Test0)");
        testing.add("(Test2,Test3)");
        testing.add("(Test7)");
        testing.add("(Test5)");
        testing.add("(Test9)");

        assertEquals(testing, drevo23.asListString());

    }

    @Test
    public void addMultipleCheckStructure() {
        drevo23.add("Test7");
        drevo23.add("Test1");
        drevo23.add("Test4");
        drevo23.add("Test0");
        drevo23.add("Test3");
        drevo23.add("Test2");
        drevo23.add("Test5");
        drevo23.add("Test9");
        drevo23.add("Test6");
        drevo23.add("Test8");
        drevo23.add("Test91");
        drevo23.add("Test92");

        testing.add("(Test4)");
        testing.add("(Test1)");
        testing.add("(Test0)");
        testing.add("(Test2,Test3)");
        testing.add("(Test7,Test9)");
        testing.add("(Test5,Test6)");
        testing.add("(Test8)");
        testing.add("(Test91,Test92)");

        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testSplitting3Levels() {
        ArrayList<String> testing = new ArrayList<>();
        drevo23.add("Test7");
        drevo23.add("Test1");
        drevo23.add("Test4");
        drevo23.add("Test0");
        drevo23.add("Test8");
        drevo23.add("Test2");
        drevo23.add("Test9");
        drevo23.add("Test6");
        drevo23.add("Test91");
        drevo23.add("Test5");
        drevo23.add("Test92");

        testing.add("(Test4,Test8)");
        testing.add("(Test1)");
        testing.add("(Test0)");
        testing.add("(Test2)");
        testing.add("(Test6)");
        testing.add("(Test5)");
        testing.add("(Test7)");
        testing.add("(Test91)");
        testing.add("(Test9)");
        testing.add("(Test92)");

        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testLeftSplit() {
        drevo23.add("Test4");
        drevo23.add("Test8");
        drevo23.add("Test9");
        drevo23.add("Test91");
        drevo23.add("Test5");
        drevo23.add("Test7");
        drevo23.add("Test2");
        drevo23.add("Test1");

        testing.add("(Test5)");
        testing.add("(Test2)");
        testing.add("(Test1)");
        testing.add("(Test4)");
        testing.add("(Test8)");
        testing.add("(Test7)");
        testing.add("(Test9,Test91)");

        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testVaje8() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(13);
        drevo23Int.add(11);

        testing.add("(6,12)");
        testing.add("(4)");
        testing.add("(1)");
        testing.add("(5)");
        testing.add("(9)");
        testing.add("(7,8)");
        testing.add("(10,11)");
        testing.add("(15)");
        testing.add("(13)");
        testing.add("(16)");

        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testAddLeftRestructure() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(4);
        drevo23Int.add(12);
        drevo23Int.add(8);
        drevo23Int.add(6);
        drevo23Int.add(13);
        drevo23Int.add(10);
        drevo23Int.add(2);
        drevo23Int.add(7);
        drevo23Int.add(5);
        drevo23Int.add(1);
        drevo23Int.add(3);

        testing.add("(4,8)");
        testing.add("(2)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(6)");
        testing.add("(5)");
        testing.add("(7)");
        testing.add("(12)");
        testing.add("(10)");
        testing.add("(13)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testAddMiddleSplit() {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        integerDrevo23.add(15);
        integerDrevo23.add(6);
        integerDrevo23.add(1);
        integerDrevo23.add(10);
        integerDrevo23.add(7);
        integerDrevo23.add(8);
        integerDrevo23.add(9);
        testing = new ArrayList<>();
        testing.add("(8)");
        testing.add("(6)");
        testing.add("(1)");
        testing.add("(7)");
        testing.add("(10)");
        testing.add("(9)");
        testing.add("(15)");

        assertEquals(testing, integerDrevo23.asListString());
        integerDrevo23.add(3);
        integerDrevo23.add(2);
        integerDrevo23.add(5);
        integerDrevo23.add(4);

        testing = new ArrayList<>();
        testing.add("(4,8)");
        testing.add("(2)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(6)");
        testing.add("(5)");
        testing.add("(7)");
        testing.add("(10)");
        testing.add("(9)");
        testing.add("(15)");

        assertEquals(testing, integerDrevo23.asListString());
    }

    @Test
    public void testRightInsertParent() {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        integerDrevo23.add(17);
        integerDrevo23.add(12);
        integerDrevo23.add(8);
        integerDrevo23.add(1);
        integerDrevo23.add(11);
        integerDrevo23.add(5);
        integerDrevo23.add(4);
        integerDrevo23.add(6);
        integerDrevo23.add(2);
        integerDrevo23.add(3);
        integerDrevo23.add(7);

        testing.add("(4,8)");
        testing.add("(2)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(6)");
        testing.add("(5)");
        testing.add("(7)");
        testing.add("(12)");
        testing.add("(11)");
        testing.add("(17)");

        assertEquals(testing, integerDrevo23.asListString());
    }

    @Test
    public void testAddRightLeftRestructure() {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        integerDrevo23.add(17);
        integerDrevo23.add(12);
        integerDrevo23.add(8);
        integerDrevo23.add(1);
        integerDrevo23.add(11);
        integerDrevo23.add(5);
        integerDrevo23.add(4);
        integerDrevo23.add(6);
        integerDrevo23.add(2);
        integerDrevo23.add(3);
        integerDrevo23.add(15);
        integerDrevo23.add(16);
        integerDrevo23.add(9);
        integerDrevo23.add(10);

        testing.add("(8,12)");
        testing.add("(2,4)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(5,6)");
        testing.add("(10)");
        testing.add("(9)");
        testing.add("(11)");
        testing.add("(16)");
        testing.add("(15)");
        testing.add("(17)");

        assertEquals(testing, integerDrevo23.asListString());

    }

    @Test
    public void testAddRightMiddleRestructure() {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        integerDrevo23.add(17);
        integerDrevo23.add(12);
        integerDrevo23.add(15);
        integerDrevo23.add(4);
        integerDrevo23.add(20);
        integerDrevo23.add(6);
        integerDrevo23.add(3);
        integerDrevo23.add(2);
        integerDrevo23.add(30);
        integerDrevo23.add(13);
        integerDrevo23.add(16);
        integerDrevo23.add(18);

        testing.add("(6,17)");
        testing.add("(3)");
        testing.add("(2)");
        testing.add("(4)");
        testing.add("(15)");
        testing.add("(12,13)");
        testing.add("(16)");
        testing.add("(20)");
        testing.add("(18)");
        testing.add("(30)");

        assertEquals(testing, integerDrevo23.asListString());
    }

    @Test
    public void testAddDepth4() {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        for (int i = 1; i <= 15; i++) {
            integerDrevo23.add(i);
        }

        testing.add("(8)");
        testing.add("(4)");
        testing.add("(2)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(6)");
        testing.add("(5)");
        testing.add("(7)");
        testing.add("(12)");
        testing.add("(10)");
        testing.add("(9)");
        testing.add("(11)");
        testing.add("(14)");
        testing.add("(13)");
        testing.add("(15)");

        assertEquals(testing, integerDrevo23.asListString());
    }

    @Test
    public void testAdd20RandomNumbers() {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        integerDrevo23.add(11);
        integerDrevo23.add(92);
        integerDrevo23.add(13);
        integerDrevo23.add(43);
        integerDrevo23.add(70);
        integerDrevo23.add(63);
        integerDrevo23.add(53);
        integerDrevo23.add(44);
        integerDrevo23.add(23);
        integerDrevo23.add(26);
        integerDrevo23.add(74);
        integerDrevo23.add(54);
        integerDrevo23.add(73);
        integerDrevo23.add(97);
        integerDrevo23.add(85);
        integerDrevo23.add(75);
        integerDrevo23.add(96);
        integerDrevo23.add(61);
        integerDrevo23.add(89);
        integerDrevo23.add(68);

        testing.add("(53,74)");
        testing.add("(13,43)");
        testing.add("(11)");
        testing.add("(23,26)");
        testing.add("(44)");
        testing.add("(61,70)");
        testing.add("(54)");
        testing.add("(63,68)");
        testing.add("(73)");
        testing.add("(85,92)");
        testing.add("(75)");
        testing.add("(89)");
        testing.add("(96,97)");

        assertEquals(testing, integerDrevo23.asListString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateElement() {
        drevo23.add("Test");
        drevo23.add("Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSecondElementDuplicate() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        drevo23.add("Test2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNodeDuplicateFirst() {
        drevo23.add("Test4");
        drevo23.add("Test2");
        drevo23.add("Test7");
        drevo23.add("Test4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNodeDuplicateSecond() {
        drevo23.add("Test4");
        drevo23.add("Test2");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.add("Test9");
        drevo23.add("Test8");
    }



    @Test
    public void removeFirst() {
    }

    @Test
    public void getFirst() {
    }

    @Test
    public void size() {
    }

    @Test
    public void depth() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void testRemoveOneElement() {
        drevo23.add("Test");
        drevo23.remove("Test");
        assertEquals(new ArrayList<String>(), drevo23.asListString());
    }

    @Test
    public void testRemoveElement3NodeFirst() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        drevo23.remove("Test1");

        testing.add("(Test2)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveElement3NodeSecond() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        drevo23.remove("Test2");

        testing.add("(Test1)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testRemoveEmpty() {
        drevo23.remove("Test");
    }


    @Test
    public void testRemoveSimpleRightRotation() {
        drevo23.add("Test2");
        drevo23.add("Test4");
        drevo23.add("Test7");
        drevo23.add("Test1");
        drevo23.remove("Test7");

        testing.add("(Test2)");
        testing.add("(Test1)");
        testing.add("(Test4)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveSimpleLeftRotation() {
        drevo23.add("Test2");
        drevo23.add("Test4");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.remove("Test2");

        testing.add("(Test7)");
        testing.add("(Test4)");
        testing.add("(Test8)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveMiddleChildSimpleRR() {
        drevo23.add("Test2");
        drevo23.add("Test7");
        drevo23.add("Test6");
        drevo23.add("Test1");
        drevo23.add("Test3");
        drevo23.add("Test8");
        drevo23.remove("Test3");

        testing.add("(Test2,Test7)");
        testing.add("(Test1)");
        testing.add("(Test6)");
        testing.add("(Test8)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveMiddleChildSimpleRR2() {
        drevo23.add("Test2");
        drevo23.add("Test7");
        drevo23.add("Test6");
        drevo23.add("Test1");
        drevo23.add("Test3");
        drevo23.add("Test0");
        drevo23.remove("Test3");

        testing.add("(Test1,Test6)");
        testing.add("(Test0)");
        testing.add("(Test2)");
        testing.add("(Test7)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveLeftChildRotation() {
        drevo23.add("Test4");
        drevo23.add("Test6");
        drevo23.add("Test7");
        drevo23.add("Test5");
        drevo23.add("Test3");
        drevo23.remove("Test3");

        testing.add("(Test6)");
        testing.add("(Test4,Test5)");
        testing.add("(Test7)");
        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveMiddleChildLeftRotation() {
        drevo23.add("Test4");
        drevo23.add("Test6");
        drevo23.add("Test7");
        drevo23.add("Test5");
        drevo23.add("Test3");
        drevo23.remove("Test5");

        testing.add("(Test4)");
        testing.add("(Test3)");
        testing.add("(Test6,Test7)");

        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveLeftChildRotation2() {
        drevo23.add("Test4");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.add("Test3");
        drevo23.add("Test5");
        drevo23.add("Test6");
        drevo23.remove("Test3");

        testing.add("(Test5,Test7)");
        testing.add("(Test4)");
        testing.add("(Test6)");
        testing.add("(Test8)");

        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveRightChildRotation1() {
        drevo23.add("Test4");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.add("Test3");
        drevo23.add("Test5");
        drevo23.remove("Test8");

        testing.add("(Test4)");
        testing.add("(Test3)");
        testing.add("(Test5,Test7)");

        assertEquals(testing, drevo23.asListString());
    }

    @Test
    public void testRemoveRightChildRotation2() {
        drevo23.add("Test4");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.add("Test3");
        drevo23.add("Test5");
        drevo23.add("Test6");
        drevo23.remove("Test8");

        testing.add("(Test4,Test6)");
        testing.add("(Test3)");
        testing.add("(Test5)");
        testing.add("(Test7)");

        assertEquals(testing, drevo23.asListString());

    }

    @Test
    public void testRemoveInnerNodeReplace() {
        drevo23.add("Test4");
        drevo23.add("Test2");
        drevo23.add("Test6");
        drevo23.add("Test5");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.remove("Test6");


        testing.add("(Test4,Test7)");
        testing.add("(Test2)");
        testing.add("(Test5)");
        testing.add("(Test8)");
        assertEquals(testing, drevo23.asListString());
    }


    @Test
    public void testRemoveInner2NodeRightReplace() {
        drevo23.add("Test4");
        drevo23.add("Test2");
        drevo23.add("Test6");
        drevo23.add("Test5");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.add("Test1");
        drevo23.add("Test3");
        drevo23.remove("Test6");

        testing.add("(Test4)");
        testing.add("(Test2)");
        testing.add("(Test1)");
        testing.add("(Test3)");
        testing.add("(Test7)");
        testing.add("(Test5)");
        testing.add("(Test8)");
        assertEquals(testing, drevo23.asListString());

    }

    @Test
    public void testRemoveInner3NodeMiddleReplace() {
        drevo23.add("Test4");
        drevo23.add("Test2");
        drevo23.add("Test6");
        drevo23.add("Test5");
        drevo23.add("Test7");
        drevo23.add("Test8");
        drevo23.add("Test1");
        drevo23.add("Test3");
        drevo23.add("Test9");
        drevo23.add("Test91");
        drevo23.remove("Test6");

        testing.add("(Test4)");
        testing.add("(Test2)");
        testing.add("(Test1)");
        testing.add("(Test3)");
        testing.add("(Test7,Test9)");
        testing.add("(Test5)");
        testing.add("(Test8)");
        testing.add("(Test91)");
        assertEquals(testing, drevo23.asListString());

    }

    @Test
    public void testRemoveLeftRotationWithChildren1() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(13);
        drevo23Int.add(11);

        drevo23Int.remove(8);
        drevo23Int.remove(7);
        drevo23Int.remove(5);
        testing.add("(12)");
        testing.add("(6,10)");
        testing.add("(1,4)");
        testing.add("(9)");
        testing.add("(11)");
        testing.add("(15)");
        testing.add("(13)");
        testing.add("(16)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveMiddleWithChildren() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(13);
        drevo23Int.add(11);
        drevo23Int.remove(8);
        drevo23Int.remove(7);
        drevo23Int.remove(10);

        testing.add("(6)");
        testing.add("(4)");
        testing.add("(1)");
        testing.add("(5)");
        testing.add("(12,15)");
        testing.add("(9,11)");
        testing.add("(13)");
        testing.add("(16)");
        assertEquals(testing, drevo23Int.asListString());

    }

    @Test
    public void testRemoveMiddleLeftRotationChildren() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(13);
        drevo23Int.add(11);
        drevo23Int.add(17);
        drevo23Int.add(18);
        drevo23Int.remove(11);
        drevo23Int.remove(8);
        drevo23Int.remove(9);

        testing.add("(6,15)");
        testing.add("(4)");
        testing.add("(1)");
        testing.add("(5)");
        testing.add("(12)");
        testing.add("(7,10)");
        testing.add("(13)");
        testing.add("(17)");
        testing.add("(16)");
        testing.add("(18)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveLeftMiddle3NodeChildren() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(5);
        drevo23Int.add(15);
        drevo23Int.add(3);
        drevo23Int.add(20);
        drevo23Int.add(25);
        drevo23Int.add(14);
        drevo23Int.add(16);
        drevo23Int.add(30);
        drevo23Int.add(35);
        drevo23Int.add(34);
        drevo23Int.add(38);
        drevo23Int.add(17);
        drevo23Int.add(18);
        drevo23Int.remove(5);

        testing.add("(17,30)");
        testing.add("(15)");
        testing.add("(3,14)");
        testing.add("(16)");
        testing.add("(20)");
        testing.add("(18)");
        testing.add("(25)");
        testing.add("(35)");
        testing.add("(34)");
        testing.add("(38)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveLeftWithChildrenRotation() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(6);
        drevo23Int.add(4);
        drevo23Int.add(12);
        drevo23Int.add(9);
        drevo23Int.add(7);
        drevo23Int.add(2);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(13);
        drevo23Int.remove(4);

        testing.add("(9)");
        testing.add("(6)");
        testing.add("(2,5)");
        testing.add("(7)");
        testing.add("(12)");
        testing.add("(10)");
        testing.add("(13)");
        assertEquals(testing, drevo23Int.asListString());

    }

    @Test
    public void testRemoveMiddleRightRotationChildren() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(13);
        drevo23Int.add(11);
        drevo23Int.add(17);
        drevo23Int.add(18);
        drevo23Int.add(2);
        drevo23Int.add(3);
        drevo23Int.remove(11);
        drevo23Int.remove(8);
        drevo23Int.remove(9);

        testing.add("(4,12)");
        testing.add("(2)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(6)");
        testing.add("(5)");
        testing.add("(7,10)");
        testing.add("(15,17)");
        testing.add("(13)");
        testing.add("(16)");
        testing.add("(18)");

        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveRightWithChildren() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(17);
        drevo23Int.remove(16);

        testing.add("(6)");
        testing.add("(4)");
        testing.add("(1)");
        testing.add("(5)");
        testing.add("(9,12)");
        testing.add("(7,8)");
        testing.add("(10)");
        testing.add("(15,17)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveRightMiddle3NodeChildren() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(5);
        drevo23Int.add(15);
        drevo23Int.add(25);
        drevo23Int.add(30);
        drevo23Int.add(35);
        drevo23Int.add(31);
        drevo23Int.add(34);
        drevo23Int.add(6);
        drevo23Int.add(10);
        drevo23Int.add(4);
        drevo23Int.add(3);
        drevo23Int.add(11);
        drevo23Int.add(12);
        drevo23Int.remove(34);

        testing.add("(6,15)");
        testing.add("(4)");
        testing.add("(3)");
        testing.add("(5)");
        testing.add("(11)");
        testing.add("(10)");
        testing.add("(12)");
        testing.add("(30)");
        testing.add("(25)");
        testing.add("(31,35)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveRightLeft3Node() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(6);
        drevo23Int.add(4);
        drevo23Int.add(10);
        drevo23Int.add(1);
        drevo23Int.add(2);
        drevo23Int.add(9);
        drevo23Int.add(11);
        drevo23Int.add(3);
        drevo23Int.add(5);
        drevo23Int.remove(10);

        testing.add("(4)");
        testing.add("(2)");
        testing.add("(1)");
        testing.add("(3)");
        testing.add("(6)");
        testing.add("(5)");
        testing.add("(9,11)");
        assertEquals(testing, drevo23Int.asListString());

    }

    @Test
    public void testRemovePropagateToRoot() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        for (int i = 16; i > 1; i--) {
            drevo23Int.add(i);
        }
        drevo23Int.remove(2);
        testing.add("(9,13)");
        testing.add("(5,7)");
        testing.add("(3,4)");
        testing.add("(6)");
        testing.add("(8)");
        testing.add("(11)");
        testing.add("(10)");
        testing.add("(12)");
        testing.add("(15)");
        testing.add("(14)");
        testing.add("(16)");

        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveToRootRight() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        for (int i = 16; i > 1; i--) {
            drevo23Int.add(i);
        }
        drevo23Int.remove(16);
        testing.add("(5,9)");
        testing.add("(3)");
        testing.add("(2)");
        testing.add("(4)");
        testing.add("(7)");
        testing.add("(6)");
        testing.add("(8)");
        testing.add("(11,13)");
        testing.add("(10)");
        testing.add("(12)");
        testing.add("(14,15)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testRemoveRoot() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        for (int i = 16; i > 1; i--) {
            drevo23Int.add(i);
        }
        drevo23Int.remove(9);

        testing.add("(5,10)");
        testing.add("(3)");
        testing.add("(2)");
        testing.add("(4)");
        testing.add("(7)");
        testing.add("(6)");
        testing.add("(8)");
        testing.add("(13,15)");
        testing.add("(11,12)");
        testing.add("(14)");
        testing.add("(16)");
        assertEquals(testing, drevo23Int.asListString());
    }

    @Test
    public void testExistsOneTrue() {
        drevo23.add("Test");
        assertTrue(drevo23.exists("Test"));
    }

    @Test
    public void testExistsOneFalse() {
        drevo23.add("Test");
        assertFalse(drevo23.exists("Test1"));
    }

    @Test
    public void testExistsMultiple() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        drevo23Int.add(7);
        drevo23Int.add(9);
        drevo23Int.add(12);
        drevo23Int.add(4);
        drevo23Int.add(6);
        drevo23Int.add(1);
        drevo23Int.add(5);
        drevo23Int.add(10);
        drevo23Int.add(15);
        drevo23Int.add(8);
        drevo23Int.add(16);
        drevo23Int.add(13);
        drevo23Int.add(11);
        assertFalse(drevo23Int.exists(100));
        assertTrue(drevo23Int.exists(5));
        assertTrue(drevo23Int.exists(10));
        assertTrue(drevo23Int.exists(12));
        assertTrue(drevo23Int.exists(13));
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, drevo23.size());
    }

    @Test
    public void testSizeOne() {
        drevo23.add("Test");
        assertEquals(1, drevo23.size());
    }

    @Test
    public void testSizeTwo() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        assertEquals(2, drevo23.size());
    }

    @Test
    public void testSizeMultiple() {
        for (int i = 0; i < 20; i++) {
            drevo23.add("Test" + i);
        }
        assertEquals(20, drevo23.size());
    }

    @Test
    public void testDepthOne() {
        drevo23.add("Test");
        assertEquals(1, drevo23.depth());
    }

    @Test
    public void testDepthTwo() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        drevo23.add("Test3");
        assertEquals(2, drevo23.depth());
    }

    @Test
    public void testDepthMultiple() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        for (int i = 0; i <= 16; i++) {
            drevo23Int.add(i);
        }
        assertEquals(4, drevo23Int.depth());
    }

    @Test
    public void testRemoveFirst() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        for (int i = 16; i > 1; i--) {
            drevo23Int.add(i);
        }

        assertEquals((Integer) 9, drevo23Int.removeFirst());
        assertEquals((Integer) 5, drevo23Int.removeFirst());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testRemoveFirstEmpty() {
        drevo23.removeFirst();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testGetFirstEmpty() {
        drevo23.getFirst();
    }

    @Test
    public void testGet() {
        Drevo23<Integer> drevo23Int = new Drevo23<>(new IntegerComparator());
        for (int i = 16; i > 1; i--) {
            drevo23Int.add(i);
        }
        assertEquals((Integer) 9, drevo23Int.getFirst());
        assertEquals((Integer) 9, drevo23Int.getFirst());

    }

    @Test
    public void testIsEmptyTrue() {
        assertTrue(drevo23.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        drevo23.add("Test");
        assertFalse(drevo23.isEmpty());
    }

    @Test
    public void testReset() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        drevo23.add("Test3");
        drevo23.reset();
        assertTrue(drevo23.isEmpty());
    }

    @Test
    public void testGetElementSecond() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        assertEquals("Test2", drevo23.getElement("Test2"));
    }

    @Test
    public void test2NodeGetElementFirst() {
        drevo23.add("Test");
        assertEquals("Test", drevo23.getElement("Test"));
    }

    @Test
    public void test3NodeGetElementFirst() {
        drevo23.add("Test1");
        drevo23.add("Test2");
        assertEquals("Test1", drevo23.getElement("Test1"));
    }

    @Test
    public void asList() {
    }

    @Test
    public void testSaveRestore() throws IOException, ClassNotFoundException {
        Drevo23<Integer> integerDrevo23 = new Drevo23<>(new IntegerComparator());
        integerDrevo23.add(11);
        integerDrevo23.add(92);
        integerDrevo23.add(13);
        integerDrevo23.add(43);
        integerDrevo23.add(70);
        integerDrevo23.add(63);
        integerDrevo23.add(53);
        integerDrevo23.add(44);
        integerDrevo23.add(23);
        integerDrevo23.add(26);
        integerDrevo23.add(74);
        integerDrevo23.add(54);
        integerDrevo23.add(73);
        integerDrevo23.add(97);
        integerDrevo23.add(85);
        integerDrevo23.add(75);
        integerDrevo23.add(96);
        integerDrevo23.add(61);
        integerDrevo23.add(89);
        integerDrevo23.add(68);

        testing.add("(53,74)");
        testing.add("(13,43)");
        testing.add("(11)");
        testing.add("(23,26)");
        testing.add("(44)");
        testing.add("(61,70)");
        testing.add("(54)");
        testing.add("(63,68)");
        testing.add("(73)");
        testing.add("(85,92)");
        testing.add("(75)");
        testing.add("(89)");
        testing.add("(96,97)");

        integerDrevo23.save(new FileOutputStream("test.bin"));
        integerDrevo23.reset();
        integerDrevo23.restore(new FileInputStream("test.bin"));
        assertEquals(testing, integerDrevo23.asListString());

    }

    @Test(expected = java.lang.OutOfMemoryError.class)
    public void testMockOutOfMemory() {
        Drevo23Mock<String> mock = new Drevo23Mock<>();
        mock.add("");
    }

    @Test(expected = IOException.class)
    public void testMockIOError() throws IOException {
        Drevo23Mock<String> mock = new Drevo23Mock<>();
        File test = new File("test.bin");
        test.createNewFile();
        mock.save(new FileOutputStream("test.bin"));
    }

}