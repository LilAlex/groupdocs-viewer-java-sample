package com.groupdocs.viewer.sample;

import org.junit.After;
import org.junit.Before;

import static com.groupdocs.viewer.sample.Utilities.*;

public class BaseJUnit {

    @Before
    public void before() {
        applyLicense();
        cleanOutput();
        initOutput();
    }

    @After
    public void after() {
    }
}
