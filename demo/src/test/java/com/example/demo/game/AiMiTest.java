package com.example.demo.game;

/**
 * Created by new on 2020/11/17.
 *
 * 菲谢尔
 */
public class AiMiTest extends BaseSetting {

    public static double BaseAttack   = 800; // 基础攻击力(包括武器)
    public static double BaseBaoji    = 0.05; // 人物基础暴击（包括天赋）
    public static double BaseBaoShang = 0.5; // 人物基础爆伤（包括天赋）
    public static int    YunQi        = 2; // 设定运气 1倒霉 2略差 3略好 4超好

    public static double wqx = 0; // 武器附加攻击力百分比
    public static double wqy = 0.3; // 武器附加暴击
    public static double wqz = 0; // 武器附加爆伤

    public static boolean isFireTeam   = false;

    public static int SYWMainAttackCount   = 1;
    public static int SYWMainBaoJiCount    = 0;
    public static int SYWMainBaoShangCount = 1;

    public static int max = 25; // 圣遗物有效副词条数目 一般是 0-42 之间

    public static void main(String[] args) {
        AiMiTest test = new AiMiTest();
        test.startCalculate();

        System.out.println("\n当前总分为");
        test.getRecord(1800, 0.4, 1, true);
    }

    @Override
    public double getBaseAttack() {
        return BaseAttack;
    }

    @Override
    public double getBaseBaoji() {
        return BaseBaoji;
    }

    @Override
    public double getBaseBaoShang() {
        return BaseBaoShang;
    }

    @Override
    public int getSYWMainAttackCount() {
        return SYWMainAttackCount;
    }

    @Override
    public int getSYWMainBaoJiCount() {
        return SYWMainBaoJiCount;
    }

    @Override
    public int getSYWMainBaoShangCount() {
        return SYWMainBaoShangCount;
    }

    @Override
    public int getSYWFuCount() {
        return max;
    }

    @Override
    public double getWeaponAttack() {
        return wqx;
    }

    @Override
    public double getWeaponBaoJi() {
        return wqy;
    }

    @Override
    public double getWeaponBaoShang() {
        return wqz;
    }

    @Override
    public boolean isFireTeam() {
        return isFireTeam;
    }

    @Override
    public int getYunQi() {
        return YunQi;
    }
}
