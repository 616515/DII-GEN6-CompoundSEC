package test.test_layer;
import java.util.Scanner;
public class login {
    private String name;
    private String id;
    private String password;
    public login(String name, String id , String password) {
        Scanner S = new Scanner(System.in);
        System.out.println("คุณชื่ออะไรครับ : ");
        S.nextLine();
        System.out.println("กรุณาใส่ชื่อของคุณ: ");
        name = S.nextLine();
        this.name = name;
        System.out.println("ต้องการตั้งpasswordว่าอะไร : ");
        id = S.nextLine();
        this.id = id;
        System.out.println("ต้องการอยุ่ห้องไหน : ");
        password = S.nextLine();
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
