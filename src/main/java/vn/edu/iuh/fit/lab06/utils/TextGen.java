package vn.edu.iuh.fit.lab06.utils;

import net.datafaker.Faker;

import java.util.List;
import java.util.Locale;

public class TextGen {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("vi","VN"));

        List<String> s = faker.lorem().paragraphs(3);
        System.out.println(s);
        s.forEach(System.out::println);
    }
}
