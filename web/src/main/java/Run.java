
import org.example.registration.check.ChangesUser;
import org.example.registration.inter.ChangesUserInterface;
import org.example.registration.inter.ReadingUser;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.json.GetUser;
import org.example.registration.json.RecordUser;
import org.example.registration.user.User;

import java.io.File;

public class Run {
    //Пришлось отказаться от консоль ввода очень много времени отнимает
    public static void main(String[] args) {
         File file = new File("jarModule/src/main/resources/json/json_file.json");
          File file2 = new File("jarModule/src/main/resources/json/json_file1.json");

        User user1 = new User(1, "nikolai", "nik1dsas", "mikolakran@gmail.com", "admin");
        User user2 = new User(2, "nikolai2", "nik3wqdf", "test2@gmail.com", "user1");
        User user3 = new User(3, "nikolai3", "nik3fds", "test3@gmail.com", "user2");
        User user4 = new User(4, "nikolai4", "nik43qrd", "test4@gmail.com", "user3");
        User user5 = new User(5, "nikolai2", "nik512e", "test5@gmail.com", "user4");//-user 2 - 5
        User user6 = new User(6, "nikolai6", "nik4f43", "test6@gmail.com", "user5");
        User user7 = new User(7, "nik43rdad", "nik43rdad", "test7@gmail.com", "user6");//-pass + user
        User user8 = new User(8, "nikolai8", "nik8d43", "test8@gmail.com", "user7");
        User user9 = new User(9, "nikolai9", "nik92d3", "test8@gmail.com", "user8");//-email 8-9
        WriteFileUser writeFileUser = new RecordUser(file);
        writeFileUser.writeUser(user1);
        writeFileUser.writeUser(user2);
        writeFileUser.writeUser(user3);
        writeFileUser.writeUser(user4);
        writeFileUser.writeUser(user5);
        writeFileUser.writeUser(user6);
        writeFileUser.writeUser(user7);
        writeFileUser.writeUser(user8);
        writeFileUser.writeUser(user9);

        ReadingUser readingUser = new GetUser(file);
        System.out.println(readingUser.getUserByKey("1")); // достаю user по key
        System.out.println(readingUser.getUserByKey("3"));// достаю user по key
        System.out.println(readingUser.getAllUser());// достаю всех users
        ChangesUserInterface changesUser = new ChangesUser(file,file2);
        changesUser.changesUserBySelectionTable("3","username","Sewza",file);//изменяю email,password,userName по выбору таблицы
        System.out.println(readingUser.getUserByKey("3"));//достаю user по key
        changesUser.deleteUserByKey("1",file);//user delete
        changesUser.deleteUserByKey("3",file);//user delete
        System.out.println(readingUser.getAllUser());// достаю всех users
    }
}