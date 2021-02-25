package com.groupdocs.viewer.sample;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import static com.groupdocs.viewer.sample.Utilities.*;

public class BaseJUnit {

    @BeforeClass
    public static void beforeClass() {
        applyLicense();
        cleanOutput();
        initOutput();
    }

    @AfterClass
    public static void afterClass() {
    }
}
