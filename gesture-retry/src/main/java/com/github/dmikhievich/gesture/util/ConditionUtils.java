package com.github.dmikhievich.gesture.util;

import com.github.dmikhievich.gesture.condition.Condition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by Dzmitry_Mikhievich
 */
public final class ConditionUtils {

    private ConditionUtils() {
    }

    public static <T> String buildCompositeDescription(@Nullable Condition<T> first,
                                                       String keyword,
                                                       @Nullable Condition<T> second) {
        checkArgument(isNotEmpty(keyword), "Composition keyword can't be empty");
        StringBuilder description = new StringBuilder();
        if (first != null) {
            description.append(prepareDescription(first)).append(SPACE);
        }
        description.append(keyword);
        if (second != null) {
            description.append(SPACE).append(prepareDescription(second));
        }
        return description.toString();
    }

    private static String prepareDescription(@Nonnull Condition condition) {
        String description = condition.getDescription();
        return condition.isComposite() ? "(" + description + ")" : description;
    }
}
