package com.mcds.observabilty.services.impl;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class YamlConfigParser {

    public static List<String> parseMetricsConfig(String configFilePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = YamlConfigParser.class.getClassLoader().getResourceAsStream(configFilePath)) {
            Map<String, Object> yamlMap = yaml.load(inputStream);
            return (List<String>) yamlMap.get("metrics");
        } catch (Exception e) {
            throw new RuntimeException("Error reading the configuration file", e);
        }
    }

}
