package com.deep.common.convert;

import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.ReflectionUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Converter interface for input DTO/Param.
 *
 * @author Deep
 * @date 2022/3/17
 */
public interface InputConverter<D> {

    /**
     * Convert to domain.(shallow)
     *
     * @return new domain with same value(not null)
     */
    @SuppressWarnings("unchecked")
    default D convertTo() {
        // Get parameterized type
        ParameterizedType currentType = parameterizedType();

        // Assert not equal null
        Objects.requireNonNull(currentType,
                "无法获取实际类型，因为参数化类型为空");

        Class<D> domainClass = (Class<D>) currentType.getActualTypeArguments()[0];

        return BeanUtils.transformFrom(this, domainClass);
    }

    /**
     * Update a domain by dto.(shallow)
     *
     * @param domain updated domain
     */
    default void update(D domain) {
        BeanUtils.updateProperties(this, domain);
    }

    /**
     * Get parameterized type.
     *
     * @return parameterized type or null.
     */
    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}
