package com.rubincomputers.sb_demo01.data;


import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Factory for creating test matchers.
 * <p>
 * Comparing actual and expected objects via AssertJ
 * Support converting json MvcResult to objects for comparation.
 */
public class MatcherFactory {


    public static <T> Matcher<T> usingAssertions(Class<T> clazz, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
        return new Matcher<>(clazz, assertion, iterableAssertion);
    }

    public static <T> Matcher<T> usingIgnoringFieldsComparator(Class<T> clazz, String... fieldsToIgnore) {
        return usingAssertions(clazz,
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(e),
                (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(e));
    }

    public static class Matcher<T> {
        private final Class<T> clazz;
        private final BiConsumer<T, T> assertion;
        private final BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion;

        private Matcher(Class<T> clazz, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
            this.clazz = clazz;
            this.assertion = assertion;
            this.iterableAssertion = iterableAssertion;
        }

        @SafeVarargs
        public final ResultMatcher contentJson(T... expected) {
            return contentJson(null, List.of(expected));
        }

        @SafeVarargs
        public final ResultMatcher contentJson(String resultFromField, T... expected) {
            return contentJson(resultFromField, List.of(expected));
        }

        public ResultMatcher contentJson(Iterable<T> expected) {
            return contentJson(null, expected);
        }

        public ResultMatcher contentJson(String resultFromField, Iterable<T> expected) {
            return result -> assertMatch(JsonUtil.readValues(getContent(result, resultFromField), clazz), expected);
            //return result -> assertMatch(JsonUtil.readValues(getContentForPage(result), clazz), expected);
        }

        @SafeVarargs
        public final void assertMatch(Iterable<T> actual, T... expected) {
            assertMatch(actual, List.of(expected));
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            iterableAssertion.accept(actual, expected);
        }

        public T readFromJson(ResultActions action) throws UnsupportedEncodingException {
            return JsonUtil.readValue(getContent(action.andReturn()), clazz);
        }

        private static String getContent(MvcResult result) throws UnsupportedEncodingException {
            return getContent(result, "");
        }

        private static String getContent(MvcResult result, String resultFromField) throws UnsupportedEncodingException {
            String contentAsString = result.getResponse().getContentAsString();
            if (!StringUtils.hasText(resultFromField)) {
                return contentAsString;
            }
            JSONObject data = null;
            try {
                data = new JSONObject(contentAsString);
                return data.getString(resultFromField);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        public void assertMatch(T actual, T expected) {
            assertion.accept(actual, expected);
        }
    }
}
