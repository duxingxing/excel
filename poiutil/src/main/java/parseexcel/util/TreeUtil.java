//package parseexcel.util;
//
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 构建树工具类
// * @author duxing
// *
// */
//public final class TreeUtil {
//    private final static String regEx = "[- _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
//    private  final static Pattern p = Pattern.compile(regEx);
//    /**
//     * 判断是否含有特殊字符
//     *
//     * @param str s
//     * @return true为包含，false为不包含
//     */
//    public static boolean isSpecialChar(String str) {
//        if(str==null){
//            return false;
//        }
//        Matcher m = p.matcher(str);
//        return m.find();
//    }
//    /**
//     * 判断是否含有特殊字符
//     *
//     * @param str
//     * @return true为包含，false为不包含
//     */
//    public static boolean isSpecialChar(char str) {
//        Matcher m = p.matcher(str+"");
//        return m.find();
//    }
//    /**
//     * 判断code是否是prarentCode的孩子节点
//     * @param prarentCode
//     * @return
//     */
//    public static boolean isChild(String prarentCode,String code){
//        if(StringUtils.isEmpty(prarentCode)||StringUtils.isEmpty(code)){
//            return false;
//        }
//        char[] chars=code.toCharArray();
//        int index=0,startIndex=prarentCode.length();
//        String specialChar="";
//        for (int i = chars.length-1; i > -1; i--) {
//            if(isSpecialChar(chars[i])){
//                index=i;
//                specialChar+=chars[i];
//                if(i-->-1){
//                    while(i>startIndex-1&&isSpecialChar(chars[i])){
//                        index=i;
//                        specialChar+=chars[i];
//                        i--;
//                    }
//                }
//                break;
//            }
//        }
//        if(code.startsWith(prarentCode+specialChar)&&startIndex==index){
//            return true;
//        }
//        return false;
//    }
//}