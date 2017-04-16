package com.gersion.superlock.utils;

import java.util.Random;

final public class PasswordUtils {
    private static char[] numArr = new char[10];
    private static char[] charBigArr = new char[27];
    private static char[] charSmallArr = new char[26];
    private static char[] charSpecial = new char[16];

    private PasswordUtils() {}

    private static int getCount(boolean big, boolean small, boolean number, boolean chars) {
        int count = 0;
        if (big) {
            count += 1;
        }
        if (small) {
            count += 1;
        }
        if (number) {
            count += 1;
        }
        if (chars) {
            count += 1;
        }
        return count;
    }

    // 生成密钥字符串,如果密码没有包含已设置的字符，会重新生成，直到能满足密码的字符要求后返回字符串
    public static String getNewPassword(boolean big, boolean small, boolean number, boolean chars,
            int length) {
        setArr(charBigArr);
        setArr(charSmallArr);
        setArr(numArr);
        setArr(charSpecial);
        String regex = ".*[!,\",#,$,%,&,',(,),*,+,,,-,.,@,/].*";
        Random random = new Random();
        char[] arr = new char[length];
        for (int i = 0; i < arr.length;) {
            int num = random.nextInt(4);
            int index;
            if (num == 0) {
                if (number) {
                    index = random.nextInt(10);
                    arr[i] = numArr[index];
                    i++;
                    continue;
                }
            } else if (num == 1) {
                if (big) {
                    index = random.nextInt(27);
                    arr[i] = charBigArr[index];
                    i++;
                    continue;
                }
            } else if (num == 2) {
                if (small) {
                    index = random.nextInt(26);
                    arr[i] = charSmallArr[index];
                    i++;
                    continue;
                }
            } else if (num == 3) {
                if (chars) {
                    index = random.nextInt(16);
                    arr[i] = charSpecial[index];
                    i++;
                    continue;
                }
            }
        }
        String key = new String(arr);
        if (big) {
            if (!key.matches(".*[A-Z].*")) {
                return getNewPassword(big, small, number, chars, length);
            }
        }

        if (small) {
            if (!key.matches(".*[a-z].*")) {

                return getNewPassword(big, small, number, chars, length);
            }

        }
        if (number) {
            if (!key.matches(".*\\d.*")) {
                return getNewPassword(big, small, number, chars, length);

            }
        }
        if (chars) {

            if (!key.matches(regex)) {
                return getNewPassword(big, small, number, chars, length);

            }
        }
        return new String(arr);
    }

    /**
     * @param length
     * @param count
     * @return
     */
    public static String getNewPassword(int length, int count) {
        setArr(charBigArr);
        setArr(charSmallArr);
        setArr(numArr);
        setArr(charSpecial);
        Random random = new Random();
        char[] arr = new char[length];
        if (count <= 0 || count > 4) {
            count = 4;
        }
        for (int i = 0; i < arr.length; i++) {
            int num = random.nextInt(count);
            int index;
            switch (num) {
                case 0:
                    index = random.nextInt(10);
                    arr[i] = numArr[index];
                    break;
                case 1:
                    index = random.nextInt(27);
                    arr[i] = charBigArr[index];
                    break;
                case 2:
                    index = random.nextInt(26);
                    arr[i] = charSmallArr[index];
                    break;
                case 3:
                    index = random.nextInt(16);
                    arr[i] = charSpecial[index];
                    break;

                default:
                    break;
            }
        }
        return new String(arr);
    }

    // 生成相应的字符数组
    private static void setArr(char[] arr) {
        if (arr.length == 10) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (char) (48 + i);
            }
        } else if (arr.length == 27) {
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = (char) (i + 65);
            }
        } else if (arr.length == 26) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (char) (i + 97);
            }
        } else if (arr.length == 16) {
            for (int i = 0; i < arr.length; i++) {

                arr[i] = (char) (i + 33);
                if (i == 15) {
                    arr[i] = '@';
                }
            }
        }
    }

    /**
     * 输入密码的字符串，得到一个密码的强度值返回
     *
     * @param pwd 要检测的密码
     * @return 返回密码强度的字符串
     */
    public static String getPasswordLevel(String pwd) {
        String regex = ".*[!,\",#,$,%,&,',(,),*,+,@,,,-,.,/].*";
        int length = pwd.length();
        if (length <= 6) {
            return "弱";
        }
        boolean isBig = pwd.matches(".*[A-Z].*");
        boolean isSmall = pwd.matches(".*[a-z].*");
        boolean isSpecial = pwd.matches(regex);
        boolean isNumber = pwd.matches(".*\\d.*");
        boolean[] arr = {isBig, isSmall, isSpecial, isNumber};
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == true) {
                count += 1;
            }
        }

        String result = "";

        switch (count) {
            case 1:
                if (length > 20) {
                    result = "强";
                } else if (length > 15) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
            case 2:
                if (length > 20) {
                    result = "极强";
                } else if (length > 15) {
                    result = "强";
                } else if (length > 10) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
            case 3:
                if (length > 18) {
                    result = "极强";
                } else if (length > 12) {
                    result = "强";
                } else if (length > 7) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
            case 4:
                if (length > 15) {
                    result = "极强";
                } else if (length > 10) {
                    result = "强";
                } else if (length > 6) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
        }
        return result;
    }

    /**
     * 根据传入的字符包含的字符类型计算密码强度
     *
     * @param isBig 包含大写
     * @param isSmall 包含小写
     * @param isSpecial 包含特殊字符
     * @param isNumber 包含数字
     * @param length 密码长度
     * @return 密码的长度值
     */
    public static String getPasswordLevel(boolean isBig, boolean isSmall, boolean isSpecial,
            boolean isNumber, int length) {
        boolean[] arr = {isBig, isSmall, isSpecial, isNumber};
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == true) {
                count += 1;
            }
        }

        String result = "";

        switch (count) {
            case 1:
                if (length > 20) {
                    result = "强";
                } else if (length > 15) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
            case 2:
                if (length > 20) {
                    result = "极强";
                } else if (length > 15) {
                    result = "强";
                } else if (length > 10) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
            case 3:
                if (length > 18) {
                    result = "极强";
                } else if (length > 12) {
                    result = "强";
                } else if (length > 7) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
            case 4:
                if (length > 15) {
                    result = "极强";
                } else if (length > 10) {
                    result = "强";
                } else if (length > 6) {
                    result = "中等";
                } else {
                    result = "弱";
                }
                break;
        }
        return result;
    }

}
