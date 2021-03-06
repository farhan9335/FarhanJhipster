package com.hcl.mypack.cucumber;

import com.hcl.mypack.HclMonitorToolApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = HclMonitorToolApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
