package io.p2vman.neoperipheral.util;

@FunctionalInterface
public interface CallLimiter {
    boolean tryCall(int now);

    class EmptyCallLimiter implements CallLimiter {
        @Override
        public boolean tryCall(int now) {
            return true;
        }
    }

    class RingCallLimiter implements CallLimiter {
        private final int maxCalls;
        private final int window;

        private final int[] ticks;
        private int head = 0;
        private int size = 0;

        public RingCallLimiter(int maxCalls, int windowTicks) {
            this.maxCalls = maxCalls;
            this.window = windowTicks;
            this.ticks = new int[maxCalls];
        }

        @Override
        public boolean tryCall(int now) {
            while (size > 0) {
                int oldest = ticks[head];

                if (now - oldest < window) break;

                head++;
                if (head == maxCalls) head = 0;
                size--;
            }

            if (size >= maxCalls) {
                return false;
            }

            int tail = head + size;
            if (tail >= maxCalls) tail -= maxCalls;

            ticks[tail] = now;
            size++;
            return true;
        }
    }

    class BucketCallLimiter implements CallLimiter {
        private final int window;
        private final int maxCalls;

        private final int[] buckets;
        private int lastTick = -1;
        private int sum = 0;

        public BucketCallLimiter(int maxCalls, int window) {
            this.maxCalls = maxCalls;
            this.window = window;
            this.buckets = new int[window];
        }

        @Override
        public boolean tryCall(int now) {
            int idx = now % window;

            if (now != lastTick) {
                sum -= buckets[idx];
                buckets[idx] = 0;
                lastTick = now;
            }

            if (sum >= maxCalls) return false;

            buckets[idx]++;
            sum++;
            return true;
        }
    }
}
