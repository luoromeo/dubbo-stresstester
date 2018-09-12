package com.luoromeo.stresstester.factory;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.luoromeo.stresstester.entity.ApiEntity;
import com.luoromeo.stresstester.entity.Parameter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * dubbo服务工厂
 */
public class DubboServiceFactory {


    private ApplicationConfig application;

    private RegistryConfig registry;

    private static final Map<String, GenericService> CACHE = new ConcurrentHashMap<>();

    private static class SingletonHolder {
        private static final DubboServiceFactory INSTANCE = new DubboServiceFactory();
    }

    private DubboServiceFactory() {
        Properties prop = new Properties();
        ClassLoader loader = DubboServiceFactory.class.getClassLoader();

        try (InputStream in = loader.getResourceAsStream("application.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(prop.getProperty("dubbo.application.name"));
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(prop.getProperty("dubbo.application.registry"));

        this.application = applicationConfig;
        this.registry = registryConfig;
    }

    public static DubboServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 泛化调用接口
     * @param method
     * @param parameters
     * @param apiEntity
     * @return
     */
    public Object genericDubboInvoke(String method, List<Parameter> parameters, ApiEntity apiEntity) {
        if (method == null) {
            throw new RuntimeException("method should not null !");
        }

        String cacheKey = getApiFullName(apiEntity.getName(), apiEntity.getApiVersion());
        GenericService genericService = CACHE.get(cacheKey);
        if (genericService == null) {
            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            //弱类型接口名
            reference.setInterface(apiEntity.getDubboApiName());
            //启用泛化调用
            reference.setGeneric(true);
            reference.setTimeout(60 * 1000);

            if (null == apiEntity.getDubboApiVersion() || "".equals(apiEntity.getDubboApiVersion())) {
                reference.setVersion("1.0.0");
            } else {
                reference.setVersion(apiEntity.getDubboApiVersion());
            }

            reference.setApplication(application);
            reference.setRegistry(registry);
            genericService = reference.get();
            CACHE.put(cacheKey, genericService);
        }

        try {
            int len = parameters.size();
            String[] invokeParamTypes = new String[len];
            Object[] invokeParams = new Object[len];
            for (int i = 0; i < len; i++) {
                invokeParamTypes[i] = parameters.get(i).getParamType() + "";
                invokeParams[i] = parameters.get(i).getObject();
            }

            return genericService.$invoke(method, invokeParamTypes, invokeParams);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getApiFullName(String apiName, String apiVersion) {

        return apiName +
                "_" +
                apiVersion;
    }
}
