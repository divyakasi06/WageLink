public class Worker {
    private int id;
    private String name;
    private String skill;
    private String availableDays;

    public Worker(String name, String skill, String availableDays) {
        this.name = name;
        this.skill = skill;
        this.availableDays = availableDays;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getSkill() { return skill; }
    public String getAvailableDays() { return availableDays; }
}