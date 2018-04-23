package com.warchaser.daggertest.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.Moshi;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static java.util.Collections.unmodifiableSet;

public class CustomConverterFactory extends Converter.Factory {
    public static CustomConverterFactory create() {
        return create(new Moshi.Builder().add(new Parse2StringAdapter()).build());
    }

    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static CustomConverterFactory create(Moshi moshi) {
        if (moshi == null) throw new NullPointerException("moshi == null");
        return new CustomConverterFactory(moshi, false, false, false);
    }

    private final Moshi moshi;
    private final boolean lenient;
    private final boolean failOnUnknown;
    private final boolean serializeNulls;

    private CustomConverterFactory(Moshi moshi, boolean lenient, boolean failOnUnknown,
                                  boolean serializeNulls) {
        this.moshi = moshi;
        this.lenient = lenient;
        this.failOnUnknown = failOnUnknown;
        this.serializeNulls = serializeNulls;
    }

    public CustomConverterFactory asLenient() {
        return new CustomConverterFactory(moshi, true, failOnUnknown, serializeNulls);
    }

    public CustomConverterFactory failOnUnknown() {
        return new CustomConverterFactory(moshi, lenient, true, serializeNulls);
    }

    public CustomConverterFactory withNullSerialization() {
        return new CustomConverterFactory(moshi, lenient, failOnUnknown, true);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        JsonAdapter<?> adapter = moshi.adapter(type, jsonAnnotations(annotations));
        if (lenient) {
            adapter = adapter.lenient();
        }
        if (failOnUnknown) {
            adapter = adapter.failOnUnknown();
        }
        if (serializeNulls) {
            adapter = adapter.serializeNulls();
        }
        return new CustomeResponseBodyConverter<>(adapter);
    }

    @Override public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                                    Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        JsonAdapter<?> adapter = moshi.adapter(type, jsonAnnotations(parameterAnnotations));
        if (lenient) {
            adapter = adapter.lenient();
        }
        if (failOnUnknown) {
            adapter = adapter.failOnUnknown();
        }
        if (serializeNulls) {
            adapter = adapter.serializeNulls();
        }
        return new CustomRequestBodyConverter<>(adapter);
    }

    private static Set<? extends Annotation> jsonAnnotations(Annotation[] annotations) {
        Set<Annotation> result = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(JsonQualifier.class)) {
                if (result == null) result = new LinkedHashSet<>();
                result.add(annotation);
            }
        }
        return result != null ? unmodifiableSet(result) : Collections.<Annotation>emptySet();
    }
}
