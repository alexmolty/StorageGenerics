package MyArray.Tests;

import MyArray.MyArray;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class myArrayPredicateTest {
    private MyArray<Integer> numbers;
    private MyArray<String> strings;
    private MyArray<Integer> other1_20;
    private MyArray<Integer> otherEven;

    private final Integer[] arNumbers = {10, 7, 11, -2, 13, 10, 2000};
    private final String[] arStrings = {"abc", "lmn", "fg", "abc"};
    private final Integer[] arEvenNumbers = {10, -2, 10, 2000};
    private final Integer[] arOddNumbers = {7, 11, 13};

    @BeforeEach
    public void setUp() {
        numbers = new MyArray<>();
        for (Integer n : arNumbers) numbers.add(n);

        strings = new MyArray<>();
        for (String s : arStrings) strings.add(s);

        other1_20 = new MyArray<>();
        for (int i = 1; i <= 20; i++) other1_20.add(i);

        otherEven = new MyArray<>();
        for (Integer n : arEvenNumbers) otherEven.add(n);
    }

    @Test
    @DisplayName("Iterate with index-based for-loop")
    public void testIter1() {
        int i = 0;
        for (int j = 0; j < numbers.size(); j++) i++;
        assertEquals(i, numbers.size());
    }

    @Test
    @DisplayName("Iterate with for-each loop")
    public void testIter2() {
        int i = 0;
        for (Integer n : numbers) i++;
        assertEquals(i, numbers.size());
    }

    @Test
    @DisplayName("Remove odd numbers using removeIf")
    public void testPredicateRemoveIfEvenNumbers() {
        assertTrue(numbers.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 != 0;
            }
        }));
        assertArrayEquals(arEvenNumbers, numbers.toArray());
    }

    @Test
    @DisplayName("Remove even numbers using removeIf")
    public void testPredicateRemoveIfOddNumbers() {
        assertTrue(numbers.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 == 0;
            }
        }));
        assertArrayEquals(arOddNumbers, numbers.toArray());
    }

    @Test
    @DisplayName("removeIf returns false when no elements match")
    public void testPredicateRemoveIfNumbersNotFound() {
        assertFalse(numbers.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 5000;
            }
        }));
    }

    @Test
    @DisplayName("Remove strings with length > 2")
    public void testPredicateRemoveIfString() {
        assertTrue(strings.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 2;
            }
        }));
        assertArrayEquals(new String[]{"fg"}, strings.toArray());
    }

    @Test
    @DisplayName("Remove null elements from numbers")
    public void testPredicateRemoveIfNull() {
        MyArray<Integer> numbers = new MyArray<>();
        numbers.addAll(1, 2, null, 4, 5, 6, 7);
        numbers.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer == null;
            }
        });
        assertArrayEquals(new Integer[]{1, 2, 4, 5, 6, 7}, numbers.toArray());
    }

    @Test
    @DisplayName("indexOf with predicate for numbers")
    public void testIndexOfPredicateWithNumbers() {
        int idx = numbers.indexOf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n > 100;
            }
        });
        assertEquals(6, idx); // 2000 на позиции 6

        int notFound = numbers.indexOf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n > 5000;
            }
        });
        assertEquals(-1, notFound);

        int nullIdx = numbers.indexOf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n == null;
            }
        });
        assertEquals(-1, nullIdx);
    }

    @Test
    @DisplayName("lastIndexOf with predicate for numbers")
    public void testLastIndexOfPredicateWithNumbers() {
        int lastGt10 = numbers.lastIndexOf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n > 10;
            }
        });
        assertEquals(6, lastGt10); // 2000 на позиции 6

        int last10 = numbers.lastIndexOf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n == 10;
            }
        });
        assertEquals(5, last10); // второй 10 на позиции 5

        int notFound = numbers.lastIndexOf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n > 5000;
            }
        });
        assertEquals(-1, notFound);
    }

    @Test
    @DisplayName("indexOf with predicate for strings")
    public void testIndexOfPredicateWithStrings() {
        int idx = strings.indexOf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() == 3;
            }
        });
        assertEquals(0, idx);

        int notFound = strings.indexOf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() == 10;
            }
        });
        assertEquals(-1, notFound);
    }

    @Test
    @DisplayName("lastIndexOf with predicate for strings")
    public void testLastIndexOfPredicateWithStrings() {
        int lastIdx = strings.lastIndexOf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() == 3;
            }
        });
        assertEquals(3, lastIdx);

        int lastLen2 = strings.lastIndexOf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() == 2;
            }
        });
        assertEquals(2, lastLen2);
    }

    @Test
    @DisplayName("indexOf first element starting with 'ab'")
    public void testFirstElementIndexOf() {
        int firstIdx = strings.indexOf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.matches("^ab.*");
            }
        });
        assertEquals(0, firstIdx);
    }
}
