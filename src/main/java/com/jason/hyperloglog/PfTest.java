package com.jason.hyperloglog;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <一句话简单描述>
 * <详细介绍>
 *
 * @author lihaitao on 2019/9/22
 */
//public class PfTest {
//
//    static class BitKeeper {
//        private int maxbits;
//
//        public void random(long value) {
//            // 查询低位0的个数
//            int bits = lowZeros(value);
//            // 维护最大bits值
//            if (bits > this.maxbits) {
//                this.maxbits = bits;
//            }
//        }
//
//
//        private int lowZeros(long value) {
//            int i = 1;
//            for (; i < 32; i++) {
//                if (value >> i << i != value) {
//                    break;
//                }
//            }
//            return i - 1;
//        }
//    }
//
//    static class Experiment {
//        private int n;
//        private int k;
//        private BitKeeper[] keepers;
//
//        public Experiment(int n) {
//            this(n, 1024);
//        }
//
//        public Experiment(int n, int k) {
//            this.n = n;
//            this.k = k;
//            this.keepers = new BitKeeper[k];
//            for (int i = 0; i < k; i++) {
//                this.keepers[i] = new BitKeeper();
//            }
//        }
//
//        public void work() {
//            for (int i = 0; i < this.n; i++) {
//                long m = ThreadLocalRandom.current().nextLong(1L << 32);
//                BitKeeper keeper = keepers[(int) (((m & 0xfff0000) >> 16) % keepers.length)];
//                keeper.random(m);
//            }
//        }
//
//        public double estimate() {
//            double sumbitsInverse = 0.0;
//            for (BitKeeper keeper : keepers) {
//                sumbitsInverse += 1.0 / (float) keeper.maxbits;
//            }
//            double avgBits = (float) keepers.length / sumbitsInverse;
//            return Math.pow(2, avgBits) * this.k;
//        }
//    }
//
//    public static void main(String[] args) {
//        for (int i = 100000; i < 1000000; i += 100000) {
//            Experiment exp = new Experiment(i);
//            exp.work();
//            double est = exp.estimate();
//            System.out.printf("%d %.2f %.2f\n", i, est, Math.abs(est - i) / i);
//        }
//    }
//
//}
public class PfTest {

    static class BitKeeper {
        private int maxbits;

        public void random() {
            // long value = new Object().hashCode() ^ (2 << 32);
            long value = ThreadLocalRandom.current().nextLong(2L << 32);
            int bits = lowZeros(value);
//            System.out.println(String.format("random-->%d, %d", value, bits ));
            if (bits > this.maxbits) {
                this.maxbits = bits;
            }
        }

        private int lowZeros(long value) {
            int i = 1;
            for (; i < 32; i++) {
                if (value >> i << i != value) {
                    break;
                }
            }
            return i - 1;
        }
    }

    static class Experiment {
        private int n;
        private BitKeeper keeper;

        private List<String> special = new LinkedList<>();

        public Experiment(int n) {
            this.n = n;
            this.keeper = new BitKeeper();
        }

        public void work() {
            for (int i = 0; i < n; i++) {
                this.keeper.random();
            }
        }

        public void debug() {
            double v = Math.log(this.n) / Math.log(2);
            String format = String.format("%d %.2f %d\n", this.n, v, this.keeper.maxbits);
            System.out.println(format);
            // 如果差距较大，那么就摘出来
            if (Math.abs(v - this.keeper.maxbits) > 3) {
                special.add(format);
            }
        }
    }

    public static void main(String[] args) {
        List<String> special = new LinkedList<>();
//        for (int i = 1; i < 10; i += 1) {
            Experiment exp = new Experiment(100000000);
            exp.work();
            exp.debug();
            special.addAll(exp.special);
//        }

        for (String s : special) {
            System.out.println("特殊： " + s);
        }
        System.out.println("special size :" + special.size());
    }

}
