package com.example.demo.game;

/**
 * Created by new on 2020/11/17.
 *
 * 只计算 暴击 爆伤 攻击力百分比
 *
 * 花，杯子 最高9分
 * 沙漏，帽子，羽毛 最高8分
 * 总分最高42分
 *
 */
public abstract class BaseSetting {

    public static String HP_Fixed     = "209,239,269,299";
    public static String ATTACK_Fixed = "14,16,18,19";
    public static String FangYu_Fixed = "16,19,21,23";
    public static String JingTong     = "16,19,21,23";
    public static String ChongNeng    = "0.045,0.052,0.058,0.065";
    public static String FangYu       = "0.051,0.058,0.066,0.073";
    public static String HP           = "0.041,0.047,0.053,0.058";

    public static String ATTACK   = "0.041,0.047,0.053,0.058";
    public static String BaoJi    = "0.027,0.031,0.035,0.039";
    public static String BaoShang = "0.054,0.062,0.070,0.078";

    public static double SYW_YuMao    = 311; // 羽毛
    public static double SYW_ATTACK   = 0.466;
    public static double SYW_BaoJi    = 0.311;
    public static double SYW_BaoShang = 0.622;
    public static double Fire_Team    = 0.25;

    public abstract double getBaseAttack();

    public abstract double getBaseBaoji();

    public abstract double getBaseBaoShang();

    public abstract int getSYWMainAttackCount();

    public abstract int getSYWMainBaoJiCount();

    public abstract int getSYWMainBaoShangCount();

    public abstract int getSYWFuCount();

    public abstract double getWeaponAttack();

    public abstract double getWeaponBaoJi();

    public abstract double getWeaponBaoShang();

    public abstract boolean isFireTeam();

    public abstract int getYunQi();

    public void startCalculate() {
        double maxNum = 0;
        int max = getSYWFuCount();
        int maxX = 0, maxY = 0, maxZ = 0, maxM = 0;
        for (int i = 0; i <= max; i++) {
            for (int j = 0; j <= max - i; j++) {
                for (int k = 0; k <= max - i - j; k++) {
                    int l = max - i - j - k;

                    // 检查属性分配是否合理
                    if (i > 30 - getSYWMainAttackCount() || j > 30 - getSYWMainBaoJiCount() || k > 30 - getSYWMainBaoShangCount()) {
                        continue;
                    }
                    if (l > 29) {
                        l = 29;
                    }
                    int xx = max - 38;
                    if (i < xx || j < xx || k < xx || l < xx) {
                        continue;
                    }

                    double cal = calculate(i, j, k, l, false);
                    if (maxNum < cal) {
                        maxNum = cal;
                        maxX = i;
                        maxY = j;
                        maxZ = k;
                        maxM = l;
                    }
                }
            }
        }

        System.out.println("\n最佳配置为攻击词条" + maxX + " 暴击词条" + maxY + " 爆伤词条" + maxZ + " 固定攻击词条" + maxM);
        calculate(maxX, maxY, maxZ, maxM, true);
    }

    public double calculate(int x, int y, int z, int m, boolean outPut) {
        double sumAttack = getBaseAttack() *
            (1 + getWeaponAttack() + getSYWMainAttackCount() * SYW_ATTACK + (Double.parseDouble(ATTACK.split(",")[getYunQi() - 1])) * x +
                (isFireTeam() ? Fire_Team : 0)) + SYW_YuMao + (Double.parseDouble(ATTACK_Fixed.split(",")[getYunQi() - 1])) * m;
        double sumBaoji =
            getBaseBaoji() + getWeaponBaoJi() + getSYWMainBaoJiCount() * SYW_BaoJi + (Double.parseDouble(BaoJi.split(",")[getYunQi() - 1])) * y;
        double sumBaoShang = getBaseBaoShang() + getWeaponBaoShang() + getSYWMainBaoShangCount() * SYW_BaoShang +
            (Double.parseDouble(BaoShang.split(",")[getYunQi() - 1])) * z;
        return getRecord(sumAttack, sumBaoji, sumBaoShang, outPut);
    }

    public double getRecord(double sumAttack, double sumBaoji, double sumBaoShang, boolean outPut) {
        if (sumBaoji > 1) {
            sumBaoji = 1;
        }
        double sum = sumAttack * (1 + sumBaoji * sumBaoShang);
        if (outPut) {
            System.out.println("攻击力：" + String.format("%.2f", sumAttack)  //
                                   + " 暴击：" + String.format("%.2f", sumBaoji) //
                                   + " 爆伤" + String.format("%.2f", sumBaoShang) //
                                   + " 总分" + String.format("%.2f", sum));//
        }
        return sum;
    }
}
