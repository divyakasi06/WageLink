public class Employer {
    private int id;
    private String name;
    private String businessType;
    private String phone;

    public Employer(String name, String businessType, String phone) {
        this.name = name;
        this.businessType = businessType;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getBusinessType() { return businessType; }
    public String getPhone() { return phone; }
}