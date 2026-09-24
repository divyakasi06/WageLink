public class Worker {
    private int id;
    private String name;
    private String skill;
    private String availableDays;
    private String phone;

    public Worker(String name, String skill, String availableDays, String phone) {
        this.name = name;
        this.skill = skill;
        this.availableDays = availableDays;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getSkill() { return skill; }
    public String getAvailableDays() { return availableDays; }
    public String getPhone() { return phone; }
}