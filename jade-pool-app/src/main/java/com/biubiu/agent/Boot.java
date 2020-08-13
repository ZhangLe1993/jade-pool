package com.biubiu.agent;

import com.biubiu.agent.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Boot implements CommandLineRunner {

    @Autowired
    private MetaService metaService;

    @Override
    public void run(String... args) throws Exception {
        /*SourceCreate sourceCreate = new SourceCreate();
        sourceCreate.setName("本机");
        sourceCreate.setDescription("本机");
        sourceCreate.setType("jdbc");
        SourceConfig sourceConfig = new SourceConfig();
        sourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306");
        sourceConfig.setUsername("root");
        sourceConfig.setPassword("root");
        sourceCreate.setConfig(sourceConfig);
        metaService.createSource(sourceCreate);*/

        // DBTables dbTables = metaService.getSourceTables(1L, "proxy");
        // System.out.println(dbTables);
    }
}
