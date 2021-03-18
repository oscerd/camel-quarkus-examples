/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.timer;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.awaitility.Awaitility.await;

@QuarkusTest
public class TimerLogSpringTest {

    @Test
    public void testTimerLogSpring() {
        File quarkusLogFile = getQuarkusLogFile();
        await().atMost(10L, TimeUnit.SECONDS).pollDelay(1, TimeUnit.SECONDS).until(() -> {
            String log = FileUtils.readFileToString(quarkusLogFile, StandardCharsets.UTF_8);
            return log.contains("Incremented the counter");
        });
    }

    private File getQuarkusLogFile() {
        String pathPrefix = "target";
        String packageType = System.getProperty("quarkus.package.type");
        if (packageType != null && packageType.equals("native")) {
            pathPrefix += "/target";
        }
        return new File(pathPrefix + "/quarkus.log");
    }
}
