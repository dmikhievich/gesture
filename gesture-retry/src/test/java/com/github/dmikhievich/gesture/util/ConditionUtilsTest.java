package com.github.dmikhievich.gesture.util;

import com.github.dmikhievich.gesture.condition.Condition;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Dzmitry_Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class ConditionUtilsTest {

    @Test
    @Parameters
    public void testBuildCompositeDescription(Condition first, String keyword, Condition second, String expectedDescription) {
        assertThat(ConditionUtils.buildCompositeDescription(first, keyword, second), equalTo(expectedDescription));
    }

    private Object parametersForTestBuildCompositeDescription() {
        return $(
                $(createConditionMock(false, "desc1"), "xor", createConditionMock(false, "desc2"), "desc1 xor desc2"),
                $(createConditionMock(true, "desc1"), "xor", createConditionMock(true, "desc2"), "(desc1) xor (desc2)"),
                $(createConditionMock(false, "desc1"), "xor", createConditionMock(true, "desc2"), "desc1 xor (desc2)"),
                $(createConditionMock(true, "desc1"), "xor", createConditionMock(false, "desc2"), "(desc1) xor desc2")
        );
    }

    public static Object[] $(Object... params) {
        return params;
    }

    private static Condition createConditionMock(boolean isComposite, String description) {
        Condition condition = mock(Condition.class);
        when(condition.isComposite()).thenReturn(isComposite);
        when(condition.getDescription()).thenReturn(description);
        return condition;
    }
}