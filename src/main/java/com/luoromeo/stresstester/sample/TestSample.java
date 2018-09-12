package com.luoromeo.stresstester.sample;


import com.luoromeo.stresstester.entity.ApiEntity;
import com.luoromeo.stresstester.factory.DubboServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSample {

    @Test
    public void genericInvokeStressTest() {

        List<Map<String, Object>> parameters = new LinkedList<>();

        Map<String, Object> cityName =new HashMap<>();
        cityName.put("ParamType", "java.lang.String");
        cityName.put("Object", "xiamen");
        parameters.add(cityName);

        ApiEntity apiEntity = new ApiEntity();
        apiEntity.setName("findCityByName");
        apiEntity.setApiVersion("v1");
        apiEntity.setDubboApiName("org.spring.springboot.dubbo.CityDubboService");

        DubboServiceFactory.getInstance().genericDubboInvoke(apiEntity.getName(), parameters, apiEntity);

    }
}
