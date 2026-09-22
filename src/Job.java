public class Job {
    private int id;
    private String title;
    private String location;
    private int wage;
    private String jobDate;
    private String postedBy;

    public Job(String title, String location, int wage, String jobDate, String postedBy) {
        this.title = title;
        this.location = location;
        this.wage = wage;
        this.jobDate = jobDate;
        this.postedBy = postedBy;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public int getWage() { return wage; }
    public String getJobDate() { return jobDate; }
    public String getPostedBy() { return postedBy; }
}