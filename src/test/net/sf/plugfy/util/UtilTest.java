/*
 *  Copyright 2013
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may use this file in compliance with the Apache License, Version 2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.plugfy.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test
 *
 * @author hendrik
 */
public class UtilTest {

    /**
     * tests find
     */
    @Test
    public void testFind() {
        String[] list = new String[] {"a", "b"};
        assertThat(Util.find(list, new EqualPredicate<String>("a")), equalTo("a"));
        assertThat(Util.find(list, new EqualPredicate<String>("c")), nullValue());
    }
}
