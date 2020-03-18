/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.github.dunwu.dlock.core;

import javax.validation.constraints.NotNull;

/**
 * Asserts lock presence. The Spring ecosystem is so complicated, so one can not be sure that the lock is applied. This
 * class makes sure that the task is indeed locked.
 * <p>
 * If you use AOP with Kotlin, it does not have to work due to final methods, if you use TaskExecutor wrapper, it can be
 * broken by Sleuth,.
 */
public class LockAssert {

    private static final ThreadLocal<String> CURRENT_LOCK_NAME = ThreadLocal.withInitial(() -> null);

    /**
     * Throws an exception if the lock is not present.
     */
    public static void assertLocked() {
        if (CURRENT_LOCK_NAME.get() == null) {
            throw new IllegalStateException("The task is not locked.");
        }
    }

    static void startLock(String name) {
        CURRENT_LOCK_NAME.set(name);
    }

    static boolean alreadyLockedBy(@NotNull String name) {
        return name.equals(CURRENT_LOCK_NAME.get());
    }

    static void endLock() {
        CURRENT_LOCK_NAME.remove();
    }

}
