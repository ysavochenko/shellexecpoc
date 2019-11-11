package com.ysavoche.test.listeners;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public abstract class AbstractSpringTestListener implements TestExecutionListener {

    static TestContext context;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        context = testContext;
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }

}
