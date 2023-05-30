import org.apache.commons.lang3.RandomStringUtils;

public class RandomUser {

    public static User generate() {
        return new User("Pavlik48@yandex.ru", "123456789", "Pavlik48");
    }

    public static User random() {
        return new User(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru", "123456789", "Pavlik48");
    }
}