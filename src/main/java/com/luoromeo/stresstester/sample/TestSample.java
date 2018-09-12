package com.luoromeo.stresstester.sample;


import com.luoromeo.stresstester.entity.ApiEntity;
import com.luoromeo.stresstester.entity.Parameter;
import com.luoromeo.stresstester.factory.DubboServiceFactory;
import com.taobao.stresstester.StressTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSample {

    /**
     * 采用泛化调用的性能测试
     */
    @Test
    public void genericInvokeStressTest() {

        List<Parameter> parameters = new LinkedList<>();
        parameters.add(new Parameter("java.lang.String", "xiamen"));

        ApiEntity apiEntity = new ApiEntity("findCityByName", "v1", "org.spring.springboot.dubbo.CityDubboService");

        StressTestUtils.testAndPrint(100, 1000, () -> DubboServiceFactory.getInstance().genericDubboInvoke(apiEntity.getName(), parameters, apiEntity));


    }
}
