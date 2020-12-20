package com.journalyourtrade.monolithservice.component;

import com.journalyourtrade.monolithservice.exception.ConfigNotFoundException;
import com.journalyourtrade.monolithservice.model.entity.ServiceInfo;
import com.journalyourtrade.monolithservice.model.entity.SysConfig;
import com.journalyourtrade.monolithservice.repository.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SysConfiguration {

    public static final String JWT_SECRET = "JWT_SECRET";

    private Map<String, String> sysConfigMap = new HashMap<>();

    @Autowired
    public SysConfiguration(SysConfigRepository sysConfigRepository) {
        List<SysConfig> configs = sysConfigRepository.findAllActiveConfigs(Arrays.asList(ServiceInfo.SERVICE_COMMON, ServiceInfo.SERVICE_USER_SERVICE));

        if (configs != null) {
            configs.forEach(config -> {
                sysConfigMap.put(config.getKey(), config.getValue());
            });
        }
    }

    public String getConfigValue(String key) throws ConfigNotFoundException {
        String value = sysConfigMap.get(key);
        if (value != null) {
            return value;
        }
        throw new ConfigNotFoundException();
    }
}
