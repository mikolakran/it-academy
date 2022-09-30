
import org.example.registration.check.ChangesUser;
import org.example.registration.inter.ChangesUser_inter;
import org.example.registration.inter.GetDateBaseUser;
import org.example.registration.inter.SetDateBaseUser;
import org.example.registration.json.ReadingJson;
import org.example.registration.json.RecordJson;
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
        User user9 = new User(9, "nikolai9", "nik92d3", "test9@gmail.com", "user8");
        SetDateBaseUser recordJson = new RecordJson(file);
        recordJson.set_all_date_base(user1);
        recordJson.set_all_date_base(user2);
        recordJson.set_all_date_base(user3);
        recordJson.set_all_date_base(user4);
        recordJson.set_all_date_base(user5);
        recordJson.set_all_date_base(user6);
        recordJson.set_all_date_base(user7);
        recordJson.set_all_date_base(user8);
        recordJson.set_all_date_base(user9);

        GetDateBaseUser setDateBase = new ReadingJson(file);
        System.out.println(setDateBase.get_user_date_base("1")); // user
        System.out.println(setDateBase.get_user_date_base("3"));// user
        System.out.println(setDateBase.get_all_json_date_base());// json
        ChangesUser_inter changesUser_inter = new ChangesUser(file,file2);
        changesUser_inter.changes_user("3","username","Sewza",file);//user changes
        System.out.println(setDateBase.get_user_date_base("3"));//
        changesUser_inter.changes_all_user("1",file);//user delete
        changesUser_inter.changes_all_user("3",file);//user delete
    }
}