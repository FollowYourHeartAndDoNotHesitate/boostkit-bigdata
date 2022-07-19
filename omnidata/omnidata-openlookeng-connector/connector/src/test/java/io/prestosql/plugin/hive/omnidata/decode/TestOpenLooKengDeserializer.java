/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.prestosql.plugin.hive.omnidata.decode;

import com.huawei.boostkit.omnidata.serialize.OmniDataBlockEncodingSerde;
import io.hetu.core.transport.execution.buffer.PagesSerde;
import io.hetu.core.transport.execution.buffer.PagesSerdeFactory;
import io.hetu.core.transport.execution.buffer.SerializedPage;
import io.prestosql.plugin.hive.omnidata.decode.impl.OpenLooKengDeserializer;
import io.prestosql.spi.Page;
import org.testng.annotations.Test;

import static io.prestosql.plugin.hive.omnidata.decode.DeserializerTestUtils.getTestPageBuilder;
import static org.testng.Assert.assertEquals;

/**
 * Block deserializer test
 *
 * @since 2022-07-19
 */
public class TestOpenLooKengDeserializer {
    /**
     * Test all types
     */
    @Test
    public void testAllSupportTypesDeserializer() {
        Page page = getTestPageBuilder().build();

        // serialize page
        PagesSerdeFactory factory = new PagesSerdeFactory(new OmniDataBlockEncodingSerde(), false);
        PagesSerde pagesSerde = factory.createPagesSerde();
        SerializedPage serializedPage = pagesSerde.serialize(page);

        // deserialize page
        OpenLooKengDeserializer deserializer = new OpenLooKengDeserializer();
        Page deserializedPage = deserializer.deserialize(serializedPage);

        assertEquals(2, deserializedPage.getPositionCount());
        assertEquals(10, deserializedPage.getPositionCount());

        assertEquals(deserializedPage.getBlock(0).getSizeInBytes(), 10);
        assertEquals(deserializedPage.getBlock(1).getSizeInBytes(), 18);
        assertEquals(deserializedPage.getBlock(2).getSizeInBytes(), 4);
        assertEquals(deserializedPage.getBlock(3).getSizeInBytes(), 18);
        assertEquals(deserializedPage.getBlock(4).getSizeInBytes(), 14);
        assertEquals(deserializedPage.getBlock(5).getSizeInBytes(), 6);
        assertEquals(deserializedPage.getBlock(6).getSizeInBytes(), 10);
        assertEquals(deserializedPage.getBlock(7).getSizeInBytes(), 4);
        assertEquals(deserializedPage.getBlock(8).getSizeInBytes(), 10);
        assertEquals(deserializedPage.getBlock(9).getSizeInBytes(), 46);
    }
}
