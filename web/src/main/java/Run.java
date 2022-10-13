
import org.example.registration.inter.exception.LoginException;
import org.example.registration.inter.ReadingUser;
import org.example.registration.inter.WriteFileUser;
import org.example.registration.json.GetUser;
import org.example.registration.json.RecordUser;
import org.example.registration.user.User;

import java.io.File;

public class Run {
    //Пришлось отказаться от консоль ввода очень много времени отнимает
    public static void main(String[] args) throws LoginException {
         File file = new File("jarModule/src/main/resources/json/json_file.json");
          File file2 = new File("jarModule/src/main/resources/json/json_file2.json");

           User user1 = new User(1, "nikolai", "nik1dsas", "mikolakran@gmail.com", "admin");//delete
        User user2 = new User(2, "nikolai2", "nifefdsa", "test2@gmail.com", "user1"); // not true username
        User user3 = new User(3, "nikolai3", "nifghfh", "test3@gmail.com", "user2");//not true password
        User user4 = new User(4, "nikolai4", "nik43qrd", "test4@gmail.com", "user3");
        WriteFileUser writeFileUser = new RecordUser(file);
        writeFileUser.writeUser(user1);
        writeFileUser.writeUser(user2);
        writeFileUser.writeUser(user3);
        writeFileUser.writeUser(user4);
        System.out.println();
        ReadingUser readingUser = new GetUser(file);

      /*  ReadingUser readingUser = new GetUser(file);
        System.out.println(readingUser.getUserByKey("1")); // достаю user по key
        System.out.println(readingUser.getUserByKey("3"));// достаю user по key если его нету
        System.out.println(readingUser.getAllUser());// достаю всех users
        System.out.println();
        ChangesUserInterface changesUser = new ChangesUser(file,file2);
        changesUser.changesUserBySelectionTable("4","email","testgmail.com",file);//изменяю email,password,userName по выбору таблицы
        changesUser.changesUserBySelectionTable("4","userName","Sw",file);//изменяю email,password,userName по выбору таблицы
        changesUser.changesUserBySelectionTable("4","password","Se",file);//изменяю email,password,userName по выбору таблицы
        System.out.println();
        System.out.println(readingUser.getUserByKey("4"));//достаю user по key
        changesUser.deleteUserByKey("1",file);//user delete
        System.out.println(readingUser.getAllUser());// достаю всех users*/
    }
}