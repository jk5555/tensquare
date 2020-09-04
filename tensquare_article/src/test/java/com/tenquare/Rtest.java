package com.tenquare;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * 随便测试
 *
 * @author kun
 */
public class Rtest {


    public static void main(String[] args) {
        LinkedHashMap<String, BigDecimal> map = new LinkedHashMap<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入板块主题（输入“quit”开始计算）");
            String type = sc.nextLine();
            if (isQuit(type)) {
                break;
            }
            System.out.println("请输入购买金额（输入“quit”开始计算）");
            String money = sc.nextLine();
            if (isQuit(money)) {
                break;
            }
            BigDecimal m;
            try {
                m = new BigDecimal(money);
            } catch (NumberFormatException e) {
                System.err.println("请输入正确的金额");
                continue;
            }

            map.merge(type, m, Rtest::sum);
        }
        if (map.isEmpty()) {
            System.out.println("你这啥都没输怎么算？？？");
            System.exit(1);
        }
        System.out.println("请输入总投资本金用于计算比例(总投资本金 = 持仓总金额 + 准备用于投资的站岗金额)");
        BigDecimal sum = sc.nextBigDecimal();
        map.forEach((key, value) -> System.out.println(key + " --- " + value + "    " + (value.divide(sum, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))) + "%"));
    }

    private static BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    private static boolean isQuit(String v) {
        return "quit".equals(v) || "exit".equals(v) || "over".equals(v);
    }
}
//大盘指数 --- 1200    2.00%
//消费白酒 --- 2638.19    3.00%
//医药 --- 5155.46    6.00%
//科技 --- 22533.32    28.00%
//消费 --- 3428.21    4.00%
//传媒 --- 4622.88    6.00%