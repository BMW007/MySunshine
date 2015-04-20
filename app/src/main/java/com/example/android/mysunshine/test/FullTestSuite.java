package com.example.android.mysunshine.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by Fzu_Xmu_bmw on 2015/4/20.
 * Email£º wmb007@126.com
 * MicroBlog:  http://weibo.com/wangmb007
 */
public class FullTestSuite extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }

    public FullTestSuite() {
        super();
    }
}