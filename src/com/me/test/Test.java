package com.me.test;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Test {

    public static void main(String[] args) {

        int n = 123400;
        //计算机通过Locale来针对当地用户习惯格式化日期、时间、数字、货币等。
        System.out.println(NumberFormat.getCurrencyInstance(Locale.CHINA).format(n));

        //“同一个时刻”在计算机中存储的本质上只是一个整数，我们称它为Epoch Time
        //Epoch Time是计算从1970年1月1日零点（格林威治时区／GMT+00:00）到现在所经历的秒数
        //因此，在计算机中，只需要存储一个整数表示某一时刻。
        // 当需要显示为某一地区的当地时间时，我们就把它格式化为一个字符串：
        //Epoch Time又称为时间戳，在不同的编程语言中，会有几种不同的存储方式
        //而在Java程序中，时间戳通常是用long表示的毫秒数
        //要获取当前时间戳，可以使用System.currentTimeMillis()，这是Java程序获取时间戳最常用的方法。
        System.out.println(System.currentTimeMillis());

        //Java标准库有两套处理日期和时间的API：
        //一套定义在java.util这个包里面，主要包括Date、Calendar和TimeZone这几个类；
        //一套新的API是在Java 8引入的，定义在java.time这个包里面，主要包括LocalDateTime、ZonedDateTime、ZoneId等。

        //旧API
        // 获取当前时间:
        //注意:
        // getYear()返回的年份必须加上1900，
        // getMonth()返回的月份是0~11分别表示1~12月，所以要加1，
        // 而getDate()返回的日期范围是1~31，又不能加1。
        Date date = new Date();
        System.out.println(date.getYear() + 1900);  // 必须加上1900
        System.out.println(date.getMonth() + 1);    // 0~11，必须加上1
        System.out.println(date.getDate());         // 1~31，不能加1
        // 转换为String:
        System.out.println(date.toString());
        // 转换为GMT时区:
        System.out.println(date.toGMTString());
        // 转换为本地时区:
        System.out.println(date.toLocaleString());

        //如果我们想要针对用户的偏好精确地控制日期和时间的格式，就可以使用SimpleDateFormat对一个Date进行转换。它用预定义的字符串表示格式化：
        //yyyy：年
        //MM：月
        //dd: 日
        //HH: 小时
        //mm: 分钟
        //ss: 秒
        // 获取当前时间:
        Date date2 = new Date();
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date2));

        //Date对象有几个严重的问题：它不能转换时区，除了toGMTString()可以按GMT+0:00输出外，
        // Date总是以当前计算机系统的默认时区为基础进行输出。
        // 此外，我们也很难对日期和时间进行加减，计算两个日期相差多少天，计算某个月第一个星期一的日期等。

        //Calendar可以用于获取并设置年、月、日、时、分、秒，它和Date比，
        // 主要多了一个可以做简单的日期和时间运算的功能。
        //注意到Calendar获取年月日这些信息变成了get(int field)，
        // 返回的年份不必转换，
        // 返回的月份仍然 要加1，
        // 返回的星期要特别注意，1~7分别表示 周日，周一，……，周六。
        // 获取当前时间:
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = 1 + c.get(Calendar.MONTH);      //+1
        int d = c.get(Calendar.DAY_OF_MONTH);
        int w = c.get(Calendar.DAY_OF_WEEK);    //4 -- 周三
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);
        System.out.println(y + "-" + m + "-" + d + " " + w + " " + hh + ":" + mm + ":" + ss + "." + ms);

        //Calendar只有一种方式获取，即Calendar.getInstance()，而且一获取到就是当前时间。
        // 如果我们想给它设置成特定的一个日期和时间，就必须先清除所有字段：
        //当前时间:
        Calendar c2 = Calendar.getInstance();
        // 清除所有:
        c2.clear();
        // 设置2019年:
        c2.set(Calendar.YEAR, 2019);
        // 设置9月:注意8表示9月:
        c2.set(Calendar.MONTH, 8);
        // 设置2日:
        c2.set(Calendar.DATE, 2);
        // 设置时间:
        c2.set(Calendar.HOUR_OF_DAY, 21);
        c2.set(Calendar.MINUTE, 22);
        c2.set(Calendar.SECOND, 23);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c2.getTime()));
        // 2019-09-02 21:22:23

        //利用Calendar.getTime()可以将一个Calendar对象转换成Date对象，然后就可以用SimpleDateFormat进行格式化了。
        Date date3 = c2.getTime();
        System.out.println(date3);
        System.out.println(sdf.format(date3));

        //TimeZone提供了时区转换的功能。时区用TimeZone对象表示
        //要列出系统支持的所有ID，请使用TimeZone.getAvailableIDs()
        TimeZone tzDefault = TimeZone.getDefault(); // 当前时区
        TimeZone tzGMT9 = TimeZone.getTimeZone("GMT+09:00"); // GMT+9:00时区
        TimeZone tzNY = TimeZone.getTimeZone("America/New_York"); // 纽约时区
        System.out.println(tzDefault.getID()); // Asia/Shanghai
        System.out.println(tzGMT9.getID()); // GMT+09:00
        System.out.println(tzNY.getID()); // America/New_York

        //利用Calendar进行时区转换的步骤是：
        //清除所有字段；
        //设定指定时区；
        //设定日期和时间；
        //创建SimpleDateFormat并设定目标时区；
        //格式化获取的Date对象（注意Date对象无时区信息，时区信息存储在SimpleDateFormat中）。
        // 当前时间:
        Calendar c3 = Calendar.getInstance();
        // 清除所有:
        c3.clear();
        // 设置为 北京时区:
        c3.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 设置 年 月 日 时 分 秒:
        c3.set(2019, 10 /* 11月 */, 20, 8, 15, 0);
        // 显示时间:
        // 本质上时区转换只能通过SimpleDateFormat在显示的时候完成。
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        System.out.println(sdf.format(c3.getTime()));       // 2019-11-19 19:15:00

        //Calendar也可以对日期和时间进行简单的加减：
        // 当前时间:
        Calendar c4 = Calendar.getInstance();
        // 清除所有:
        c4.clear();
        // 设置年月日时分秒:
        c4.set(2019, 10 /* 11月 */, 20, 8, 15, 0);
        // 加5天并减去2小时:
        c4.add(Calendar.DAY_OF_MONTH, 5);
        c4.add(Calendar.HOUR_OF_DAY, -2);
        // 显示时间:
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = c4.getTime();
        System.out.println(sdf.format(d1));
        // 2019-11-25 6:15:00

        //新API
        //严格区分了时刻、本地日期、本地时间和带时区的日期时间，并且，对日期和时间进行运算更加方便。
        //此外，新API修正了旧API不合理的常量设计：
        //  Month的范围用1~12表示1月到12月；
        //  Week的范围用1~7表示周一到周日。
        //最后，新API的类型几乎全部是不变类型（和String类似），可以放心使用不必担心被修改。

        //LocalDateTime，它表示一个本地日期和时间
        //本地日期和时间通过now()获取到的总是以当前默认时区返回的，和旧API不同，
        // LocalDateTime、LocalDate和LocalTime默认严格按照ISO 8601规定的日期和时间格式进行打印。
        LocalDateTime localDateTime=LocalDateTime.now();
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        System.out.println(localDateTime);
        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(localDateTime.toLocalDate());
        System.out.println(localDateTime.toLocalTime());

        //通过指定的日期和时间创建LocalDateTime可以通过of()方法：
        // 指定日期和时间:
        LocalDate d2 = LocalDate.of(2019, 11, 30); // 2019-11-30, 注意11=11月
        LocalTime t2 = LocalTime.of(15, 16, 17); // 15:16:17
        LocalDateTime dt2 = LocalDateTime.of(2019, 11, 30, 15, 16, 17);
        LocalDateTime dt3 = LocalDateTime.of(d2, t2);
        System.out.println(dt2);
        System.out.println(dt3);

        //因为严格按照ISO 8601的格式，因此，将字符串转换为LocalDateTime就可以传入标准格式：
        //注意ISO 8601规定的日期和时间分隔符是 T
        LocalDateTime dts = LocalDateTime.parse("2019-11-19T15:16:17");
        LocalDate ds = LocalDate.parse("2019-11-19");
        LocalTime ts = LocalTime.parse("15:16:17");
        System.out.println(dts);

        //要自定义输出的格式，或者要把一个非ISO 8601格式的字符串解析成LocalDateTime，
        // 可以使用新的DateTimeFormatter：
        // 自定义 格式化:
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));
        // 用自定义 格式解析:
        LocalDateTime dt4 = LocalDateTime.parse("2019/11/30 15:16:17", dtf);
        System.out.println(dt4);

        //LocalDateTime提供了对日期和时间进行 加减 的非常简单的 链式 调用：
        //注意到月份加减 会自动调整 日期，例如从2019-10-31减去1个月得到的结果是2019-09-30，因为9月没有31日。
        LocalDateTime dt = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
        System.out.println(dt);
        // 加5天减3小时:
        LocalDateTime dt5 = dt.plusDays(5).minusHours(3);
        System.out.println(dt5); // 2019-10-31T17:30:59
        // 减1月:
        LocalDateTime dt6 = dt5.minusMonths(1);
        System.out.println(dt6); // 2019-09-30T17:30:59

        //对日期和时间进行 调整 则使用withXxx()方法，例如：withHour(15)会把10:11:12变为15:11:12：
        LocalDateTime dt7 = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
        System.out.println(dt7);
        // 日期变为31日:
        LocalDateTime dt8 = dt7.withDayOfMonth(31);
        System.out.println(dt8); // 2019-10-31T20:30:59
        // 月份变为9:
        LocalDateTime dt9 = dt7.withMonth(9);
        System.out.println(dt9); // 2019-09-30T20:30:59

        //LocalDateTime还有一个通用的with()方法允许我们做更复杂的运算
        // 本月第一天0:00时刻:
        LocalDateTime firstDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        System.out.println(firstDay);

        // 本月最后1天:
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDay);

        // 下月第1天:
        LocalDate nextMonthFirstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(nextMonthFirstDay);

        // 本月第1个周一:
        LocalDate firstWeekday = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(firstWeekday);

        //要判断两个LocalDateTime的先后，可以使用isBefore()、isAfter()方法，对于LocalDate和LocalTime类似：
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
        System.out.println(now.isBefore(target));
        System.out.println(LocalDate.now().isBefore(LocalDate.of(2019, 11, 19)));
        System.out.println(LocalTime.now().isAfter(LocalTime.parse("08:15:00")));

        //LocalDateTime无法与时间戳进行转换，因为LocalDateTime没有时区，无法确定某一时刻

        //Duration表示两个时刻之间的 时间间隔
        LocalDateTime start = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 9, 19, 25, 30);
        Duration dd = Duration.between(start, end);
        System.out.println(dd); // PT1235H10M30S

        //Period表示两个日期之间的 天数
        Period p = LocalDate.of(2019, 11, 19).until(LocalDate.of(2020, 1, 9));
        System.out.println(p); // P1M21D

        //Duration和Period的表示方法也符合ISO 8601的格式，它以P...T...的形式表示，P...T之间表示日期间隔，
        // T后面表示时间间隔。如果是PT...的格式表示仅有时间间隔。
        // 利用ofXxx()或者parse()方法也可以直接创建Duration：
        Duration dd1 = Duration.ofHours(10); // 10 hours
        Duration dd2 = Duration.parse("P1DT2H3M"); // 1 day, 2 hours, 3 minutes
        System.out.println(dd1);
        System.out.println(dd2);

        //可以简单地把ZonedDateTime理解成LocalDateTime加ZoneId。ZoneId是java.time引入的新的时区类，
        // 注意和旧的java.util.TimeZone区别。
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        ZonedDateTime zny = ZonedDateTime.now(ZoneId.of("America/New_York")); // 用指定时区获取当前时间
        System.out.println(zbj);//同一时刻now
        System.out.println(zny);

        //通过给一个LocalDateTime附加一个ZoneId，就可以变成ZonedDateTime：
        LocalDateTime ldt = LocalDateTime.of(2019, 9, 15, 15, 16, 17);
        ZonedDateTime zbj1 = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime zny1 = ldt.atZone(ZoneId.of("America/New_York"));
        System.out.println(zbj1);//不同时刻
        System.out.println(zny1);

        //要转换时区，首先我们需要有一个ZonedDateTime对象，然后，通过withZoneSameInstant()将关联时区转换到另一个时区，转换后日期和时间都会相应调整。
        //时区转换的时候，由于夏令时的存在，不同的日期转换的结果很可能是不同的。
        //涉及到时区时，千万不要自己计算时差，否则难以正确处理夏令时。
        // 以中国时区获取当前时间:
        ZonedDateTime zbj2 = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        // 转换 为纽约时间:
        ZonedDateTime zny2 = zbj2.withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println(zbj2);
        System.out.println(zny2);

        //ZonedDateTime将其转换为本地时间就非常简单,转换为LocalDateTime时，直接 丢弃了 时区信息。
        LocalDateTime dateTime=ZonedDateTime.now().toLocalDateTime();
        System.out.println(dateTime);
        LocalDateTime zny3 = ZonedDateTime.now(ZoneId.of("America/New_York")).toLocalDateTime();
        System.out.println(zny3);

        //某航线从北京飞到纽约需要13小时20分钟，请根据北京起飞日期和时间计算 到达 纽约的 当地日期和时间。
        LocalDateTime departureAtBeijing = LocalDateTime.of(2019, 9, 15, 13, 0, 0);
        int hours = 13;
        int minutes = 20;
        LocalDateTime arrivalAtNewYork = calculateArrivalAtNY(departureAtBeijing, hours, minutes);
        System.out.println(departureAtBeijing + " -> " + arrivalAtNewYork);
        // test:
        if (!LocalDateTime.of(2019, 10, 15, 14, 20, 0)
                .equals(calculateArrivalAtNY(LocalDateTime.of(2019, 10, 15, 13, 0, 0), 13, 20))) {
            System.err.println("测试失败!");
        } else if (!LocalDateTime.of(2019, 11, 15, 13, 20, 0)
                .equals(calculateArrivalAtNY(LocalDateTime.of(2019, 11, 15, 13, 0, 0), 13, 20))) {
            System.err.println("测试失败!");
        }

        //DateTimeFormatter可以通过格式化字符串和Locale对日期和时间进行定制输出。
        //DateTimeFormatter不但是不变对象，它还是线程安全的,DateTimeFormatter可以只创建一个实例，到处引用
        //创建DateTimeFormatter时，我们仍然通过传入格式化字符串实现：
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //另一种创建DateTimeFormatter的方法是，传入格式化字符串时，同时指定Locale：
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("E, yyyy-MMMM-dd HH:mm", Locale.US);

        ZonedDateTime zdt = ZonedDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm ZZZZ");
        System.out.println(formatter.format(zdt));

        //在格式化字符串中，如果需要输出固定字符，可以用  'xxx'  表示。
        var zhFormatter = DateTimeFormatter.ofPattern("yyyy MMM 'xxx' dd EE HH:mm", Locale.CHINA);
        System.out.println(zhFormatter.format(zdt));

        var usFormatter = DateTimeFormatter.ofPattern("E, MMMM/dd/yyyy HH:mm", Locale.US);
        System.out.println(usFormatter.format(zdt));

        //当我们直接调用System.out.println()对一个ZonedDateTime或者LocalDateTime实例进行打印的时候，
        // 实际上，调用的是它们的toString()方法，默认的toString()方法显示的字符串就是按照ISO 8601格式显示的，我们可以通过DateTimeFormatter预定义的几个静态变量来引用：
        var ldt2 = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ISO_DATE.format(ldt2));
        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(ldt2));

        //Instant表示高精度时间戳，它可以和ZonedDateTime以及long互相转换。
        //用Instant.now()获取当前时间戳，效果和System.currentTimeMillis()类似：
        Instant instant = Instant.now();
        System.out.println(instant.getEpochSecond()); // 秒
        System.out.println(instant.toEpochMilli()); // 毫秒

        //Instant就是时间戳，那么，给它附加上一个时区，就可以创建出ZonedDateTime：
        // 以指定时间戳创建Instant:
        Instant ins = Instant.ofEpochSecond(1568568760);
        ZonedDateTime zdti = ins.atZone(ZoneId.systemDefault());
        System.out.println(zdti); // 2019-09-16T01:32:40+08:00[Asia/Shanghai]


        //旧API转新API
        //如果要把旧式的Date或Calendar转换为新API对象，可以通过toInstant()方法转换为Instant对象，再继续转换为ZonedDateTime：
        // Date -> Instant:
        Instant ins1 = new Date().toInstant();

        // Calendar -> Instant -> ZonedDateTime:
        Calendar calendar = Calendar.getInstance();
        Instant ins2 = Calendar.getInstance().toInstant();
        ZonedDateTime zdtc = ins2.atZone(calendar.getTimeZone().toZoneId());
        System.out.println(zdtc);

        //要把新的ZonedDateTime转换为旧的API对象，只能借助long型时间戳做一个“中转”：
        // ZonedDateTime -> long:
        ZonedDateTime zdtl = ZonedDateTime.now();
        long tsl = zdtl.toEpochSecond() * 1000;
        System.out.println(tsl);

        // long -> Date:
        Date date1 = new Date(tsl);
        System.out.println(date1);

        // long -> Calendar:
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.setTimeZone(TimeZone.getTimeZone(zdt.getZone().getId()));
        calendar1.setTimeInMillis(zdtl.toEpochSecond() * 1000);


        //在数据库中存储时间戳时，尽量使用long型时间戳，它具有省空间，效率高，不依赖数据库的优点。
        //java.sql.Date，它继承自java.util.Date，但会自动忽略所有时间相关信息。这个奇葩的设计原因要追溯到数据库的日期与时间类型。
        //在数据库中，也存在几种日期和时间类型：
        //DATETIME：表示日期和时间；
        //DATE：仅表示日期；
        //TIME：仅表示时间；
        //TIMESTAMP：和DATETIME类似，但是数据库会在创建或者更新记录的时候同时修改TIMESTAMP。
        //实际上，在数据库中，我们需要存储的最常用的是时刻（Instant），
        // 因为有了时刻信息，就可以根据用户自己选择的时区，显示出正确的本地时间。
        // 所以，最好的方法是直接用长整数long表示，在数据库中存储为BIGINT类型。
        long tsql = 1574208900000L;
        System.out.println(timestampToString(tsql, Locale.CHINA, "Asia/Shanghai"));
        System.out.println(timestampToString(tsql, Locale.US, "America/New_York"));


    }

    static String timestampToString(long epochMilli, Locale lo, String zoneId) {
        Instant ins = Instant.ofEpochMilli(epochMilli);
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        return f.withLocale(lo).format(ZonedDateTime.ofInstant(ins, ZoneId.of(zoneId)));
    }

    static LocalDateTime calculateArrivalAtNY(LocalDateTime bj, int h, int m) {

        return bj.atZone(ZoneId.of("Asia/Shanghai"))    /*设置当前时区*/
                .withZoneSameInstant(ZoneId.of("America/New_York")) /*转换到纽约时区*/
                .plusHours(h).plusMinutes(m)    /*加飞行时间*/
                .toLocalDateTime(); /*去掉时区信息，只保留本地（纽约）日期时间*/
    }

}
