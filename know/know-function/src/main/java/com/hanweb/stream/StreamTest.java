package com.hanweb.stream;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * @author pc
 * @Date 2020/11/10
 **/
public class StreamTest {

    public static void main(String[] args) {
        //创建无限流
        LocalDateTime startLocalDateTime = LocalDateTime.now();
        Stream<LocalDateTime> iterate = Stream.iterate(startLocalDateTime, d -> d.plusYears(1));
        iterate.limit(10).forEach(System.out::println);

        //1、Stream.iterate(startLocalDateTime, d-> d.plusYears(1))获取一个无限流
        //2、limit截取需要并进行相关业务处理
//        Stream.iterate(startLocalDateTime, d-> d.plusYears(1)).limit(between+1).forEach(
//                f->{
//                    String format = f.format(INDEX_YEAR_DF);
//                    String s = indexPrefix + "_" + format + "_"+YEAR_IDENTIFICATION;
//                    list.add(s);
//                }
//        );
    }

}
