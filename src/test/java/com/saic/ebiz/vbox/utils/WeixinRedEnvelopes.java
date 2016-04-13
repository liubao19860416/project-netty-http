package com.saic.ebiz.vbox.utils;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

/**
 * 微信红包生成算法
 * 
 * 微信红包有多种玩法，其中一种就是指定金额、人数(m)，拆红包的人收到的金额是随机，收到的金额保留两位小数，至少有一分，所有人的红包加起来等于指定金额。
 * 
 * 我想到一种做法就是：将指定金额放大100倍，也就是变成单位“分”，这时金额就是整数了，设为n，从1到n这个整数区间随机抽取m(是人数)个整数，
 * 这样1到n的整数区间就分成了m或m+1(这种情况，最后的两 个区间合成一个区间)个区间。
 * 
 * 比如输入金额1.00元，人数m=3，n=100 *
 * 1。从1到100之间随机选中的三个整数为15、42、88，这时产生了m+1个区间，最后的两个区间合并，最终得到三个区间
 * ：1--15，16--42，43--100，根据这三个区间计算
 * 金额为0.15、0.42-0.15=0.27、1.00-0.42=0.58，最终得到的随机金额为：0.15、0.27、0.58。
 * 
 * 抽样算法是参考《编程珠玑》这本书中的。java代码如下，使用BigDecimal便于精度控制，使用junit进行了简单测试
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年2月22日
 * 
 */
public class WeixinRedEnvelopes {

    /**
     * 金额的精度：保留两位小数
     */
    private static int scale = 2;

    public static void main(String[] args) {
        int numberOfPeople = 10;
        BigDecimal money = new BigDecimal(10);
        System.out.println(JSON
                .toJSONString(generalPlay(money, numberOfPeople)));
    }

    /**
     * 普通玩法
     * <p>
     * 输入金额、人数，随机输出金额列表，例如：<br>
     * 输入：1.0 3<br>
     * 输出：0.11 0.37 0.52
     * </p>
     * 
     * @return
     */
    public static BigDecimal[] generalPlay(final BigDecimal money,
            int numberOfPeople) {
        // 检验参数的合法性
        checkGeneralPlayValidParam(money, numberOfPeople);

        // 将金额转化为分，就能转化为整数
        BigDecimal divisor = new BigDecimal(100);
        int n = money.multiply(divisor).intValue();
        // 从1--n之间随机抽出numberOfPeople个数。其实这里就是一个抽样问题
        BigDecimal[] result = new BigDecimal[numberOfPeople];
        int m = numberOfPeople;
        int index = 0;
        for (int i = 0; i < n; i++) {
            long bigrand = bigRand();
            if (bigrand % (n - i) < m) {
                result[index++] = new BigDecimal(i + 1).divide(divisor, scale,
                        BigDecimal.ROUND_HALF_UP);
                m--;
            }
        }
        // 分区间处理
        for (int i = numberOfPeople - 1; i > 0; i--) {
            if (i == (numberOfPeople - 1)) {
                // 最后一个就取剩余的
                result[i] = money.subtract(result[i - 1]);
            } else {
                result[i] = result[i].subtract(result[i - 1]);
            }
        }
        return result;
    }

    /**
     * 产生一个很大的随机整数
     * 
     * @return
     */
    private static long bigRand() {
        long bigrand = (long) (Math.random() * Integer.MAX_VALUE)
                + Integer.MAX_VALUE;

        return bigrand;
    }

    /**
     * 检查方法{@link #generalPlay(BigDecimal, int)}参数的有效性
     */
    private static void checkGeneralPlayValidParam(final BigDecimal money,
            int numberOfPeople) {
        // 确保人数大于等于1
        if (numberOfPeople < 1) {
            throw new RuntimeException("人数 " + numberOfPeople + " 应该大于0！");
        }
        // 确保每个人至少能分到0.01元
        if (money.compareTo(new BigDecimal("0.01").multiply(new BigDecimal(
                numberOfPeople))) < 0) {
            throw new RuntimeException("人数太多，钱不够分！");
        }
        // 确保money只有两位小数
        if (money.scale() > scale) {
            throw new RuntimeException("金额数据不对，最多保留两位小数！");
        }
    }
}