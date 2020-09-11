package com.ulpay.testplatform.utils.random;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 随机数工具类
 *
 * @author yingjie.liu
 * @create 2018-02-28 11:34
 **/
public class RandomUtils {
    private IdCardGenerator idCardGenerator = new IdCardGenerator();
    /**
     * 读取 properties文件内容
     *
     * @param fileName properties文件名（文件存放在resources目录下）
     * @return
     */
    public Properties getProperties(String fileName) {
        InputStream in = this.getClass().getResourceAsStream("/" + fileName);
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 生成6位的时间
     *
     * @return time 当前时间
     */
    public String getTime6() {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String time = sdf.format(nowdate);
        return time;
    }

    /**
     * 获取8位当前日期字符串
     *
     * @return date 当前日期
     */
    public String getDate8() {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(nowdate);
        return date;

    }

    /**
     * 获取12位当前时间字符串
     *
     * @return dateTime 当前时间
     */
    public String getDateTime12() {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String dateTime = sdf.format(nowdate);
        return dateTime;
    }

    /**
     * 获取14位当前时间字符串
     *
     * @return dateTime 当前时间
     */
    public String getDateTime14() {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateTime = sdf.format(nowdate);
        return dateTime;
    }

    /**
     * 获取17位当前时间字符串
     *
     * @return dateTime 当前时间
     */
    public String getDateTime17() {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateTime = sdf.format(nowdate);
        return dateTime;
    }

    /**
     * 日期时间加随机数，总共14位日期 + （len-14）位随机数
     *
     * @param length 要生成随机数的长度
     * @return
     */
    public String getRandomTime(int length) {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(nowdate);
        Random rand = new Random();
        for (int i = 0; i < length - 14; i++) {
            str += String.valueOf(rand.nextInt(10));
        }
        return str;
    }
    /**
     * 获取随机位数随机数
     *
     * @param length 要生成随机数的长度
     * @return
     */
    public String getRandomAll(int length) {
        String str = "";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            str += String.valueOf(rand.nextInt(10));
        }
        return str;
    }

    /**
     * 生成当前时间以后的几个小时时间
     *
     * @param hour 延迟小时数
     * @return
     */
    public String delayHours(int hour) {
        Calendar calendar = Calendar.getInstance();
//        Date now = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        Date nextDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(nextDate);
        return str;
    }

    /**
     * 生成当前日期的未来几天
     *
     * @param day 延迟天数
     * @return
     */
    public String delayDay(int day) {
        Calendar calendar = Calendar.getInstance();
//        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        Date nextDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(nextDate);
        return str;
    }

    /**
     * 获取过去几个小时的日期
     *
     * @param past
     * @return
     */
    public String getPastTime(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("hhmmss");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return result;
    }

    /**
     * 生成随机字符串
     *
     * @param length 字符串的长度
     * @return
     */
    public String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 两个double类型数字 相加
     *
     * @return
     */
    public double doubleSum(double double1, double double2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(double1));
        BigDecimal bd2 = new BigDecimal(Double.toString(double2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 两个double类型数字 相减
     *
     * @return
     */
    public double doubleSub(double double1, double double2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(double1));
        BigDecimal bd2 = new BigDecimal(Double.toString(double2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法,保留两位小数，四舍五入
     *
     * @return 乘积结果
     */
    public double doubleMul(double double1, double double2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(double1));
        BigDecimal bd2 = new BigDecimal(Double.toString(double2));
        Double d = bd1.multiply(bd2).doubleValue();
        BigDecimal bd = new BigDecimal(d);
        Double resutl = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return resutl;
    }

    /**
     * 随机生成身份证号码
     * @return
     */
    public String getIdNo(){
        return idCardGenerator.generate(idCardGenerator.randomBirthday(),String.valueOf(new Random().nextInt(10)));
    }

    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    /**
     * 随机生成手机号码
     * @return 手机号码
     */
    public String getMobileNo(){
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    public int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
    /**
     * 随机获取一个汉字来组成名字
     *
     * @return
     */
    private String getName() {
        String nameStr = "";
        int highCode, lowCode;
        Random random = new Random();
        // 区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        highCode = (176 + Math.abs(random.nextInt(71)));
        random = new Random();
        // 位码，0xA0打头，范围第1~94列
        lowCode = 161 + Math.abs(random.nextInt(94));

        byte[] codeArr = new byte[2];
        codeArr[0] = (new Integer(highCode)).byteValue();
        codeArr[1] = (new Integer(lowCode)).byteValue();
        try {
            // 区位码组合成汉字
            nameStr = new String(codeArr, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nameStr;
    }

    public String getRandomName(){
        String[] surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈",
                "韩", "杨", "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶",
                "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费",
                "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于",
                "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧",
                "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴",
                "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席",
                "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟", "徐",
                "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢",
                "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪",
                "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀",
                "羊", "于", "惠", "甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段",
                "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班",
                "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘",
                "景", "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲",
                "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥", "能",
                "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦",
                "雍", "却", "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温",
                "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾", "鱼", "容", "向", "古",
                "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文",
                "寇", "广", "禄", "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂",
                "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空", "曾", "毋", "沙", "乜", "养",
                "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖",
                "益", "桓", "公", "仉", "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚",
                "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨", "哈", "谯", "篁", "年",
                "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭",
                "密", "敬", "揭", "舜", "楼", "疏", "冒", "浑", "挚", "胶", "随", "高", "皋", "原", "种", "练", "弥",
                "仓", "眭", "蹇", "覃", "阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨", "木", "京", "狐",
                "郇", "虎", "枚", "抗", "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老",
                "是", "秘", "畅", "邝", "还", "宾", "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯",
                "诸葛", "闻人", "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正", "濮阳", "淳于", "单于",
                "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空",
                "兀官", "司寇", "南门", "呼延", "子车", "颛孙", "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋",
                "夹谷", "宰父", "谷梁", "段干", "百里", "东郭", "微生", "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪",
                "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门", "毋丘", "贺兰",
                "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙"};
        Random random = new Random(System.currentTimeMillis());
        // 随机取得一个姓氏
        int index = random.nextInt(surname.length - 1);
        String randomName = surname[index];
        // 根据随机布尔值，确定名字是一个字还是两个字
        if (random.nextBoolean()) {
            randomName += getName() + getName();
        } else {
            randomName += getName();
        }
        return randomName;
    }

    /**
     * 用于从url中获取指定参数
     * @param url  如签约、签约并支付、实名认证url
     * @param key  获取指定参数
     */
    public String getParamByUrl(String url, String key) {
        String pattern = "(\\?|&)#?" + key + "=[a-zA-Z0-9]*(&)?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);
        if (m.find()) {
            return m.group(0).split("=")[1].replace("&", "");
        } else {
            return null;
        }
    }

    @Test
    public  void test () {
        System.out.println(getIdNo());
    }
}
