package com.sera;

import com.sera.helper.UrlHelper;
import junit.framework.Assert;
import org.junit.Test;

/**
 * test urlhelper
 * Created by wangqing on 16/7/12.
 */
public class TestUrlHelper {

    @Test
    public void testUrlIsValid(){
        UrlHelper dd = new UrlHelper();
        Assert.assertTrue(dd.isValid("http://cn.bing.com/"));
    }

}
