//package io.github.dunwu.web.util;
//
//import java.util.concurrent.atomic.AtomicLong;
//
//import static com.alibaba.fastjson.util.IOUtils.DIGITS;
//
///**
// * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
// * @since 2019-08-02
// */
//public class ShortUrlUtil2 {
//
//    private static AtomicLong sequence = new AtomicLong(0);
//
//    public static String shorten(String longUrl) {
//        long myseq = sequence.incrementAndGet();
//        String shortUrl = to62RadixString(myseq);
//        return shortUrl;
//    }
//
//    public static String to62RadixString(long seq) {
//        StringBuilder sBuilder = new StringBuilder();
//        while (true) {
//            int remainder = (int) (seq % 62);
//            sBuilder.append(DIGITS[remainder]);
//            seq = seq / 62;
//            if (seq == 0) {
//                break;
//            }
//        }
//        return sBuilder.toString();
//    }
//
//    public static void main(String[] args) {
//        String url = ShortUrlUtil2.shorten(
//            "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=00013449_oem_dg&wd=Java%20%E7%9F%AD%E5%9C%B0"
//                + "%E5%9D%80%E7%AE%97%E6%B3%95&oq=%25E7%259F%25AD%25E5%259C%25B0%25E5%259D%2580%25E7%25AE%2597%25E6"
//                + "%25B3%2595&rsv_pq=f61b373c000292c7&rsv_t"
//                + "=b1165DKwZnnUOQkRNhfn6LLIQRMwXFYHvCsoJnsBu3WfoELAoxV7ARXX7QibO9tyv2TSaq2x&rqlang=cn&rsv_enter=0"
//                + "&rsv_dl=tb&inputT=1547&rsv_sug3=66&rsv_sug1=50&rsv_sug7=000&rsv_sug2=0&rsv_sug4=2104&rsv_sug=2");
//        System.out.println("url = [" + url + "]");
//    }
//}
