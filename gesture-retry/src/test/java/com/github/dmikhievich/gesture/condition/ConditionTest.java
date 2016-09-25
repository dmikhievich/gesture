package com.github.dmikhievich.gesture.condition;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Dzmitry Mikhievich
 */
@SuppressWarnings("unchecked")
@RunWith(JUnitParamsRunner.class)
public class ConditionTest {

    private final Condition<Object> testCondition = (Condition<Object>) mock(Condition.class);
    private final Object argMock = mock(Object.class);

    @Before
    public void setupMocks() {
        when(testCondition.and(any())).thenCallRealMethod();
        when(testCondition.or(any())).thenCallRealMethod();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd_nullCondition() {
        Condition<Object> nullableCondition = null;
        testCondition.and(nullableCondition);
    }

    @Test
    public void testIsComposite_falseByDefault() {
        Condition condition = mock(Condition.class, CALLS_REAL_METHODS);
        assertThat(condition.isComposite(), is(false));
    }

    @Test
    @Parameters({"true, true",
            "false, true",
            "true, false",
            "false, false"})
    public void testAnd_calledWithValidCondition(boolean baseCondResult, boolean otherCondResult) {
        when(testCondition.matches(argMock)).thenReturn(baseCondResult);
        Condition<Object> otherCondition = (Condition<Object>) mock(Condition.class);
        when(otherCondition.matches(argMock)).thenReturn(otherCondResult);

        Condition<Object> aggregatedCondition = testCondition.and(otherCondition);
        boolean expectedResult = baseCondResult && otherCondResult;
        assertThat(aggregatedCondition.matches(argMock), equalTo(expectedResult));
        assertThat(aggregatedCondition.isComposite(), is(true));
    }


    @Test
    @Parameters({"someCompCond|true|plainCond|false|(someCompCond) and plainCond",
            "plainCond|false|someCompCond|true|plainCond and (someCompCond)",
            "plainCond|false|plainCond|false|plainCond and plainCond",
            "someCompCond|true|someCompCond|true|(someCompCond) and (someCompCond)",
    })
    public void testAnd_checkDescription(String description1, boolean composite1,
                                         String description2, boolean composite2,
                                         String expectedDescription) {
        when(testCondition.isComposite()).thenReturn(composite1);
        when(testCondition.getDescription()).thenReturn(description1);
        Condition<Object> otherCondition = (Condition<Object>) mock(Condition.class);
        when(otherCondition.isComposite()).thenReturn(composite2);
        when(otherCondition.getDescription()).thenReturn(description2);

        Condition<Object> aggregatedCondition = testCondition.and(otherCondition);
        assertThat(aggregatedCondition.getDescription(), equalTo(expectedDescription));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testOr_nullCondition() {
        Condition<Object> nullableCondition = null;
        testCondition.or(nullableCondition);
    }

    @Test
    @Parameters({"true, true",
            "false, true",
            "true, false",
            "false, false"})
    public void testOr_calledWithValidCondition(boolean baseCondResult, boolean otherCondResult) {
        when(testCondition.matches(argMock)).thenReturn(baseCondResult);
        Condition<Object> otherCondition = (Condition<Object>) mock(Condition.class);
        when(otherCondition.matches(argMock)).thenReturn(otherCondResult);

        Condition<Object> aggregatedCondition = testCondition.or(otherCondition);
        boolean expectedResult = baseCondResult || otherCondResult;
        assertThat(aggregatedCondition.matches(argMock), equalTo(expectedResult));
        assertThat(aggregatedCondition.isComposite(), is(true));
    }


    @Test
    @Parameters({"someCompCond|true|plainCond|false|(someCompCond) or plainCond",
            "plainCond|false|someCompCond|true|plainCond or (someCompCond)",
            "plainCond|false|plainCond|false|plainCond or plainCond",
            "someCompCond|true|someCompCond|true|(someCompCond) or (someCompCond)",
    })
    public void testOr_checkDescription(String description1, boolean composite1,
                                        String description2, boolean composite2,
                                        String expectedDescription) {
        when(testCondition.isComposite()).thenReturn(composite1);
        when(testCondition.getDescription()).thenReturn(description1);
        Condition<Object> otherCondition = (Condition<Object>) mock(Condition.class);
        when(otherCondition.isComposite()).thenReturn(composite2);
        when(otherCondition.getDescription()).thenReturn(description2);

        Condition<Object> aggregatedCondition = testCondition.or(otherCondition);
        assertThat(aggregatedCondition.getDescription(), equalTo(expectedDescription));
    }

    @Test
    @Parameters({"true", "false"})
    public void testNot_calledWithNonNullCondition(boolean result) {
        when(testCondition.matches(argMock)).thenReturn(result);

        Condition<Object> aggregatedCondition = Condition.not(testCondition);
        assertThat(aggregatedCondition.matches(argMock), equalTo(!result));
        assertThat(aggregatedCondition.isComposite(), is(false));
    }

    @Test
    @Parameters({"true|someCond|not (someCond)",
            "false|someCond|not someCond"})
    public void testNot_checkDescription(boolean composite, String description, String expectedDescription) {
        when(testCondition.isComposite()).thenReturn(composite);
        when(testCondition.getDescription()).thenReturn(description);

        Condition<Object> aggregatedCondition = Condition.not(testCondition);
        assertThat(aggregatedCondition.getDescription(), equalTo(expectedDescription));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNot_calledWithNullCondition() {
        Condition.not(null);
    }
}
