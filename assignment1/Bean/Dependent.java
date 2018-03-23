package Bean;

public class Dependent extends User {

   protected String parent_id1;
    protected String parent_id2;

    public Dependent(String user_id, String fName, String lName, int age, String parent_id1, String parent_id2) {
        super(user_id, fName, lName, age);
        this.parent_id1 = parent_id1;
        this.parent_id2 = parent_id2;
    }
    public Dependent()
    {

    }
    public String getParent_id1() {
        return parent_id1;
    }

    public void setParent_id1(String parent_id1) {
        this.parent_id1 = parent_id1;
    }

    public String getParent_id2() {
        return parent_id2;
    }

    public void setParent_id2(String parent_id2) {
        this.parent_id2 = parent_id2;
    }
}
