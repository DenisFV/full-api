package ru.denisfv.fullapi.spring.lifecycle.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.denisfv.fullapi.spring.lifecycle.annotation.MyProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static ru.denisfv.fullapi.spring.lifecycle.SpringLifeCycleApplication.isCGLIB;

public class ProxyBPP implements BeanPostProcessor {
    private Map<String, Class<?>> proxyBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("~ProxyBPP before() for '" + beanName + "'");

        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(MyProxy.class) || beanClass.getSuperclass().isAnnotationPresent(MyProxy.class)) {
            proxyBeans.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("~ProxyBPP after() for '" + beanName + "'");

        Class<?> beanClass = proxyBeans.get(beanName);
        if (beanClass != null) {

            if (isCGLIB) {
                return org.springframework.cglib.proxy.Proxy.newProxyInstance(
                        bean.getClass().getClassLoader(),
                        bean.getClass().getInterfaces(),
                        new org.springframework.cglib.proxy.InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                                System.out.println("-@Proxy with CGLIB");
                                return method.invoke(bean, args);
                            }
                        }
                );
//                return new MethodInterceptor() {
//                    @Override
//                    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//                        System.out.println("-@Proxy with CGLIB");
////                        return method.invoke(bean, args);
////                        return methodProxy.invoke(bean, args);
//                        return methodProxy.invokeSuper(bean, args);
//                    }
//                };
//                return new org.springframework.cglib.proxy.FixedValue() {
//                    @Override
//                    public Object loadObject() throws Exception {
//                        return bean;
//                    }
//                };
            } else {
                return Proxy.newProxyInstance(
                        bean.getClass().getClassLoader(),
                        bean.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                                System.out.println("-@Proxy with Dynamic");
                                return method.invoke(bean, args);
                            }
                        }
                );
            }
        }
        return bean;
    }
}
