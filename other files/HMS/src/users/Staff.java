package users;

public class Staff extends User {
    private final String role;
    private final String gender;
    private final int age;

    public Staff(String userId, String password, String name, String role, String gender, int age) {
        super(userId, password, name);
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String toPrint() {
        return String.format("%s|%s|%s|%s|%s|%s", userId, password, name, role, gender, age);
    }

    @Override
    public void showMainPage() {

    }
}
