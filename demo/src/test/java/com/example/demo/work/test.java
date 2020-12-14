package com.example.demo.work;

import java.math.BigDecimal;

/**
 * Created by new on 2020/12/11.
 */
public class test {

    public static void main(String[] args) {
        double Σb = 0.52; // 矩形截面的相对受限受压区高度
        double fc = 14300; // 混凝土轴心抗压强度设计值
        double fy = 360000; // 纵向钢筋抗拉强度设计值

        double α = 0.3; // 受压区混凝土截面面积的圆心角与2π的比值
        double αt; // 纵向受拉钢筋截面面积:全部纵向钢筋截面面积。α>0.625 时 αt=0
        double αs = 0.25; // 受拉钢筋的圆心角与2π的比值。宜取 1/6 - 1/3 通常可取 0.25
        double α1s = 0.15; // 受压钢筋的圆心角与2π的比值。宜取 α1s<=0.5α

        double r = 0.75; // 桩半径
        double rs = 0.65; // 纵向钢筋重心所在圆周的半径

        double M = 3365; // 桩弯矩设计值

        double A = Math.PI * r * r; // 桩截面积
        double As; // 全部纵向钢筋的截面面积
        //double Asr; // 纵向受拉钢筋的截面面积
        double A1sr; // 纵向受压钢筋的截面面积

        if (α < (1d / 3.5)) {
            System.out.println("α值(" + α + ")不满足要求 α>=1/3.5 ！");
            return;
        }
        for (int i = 0; i < 10000; i++) {
            double Asr = 0.00001d * i;

            double Num_2πα = 2 * Math.PI * α;
            A1sr = Asr - (α * fc * A * (1 - Math.sin(Num_2πα) / Num_2πα) / fy);

            double tempValue1 = fc * A * r * 2 / 3 * ((3 * Math.sin(Math.PI * α) - Math.sin(3 * (Math.PI * α))) / 4) / Math.PI //
                + fy * Asr * rs * Math.sin(Math.PI * αs) / (Math.PI * αs) //
                + fy * A1sr * rs * Math.sin(Math.PI * α1s) / (Math.PI * α1s);

            double tempValue21 = Math.cos(Math.PI * α);
            double tempValue22 = 1 - (1 + rs / r * (Math.cos(Math.PI * αs))) * Σb;

            if (M <= tempValue1 && tempValue21 >= tempValue22) {
                System.out.println("Asr= " + String.format("%.7f", Asr) + "; A1sr= " + String.format("%.7f", A1sr) + " 符合条件");
                break;
            }
        }
    }
}
