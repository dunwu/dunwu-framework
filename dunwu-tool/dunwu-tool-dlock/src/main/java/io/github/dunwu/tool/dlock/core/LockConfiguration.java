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
package io.github.dunwu.tool.dlock.core;

import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Lock configuration.
 */
public class LockConfiguration {

    private final String name;

    /**
     * The lock is held until this instant, after that it's automatically released (the process holding it has most
     * likely died without releasing the lock) Can be ignored by providers which can detect dead processes (like
     * Zookeeper)
     */
    private final Instant lockMaxTime;

    /**
     * The lock will be held until this time even if the task holding the lock finishes earlier.
     */
    private final Instant lockMinTime;

    public LockConfiguration(@NotNull String name, @NotNull Instant lockMaxTime) {
        this(name, lockMaxTime, Instant.now());
    }

    public LockConfiguration(@NotNull String name, @NotNull Instant lockMaxTime, @NotNull Instant lockMinTime) {
        this.name = Objects.requireNonNull(name);
        this.lockMaxTime = Objects.requireNonNull(lockMaxTime);
        this.lockMinTime = Objects.requireNonNull(lockMinTime);
        if (lockMinTime.isAfter(lockMaxTime)) {
            throw new IllegalArgumentException("lockAtMostUntil is before lockAtLeastUntil for lock '" + name + "'.");
        }
        if (lockMaxTime.isBefore(Instant.now())) {
            throw new IllegalArgumentException("lockAtMostUntil is in the past for lock '" + name + "'.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("lock name can not be empty");
        }
    }

    @Override
    public String toString() {
        return "LockConfiguration{" +
            "name='" + name + '\'' +
            ", lockAtMostUntil=" + lockMaxTime +
            ", lockAtLeastUntil=" + lockMinTime +
            '}';
    }

    public String getName() {
        return name;
    }

    public Instant getLockMaxTime() {
        return lockMaxTime;
    }

    public Instant getLockMinTime() {
        return lockMinTime;
    }

    /**
     * Returns either now or lockAtLeastUntil whichever is later.
     */
    public Instant getUnlockTime() {
        Instant now = Instant.now();
        return lockMinTime.isAfter(now) ? lockMinTime : now;
    }

}
