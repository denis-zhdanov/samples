package org.denis.sample.spring.test.mockbehavior;

import org.denis.sample.spring.test.mockbehavior.mock.DelegatingMockUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class TestApplication {

    private static final Set<Class<?>> INTERFACES_TO_MOCK = new HashSet<>(
            Collections.singletonList(Map.class)
    );

    @Bean
    public BeanPostProcessor mockPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                for (Class<?> intf : INTERFACES_TO_MOCK) {
                    if (intf.isInstance(bean)) {
                        return DelegatingMockUtil.createMock(bean);
                    }
                }
                return bean;
            }
        };
    }
}
