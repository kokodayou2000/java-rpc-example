package org.example.rpc.spring.reference;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.annotation.RemoteReference;
import org.example.rpc.spring.service.SpringRpcProviderBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ApplicationContextAware 获取上下文的信息
 * BeanClassLoaderAware 获取bean的类装载器
 * BeanFactoryPostProcessor bean 实例化后执行的扩展
 */
@Slf4j
public class SpringRpcReferencePostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;

    private RpcClientProperties rpcClientProperties;

    // 保存发布的引用bean的信息
    private final Map<String, BeanDefinition> rpcRefBeanDefinition = new ConcurrentHashMap<>();

    public SpringRpcReferencePostProcessor(RpcClientProperties properties){
        this.rpcClientProperties = properties;
    }
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 在bean实例化之前执行，判定bean是否被添加了 @RemoteReference 注解，如果添加了注解就注入
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 对 beanDefinitionNames 进行遍历
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            // 根据 beanDefinitionName 获取 beanDefinition
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null){
                // 通过类加载器和类名获取类
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                // 遍历 clazz 这个类的全部字段
                ReflectionUtils.doWithFields(clazz,this::parseRpcReference);
            }
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcRefBeanDefinition.forEach((name,definition)->{
            if (applicationContext.containsBean(name)){
                log.warn("SpringContext already registry bean {}",name);
                return;
            }
            // 注册bean，能被 ApplicationContext 中获取到
            registry.registerBeanDefinition(name,definition);
            log.info("registered rpcReferenceBean {} success ",name);
        });
    }
    private void parseRpcReference(Field field){
        // 如果定义了，就进行解析
        RemoteReference annotation = AnnotationUtils.getAnnotation(field, RemoteReference.class);
        if (annotation != null){
            // 构建的实际对象是 SpringRpcReferenceBean
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .genericBeanDefinition(SpringRpcReferenceBean.class);

            // init
            builder.setInitMethodName("init");
            // field.getType 是获取类 例如 IUserService
            builder.addPropertyValue("interfaceClass",field.getType());
            builder.addPropertyValue("serviceAddress",rpcClientProperties.getServiceAddress());
            builder.addPropertyValue("servicePort",rpcClientProperties.getServicePort());
            BeanDefinition beanDefinition = builder.getBeanDefinition();
            rpcRefBeanDefinition.put(field.getName(),beanDefinition);
        }
    }

}
