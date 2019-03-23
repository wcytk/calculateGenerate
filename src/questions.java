import java.io.*;
import java.util.*;

class questions {
    private int lines;
    private FileWriter fw = null;

    void init(int lines) {
        this.lines = lines;
    }

    private String getStudent() {
        return "2017012509";
    }

    private int getQuestionNum() {
        return lines;
    }

    // 是用随机数获取四则运算符号
    private char getOp() {
        char temp = 'i';
        int s = (int) (Math.random() * 4 + 1);
        if (s == 1) {
            temp = '+';
        } else if (s == 2) {
            temp = '-';
        } else if (s == 3) {
            temp = '*';
        } else if (s == 4) {
            temp = '÷';
        }
        return temp;
    }

    // 随机获取100以内的数字
    private int getNum() {
        return (int) (Math.random() * 100);
    }

    // 随机获取运算符号的数量
    private int getOpNum() {
        return (int) (Math.random() * 4 + 2);
    }

    private int getPosition(int num) {
        return (int) (Math.random() * num + 1);
    }

    // 随机选择需不需要加上括号
    private boolean getBrackets() {
        return (int) (Math.random() * 2) == 1;
    }

    // 写文件
    private void writeFile(String s, int status) {
        try {
            File f = new File("result.txt");
            switch (status) {
                case 1:
                    fw = new FileWriter(f, false);
                case 2:
                    fw = new FileWriter(f, true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fw);
        if (status == 1) {
            pw.print(s);
        } else if (status == 2) {
            pw.print("\n" + s);
        }
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 进行乘除和括号的运算
    private void preCalculate(String[] stack, int[] y, char[] z, int[] b, boolean[] flag, int[] h, int x, int[] count) {
        count[0] = 0;
        for (int i = 0; i < x; i++) {
            StringBuilder sb;
            // 如果二者之间符号为乘，就将上一个数更新为二者的乘积
            if (z[i] == '*') {
                sb = new StringBuilder();
                if (i == 0) {
                    // 如果是第一个符号，则直接将第一个数和第二个数相乘
                    if (i < x - 1) {
                        if (z[i + 1] == '+' || z[i + 1] == '-') {
                            if (getBrackets()) {
                                if (z[i + 1] == '+') {
                                    stack[++h[0]] = sb.append((double) y[i] * (y[i + 1] + y[i + 2])).toString();
                                    i++;
                                    b[i]++;
                                    count[0]++;
                                    continue;
                                } else if (z[i + 1] == '-') {
                                    while (y[i + 1] - y[i + 2] <= 0) {
                                        y[i + 1] = (int) (Math.random() * 100 + 1);
                                        y[i + 2] = (int) (Math.random() * 100 + 1);
                                    }
                                    stack[++h[0]] = sb.append((double) y[i] * (y[i + 1] - y[i + 2])).toString();
                                    i++;
                                    b[i]++;
                                    count[0]++;
                                    continue;
                                }
                            }
                        }
                    }
                    stack[++h[0]] = sb.append((double) y[i] * y[i + 1]).toString();
                } else {
                    if (i < x - 1) {
                        if (z[i + 1] == '+' || z[i + 1] == '-') {
                            if (getBrackets()) {
                                if (z[i + 1] == '+') {
                                    stack[h[0]] = sb.append(Double.parseDouble(stack[h[0]]) * (y[i + 1] + y[i + 2])).toString();
                                    i++;
                                    b[i]++;
                                    count[0]++;
                                    continue;
                                } else if (z[i + 1] == '-') {
                                    while (y[i + 1] - y[i + 2] <= 0) {
                                        y[i + 1] = (int) (Math.random() * 100 + 1);
                                        y[i + 2] = (int) (Math.random() * 100 + 1);
                                    }
                                    stack[h[0]] = sb.append(Double.parseDouble(stack[h[0]]) * (y[i + 1] - y[i + 2])).toString();
                                    i++;
                                    b[i]++;
                                    count[0]++;
                                    continue;
                                }
                            }
                        }
                    }
                    // 如果不是，则将栈中上一个数与下一个运算数相乘
                    stack[h[0]] = sb.append(Double.parseDouble(stack[h[0]]) * y[i + 1]).toString();
                }
                // 如果二者之间符号为除，则将上一个数更新为二者的商
            } else if (z[i] == '÷') {
                sb = new StringBuilder();
                if (i == 0) {
                    if (i < x - 1) {
                        if (z[i + 1] == '+' || z[i + 1] == '-') {
                            if (getBrackets()) {
                                int temp;
                                if (z[i + 1] == '+') {
                                    temp = y[i + 1] + y[i + 2];
                                    while (temp <= 0) {
                                        temp = getPlus(y, i);
                                    }
                                    while (y[i] % temp != 0 || y[i] >= 100) {
                                        temp = getPlus(y, i);
                                        if (temp == 0) {
                                            temp = 101;
                                            continue;
                                        }
                                        y[i] = temp * (int) (Math.random() * 5 + 1);
                                    }
                                    stack[++h[0]] = sb.append((double) y[i] / temp).toString();
                                    if (y[i] == temp || temp == 1) flag[0] = true;
                                    i++;
                                    b[i]++;
                                    count[0]++;
                                    continue;
                                } else if (z[i + 1] == '-') {
                                    temp = y[i + 1] - y[i + 2];
                                    while (temp == 0) {
                                        y[i + 1] = (int) (Math.random() * 100 + 1);
                                        y[i + 2] = (int) (Math.random() * 100 + 1);
                                        temp = y[i + 1] - y[i + 2];
                                    }
                                    while (y[i] % temp != 0 || y[i] >= 100) {
                                        y[i + 1] = (int) (Math.random() * 100 + 1);
                                        y[i + 2] = (int) (Math.random() * 100 + 1);
                                        temp = y[i + 1] - y[i + 2];
                                        if (temp <= 0) {
                                            temp = 101;
                                            continue;
                                        }
                                        if (y[i] == temp || temp == 1) flag[0] = true;
                                        y[i] = temp * (int) (Math.random() * 5 + 1);
                                    }
                                    stack[++h[0]] = sb.append((double) y[i] / temp).toString();
                                    i++;
                                    b[i]++;
                                    count[0]++;
                                    continue;
                                }
                            }
                        }
                    }
                    if (y[i + 1] == 0) {
                        // 如果除数为0，则重新生成除数直至不为0
                        while (y[i + 1] == 0) {
                            y[i + 1] = getNum();
                        }
                    }
                    // 若果除数不能将被除数除尽，则重新生成除数和被除数直至可以除尽
                    if (y[i] % y[i + 1] != 0) {
                        while (y[i] % y[i + 1] != 0 || y[i] > 100) {
                            y[i + 1] = (int) (Math.random() * 100 + 1);
                            y[i] = y[i + 1] * (int) (Math.random() * 5 + 1);
                        }
                        if (y[i] == y[i + 1] || y[i + 1] == 1) flag[0] = true;
                    }
                    stack[++h[0]] = sb.append((double) y[i] / y[i + 1]).toString();
                } else {
                    if (i < x - 1) {
                        if (z[i + 1] == '+' || z[i + 1] == '-') {
                            if (getBrackets()) {
                                int temp;
                                if (z[i + 1] == '+') {
                                    temp = y[i + 1] + y[i + 2];
                                    while (Double.parseDouble(stack[h[0]]) % temp != 0) {
                                        temp = getPlus(y, i);
                                    }
                                    i = getQuotient(stack, b, flag, h, count, i, sb, temp);
                                    continue;
                                } else if (z[i + 1] == '-') {
                                    temp = y[i + 1] - y[i + 2];
                                    while (temp == 0) {
                                        temp = getMinus(y, i);
                                    }
                                    while (Double.parseDouble(stack[h[0]]) % temp != 0) {
                                        temp = getMinus(y, i);
                                        if (temp < 0) {
                                            temp = 101;
                                        }
                                    }
                                    i = getQuotient(stack, b, flag, h, count, i, sb, temp);
                                    continue;
                                }
                            }
                        }
                    }
                    if (y[i + 1] == 0) {
                        while (y[i + 1] == 0) {
                            y[i + 1] = getNum();
                        }
                    }
                    while (Double.parseDouble(stack[h[0]]) % y[i + 1] != 0 || y[i] > 100) {
                        y[i + 1] = (int) (Math.random() * 100 + 1);
                    }
                    if (y[i] == y[i + 1] || y[i + 1] == 1) flag[0] = true;
                    stack[h[0]] = sb.append(Double.parseDouble(stack[h[0]]) / y[i + 1]).toString();
                }
            } else {
                // 二者之间的符号为加减
                // 判断是否要加上括号
                // 如果需要加括号
                if (getBrackets()) {
                    sb = new StringBuilder();
                    // 如果符号为+
                    if (z[i] == '+') {
                        if (i == 0) {
                            // 将第一个数更新为二者的和
                            count[0]++;
                            stack[++h[0]] = sb.append(y[i] + y[i + 1]).toString();
                            b[i]++;
                            continue;
                        } else {
                            // 将上一个数更新为二者的和
                            if (b[i - 1] <= 0 && z[i - 1] != '*' && z[i - 1] != '÷') {
                                count[0]++;
                                stack[h[0]] = sb.append(y[i] + y[i + 1]).toString();
                                b[i]++;
                                continue;
                            }
                        }
                    } else if (z[i] == '-') {
                        // 如果符号为-
                        if (i == 0) {
                            // 将第一个数更新为二者的差
                            count[0]++;
                            while (y[i] - y[i + 1] <= 0) {
                                y[i] = (int) (Math.random() * 100 + 1);
                                y[i + 1] = (int) (Math.random() * 100);
                            }
                            stack[++h[0]] = sb.append(y[i] - y[i + 1]).toString();
                            // 如果二者的差小于0，则舍弃
                            if (y[i] - y[i + 1] < 0) flag[0] = true;
                            b[i]++;
                            continue;
                        } else {
                            // 将上一个数更新为二者的差
                            if (b[i - 1] <= 0 && z[i - 1] != '*' && z[i - 1] != '÷') {
                                count[0]++;
                                while (y[i] - y[i + 1] <= 0) {
                                    y[i + 1] = (int) (Math.random() * 100);
                                }
                                stack[h[0]] = sb.append(y[i] - y[i + 1]).toString();
                                // 如果二者的差小于0，则舍弃
                                if (y[i] - y[i + 1] < 0) flag[0] = true;
                                b[i]++;
                                continue;
                            }
                        }
                    }
                }
                // 如果不需要加上括号，则将运算数和运算符直接进栈
                if (i == 0) {
                    sb = new StringBuilder();
                    stack[++h[0]] = sb.append(y[i]).toString();
                }
                sb = new StringBuilder();
                stack[++h[0]] = sb.append(z[i]).toString();
                sb = new StringBuilder();
                stack[++h[0]] = sb.append(y[i + 1]).toString();
            }
        }
    }

    private int getQuotient(String[] stack, int[] b, boolean[] flag, int[] h, int[] count, int i, StringBuilder sb, int temp) {
        if (Double.parseDouble(stack[h[0]]) == temp || temp == 1) flag[0] = true;
        stack[h[0]] = sb.append(Double.parseDouble(stack[h[0]]) / temp).toString();
        i++;
        b[i]++;
        count[0]++;
        return i;
    }

    private int getMinus(int[] y, int i) {
        int temp;
        y[i + 1] = (int) (Math.random() * 100);
        y[i + 2] = (int) (Math.random() * 100);
        temp = y[i + 1] - y[i + 2];
        return temp;
    }

    private int getPlus(int[] y, int i) {
        int temp;
        y[i + 1] = (int) (Math.random() * 100);
        y[i + 2] = (int) (Math.random() * 100);
        temp = y[i + 1] + y[i + 2];
        return temp;
    }

    // 下一步进行栈中数据的计算
    private void calStack(String[] stack, int[] h, boolean[] flag, double[] cal) {
        for (int i = 0; i <= h[0]; i++) {
            if (h[0] == 0) {
                cal[0] = Double.parseDouble(stack[i]);
            }
            if (i == 1) {
                if (stack[i].equals("+")) {
                    cal[0] = Double.parseDouble(stack[i - 1]) + Double.parseDouble(stack[i + 1]);
                } else if (stack[i].equals("-")) {
                    cal[0] = Double.parseDouble(stack[i - 1]) - Double.parseDouble(stack[i + 1]);
                    if (cal[0] < 0) {
                        flag[0] = true;
                    }
                }
            } else {
                if (stack[i].equals("+")) {
                    cal[0] += Double.parseDouble(stack[i + 1]);
                } else if (stack[i].equals("-")) {
                    cal[0] -= Double.parseDouble(stack[i + 1]);
                    if (cal[0] < 0) {
                        flag[0] = true;
                    }
                }
            }
        }
//        if(!flag[0]&& cal[0] > 0) {
//            System.out.print(stack[0]);
//            for (int i = 1; i <= h[0]; i++) {
//                System.out.print(" " + stack[i]);
//            }
//            System.out.print("\n");
//        }
    }

    // 初始化计算函数的参数
    private double calculate(int x, int[] y, char[] z, int[] b, int[] count1) {
        double[] cal = new double[x];
        cal[0] = 0;
        int[] h = new int[x];
        h[0] = -1;
        boolean[] flag = new boolean[x];
        flag[0] = false;
        String[] stack = new String[2 * x + 1];
        int[] count = new int[x];
        preCalculate(stack, y, z, b, flag, h, x, count1);
        calStack(stack, h, flag, cal);
        if (flag[0]) return -1;
        else return cal[0];
    }

    // 随机获取分子
    private int getNumerator(int de) {
        return (int) (Math.random() * de + 1);
    }

    // 随机获取分母
    private int getDenominator() {
        return (int) (Math.random() * 50 + 1);
    }

    // 求最大公因数
    private int gcd(int a, int b) {
        return (b > 0) ? gcd(b, a % b) : a;
    }

    // 生成分数式子
    private void getFraction() {
        boolean flag = true;
        int x, den, num;
        int[] de, nu;
        char[] op;
        while (true) {
            flag = true;
            x = getOpNum();
            de = new int[x + 1];
            nu = new int[x + 1];
            op = new char[x];
            for (int i = 0; i < x + 1; i++) {
                // 获取分子分母
                de[i] = getDenominator();
                nu[i] = getNumerator(de[i]);
                // 将二者约分为真分数
                while (gcd(de[i], nu[i]) != 1 || de[i] <= nu[i]) {
                    de[i] = getDenominator();
                    nu[i] = getNumerator(de[i]);
                }
                // 随机生成两个运算符
                if (i < x) {
                    op[i] = getOp();
                    // 如果为*或÷，则舍弃
                    while (op[i] == '*' || op[i] == '÷') {
                        op[i] = getOp();
                    }
                }
            }
            den = de[0];
            num = nu[0];
            // 获得计算结果
            for (int i = 0; i < x; i++) {
                // 算出最大公因数
                int cf = gcd(den, de[i + 1]);
                // 将两个分数通分
                if (op[i] == '+') {
                    // 用分子乘上公因数
                    num = num * de[i + 1] / cf;
                    num += (nu[i + 1] * den / cf);
                } else {
                    // 用分子乘上公因数
                    num = num * de[i + 1] / cf;
                    num -= (nu[i + 1] * den / cf);
                    // 如果出现分子为0，则舍去
                    if (num < 0) {
                        flag = false;
                    }
                }
                // 两数相乘再除以最大公因数得到最小公倍数
                den = den * de[i + 1] / cf;
                if (num > den) flag = false;
            }
            // 如果出现分子为0或分母大于100，则舍去
            if (num == 0 || den > 100) flag = false;
            // 最后的结果进行一次约分
            int cf = gcd(den, num);
            den /= cf;
            num /= cf;
            // 将数组中的数字和运算符转换为字符串
            if (flag) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < x + 1; i++) {
                    sb.append(nu[i]).append("/").append(de[i]);
                    if (i < x) {
                        sb.append(op[i]);
                    }
                }
                sb.append("=").append(num).append("/").append(den);
                // 将字符串输出
                writeFile(sb.toString(), 2);
                System.out.println(sb.toString());
                break;
            }
        }
    }

    // 制定选择到分数运算的概率
    private boolean select() {
        return (int) (Math.random() * 8 + 1) == 2;
    }

    void generate() {
        int line = lines;
        // 输出学号
        writeFile(getStudent(), 1);
        System.out.println(getStudent());
        int status = 0;
        while (line > 0) {
            // 初始化函数中参数
            int x = getOpNum();
            int[] y = new int[x + 1];
            char[] z = new char[x];
            int[] b = new int[x];
            int[] count = new int[x];
            // 获取运算类型，分数运算还是整数
            if (!select() || status > 0) {
                StringBuilder a = new StringBuilder();
                for (int i = 0; i < x + 1; i++) {
                    y[i] = getNum();
                    while (y[i] == 0) {
                        y[i] = getNum();
                    }
                }

                char first = '+';
                for (int i = 0; i < x; i++) {
                    z[i] = getOp();
                    if (i == 0) {
                        first = z[i];
                    } else if (z[i] == first) {
                        while (z[i] == first) {
                            z[i] = getOp();
                        }
                    }
                }

                double cal = calculate(x, y, z, b, count);
                // 如果结果小于0或出现括号为1个，则重新生成
                if (cal < 0) {
                    continue;
                }
                if (count[0] == 1 || (status > 0 && count[0] < 2)) {
                    status++;
                    continue;
                } else if (count[0] > 1) {
                    status = 0;
                }

                // 将数组输出为字符串
                boolean put = false;
                for (int i = 0; i < x + 1; i++) {
                    if (i < x) {
                        if (b[i] > 0) {
                            a.append('(');
                        }
                    }
                    a.append(y[i]);
                    if (put) {
                        a.append(')');
                        put = false;
                    }
                    if (i < x) {
                        a.append(z[i]);
                        if (b[i] > 0) {
                            put = true;
                        }
                    }
                }
                writeFile(a.toString() + "=" + (int) cal, 2);
                System.out.println(a.toString() + "=" + (int) cal);
            } else {
                getFraction();
            }
            line--;
        }
    }
}