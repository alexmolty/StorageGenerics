import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyArrayTest {
    MyArray<Integer> myArrayInt = new MyArray<>();
    MyArray<String> myArrayString = new MyArray<>();

    @Test
    @Order(1)
    void creationMyArray() {
        MyArray<String> myArrayInt = new MyArray<>(0);
        assertEquals(0, myArrayInt.size());
        assertEquals(16, myArrayInt.getCapacity());
    }

    @Test
    @Order(2)
    void add() {
        myArrayInt.add(5);
        myArrayInt.add(10);
        assertEquals(5, myArrayInt.get(0));
        assertEquals(10, myArrayInt.get(1));

        myArrayString.add("Hello");
        myArrayString.add("World");
        assertEquals("Hello", myArrayString.get(0));
        assertEquals("World", myArrayString.get(1));
    }

    @Test
    void addNullIgnore() {
        myArrayInt.add(5);
        myArrayInt.add(null);
        myArrayInt.add(10);
        assertEquals(5, myArrayInt.get(0));
        assertEquals(10, myArrayInt.get(1));
    }
    @Test
    void addByIndex() {
        myArrayInt.addAll(1, 2, 3);
        myArrayInt.add(1, 999);
        assertArrayEquals(new Object[]{1, 999, 2, 3}, myArrayInt.toArray());

        // в начало
        myArrayInt.add(0, 0);
        assertArrayEquals(new Object[]{0, 1, 999, 2, 3}, myArrayInt.toArray());

        // в конец
        myArrayInt.add(myArrayInt.size(), 100);
        assertArrayEquals(new Object[]{0, 1, 999, 2, 3, 100}, myArrayInt.toArray());

        // недопустимые индексы
        assertFalse(myArrayInt.add(-1, 5));
        assertFalse(myArrayInt.add(myArrayInt.size() + 1, 5));
    }
    @Test
    void set() {
        myArrayInt.add(5);
        myArrayInt.add(10);
        myArrayInt.set(0, 123);
        assertEquals(123, myArrayInt.get(0));
    }

    @Test
    void remove() {
        myArrayInt.add(5);
        myArrayInt.add(10);
        myArrayInt.remove(0);
        assertEquals(10, myArrayInt.get(0));
        assertEquals(1, myArrayInt.size());
        myArrayInt.remove(0);
        assertEquals(0, myArrayInt.size());
    }

    @Test
    void removeNull() {
        myArrayInt.add(5);
        myArrayInt.add(10);
        myArrayInt.add(15);
        myArrayInt.set(1, null);
        assertEquals(1, myArrayInt.indexOf(null));
        myArrayInt.remove(null);
        assertEquals(2, myArrayInt.size());
        assertEquals(15, myArrayInt.get(1));
    }
    @Test
    void removeByIndex() {
        myArrayInt.addAll(1, 2, 3);
        assertEquals(2, myArrayInt.remove(1));  // индекс 1 → удалил 2
        assertArrayEquals(new Object[]{1, 3}, myArrayInt.toArray());
    }

    @Test
    void removeByObj() {
        myArrayInt.addAll(1, 2, 3);
        assertTrue(myArrayInt.remove(Integer.valueOf(1)));  // удалил объект 1
        assertArrayEquals(new Object[]{2, 3}, myArrayInt.toArray());
    }
// Uncomment this test - there should be an error: incompatible types
//    @Test
//    void addStringToArrayInt(){
//        myArrayInt.add("String");
//    }
    @Test
    void indexOf() {
        myArrayInt.add(12);
        myArrayInt.add(23);
        myArrayInt.add(23);
        myArrayInt.add(34);
        assertEquals(1, myArrayInt.indexOf(23));
        assertEquals(3, myArrayInt.indexOf(34));
        assertEquals(0, myArrayInt.indexOf(12));
        assertEquals(-1, myArrayInt.indexOf(100));
        assertEquals(-1, myArrayInt.indexOf(null));
    }
    @Test
    void lastIndexOf() {
        myArrayInt.addAll(1, 2, 3, 2, 1);
        assertEquals(4, myArrayInt.lastIndexOf(1));
        assertEquals(3, myArrayInt.lastIndexOf(2));
        assertEquals(2, myArrayInt.lastIndexOf(3));
        assertEquals(-1, myArrayInt.lastIndexOf(99));
    }
    @Test
    void contains() {
        myArrayInt.add(12);
        myArrayInt.add(23);
        assertTrue(myArrayInt.contains(23));
        assertFalse(myArrayInt.contains(34));
        assertFalse(myArrayInt.contains(null));
    }

    @Test
    void addAllnewMyArray() {
        myArrayInt.add(12);
        myArrayInt.add(23);
        MyArray<Integer> myArrayInt2 = new MyArray<>();
        myArrayInt2.add(34);
        myArrayInt2.add(45);
        myArrayInt.addAll(myArrayInt2);
        assertEquals(4, myArrayInt.size());
        assertEquals(12, myArrayInt.get(0));
        assertEquals(23, myArrayInt.get(1));
        assertEquals(34, myArrayInt.get(2));
        assertEquals(45, myArrayInt.get(3));
    }

    @Test
    void addAllVarargs() {
        myArrayInt.add(1);
        myArrayInt.add(2);
        myArrayInt.addAll(3, 4, 5);
        myArrayInt.addAll(6);
        myArrayInt.addAll(new Integer[]{7, 8});
        myArrayInt.addAll();
        assertEquals(8, myArrayInt.size());
        assertEquals(5, myArrayInt.get(4));
    }

    @Test
    void addAllByIndex() {
        myArrayInt.addAll(0, 1, 2, 3, 4, 5);
        MyArray<Integer> myArrayInt2 = new MyArray<>();
        myArrayInt2.addAll(999, 999, 999);
        myArrayInt.addAll(2, myArrayInt2);
        assertArrayEquals(new Object[]{0, 1, 999, 999, 999, 2, 3, 4, 5}, myArrayInt.toArray());
    }

    @Test
    void addAllByIndexEdgeCases() {
        myArrayInt.addAll(1, 2, 3);

        // вставка в начало
        MyArray<Integer> front = new MyArray<>();
        front.addAll(10, 20);
        assertTrue(myArrayInt.addAll(0, front));
        assertArrayEquals(new Object[]{10, 20, 1, 2, 3}, myArrayInt.toArray());

        // вставка в конец
        MyArray<Integer> tail = new MyArray<>();
        tail.addAll(30, 40);
        assertTrue(myArrayInt.addAll(myArrayInt.size(), tail));
        assertArrayEquals(new Object[]{10, 20, 1, 2, 3, 30, 40}, myArrayInt.toArray());

        // недопустимый индекс
        assertFalse(myArrayInt.addAll(-1, front));
        assertFalse(myArrayInt.addAll(myArrayInt.size() + 1, front));

        // null
        assertFalse(myArrayInt.addAll(0, null));
    }

    @Test
    void removeAll() {
        myArrayInt.addAll(1, 2, 3, 4, 5);
        MyArray<Integer> myArrayInt2 = new MyArray<>();
        myArrayInt2.addAll(2, 3, 8);
        assertTrue(myArrayInt.removeAll(myArrayInt2));
        assertArrayEquals(new Object[]{1, 4, 5}, myArrayInt.toArray());
    }
    @Test
    void allocate() {
        assertEquals(16, myArrayInt.getCapacity());
        for (int i = 0; i < 16; i++) {
            myArrayInt.add(i);
        }
        assertEquals(16, myArrayInt.getCapacity());
        myArrayInt.add(999);           // тут должен произойти рост
        assertEquals(32, myArrayInt.getCapacity());
        assertEquals(17, myArrayInt.size());
    }
}