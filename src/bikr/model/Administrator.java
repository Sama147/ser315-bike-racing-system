package bikr.model;
public class Administrator extends User
{
    //default constructor
    public Administrator() { }

    //constructor
    public Administrator(String firstName, String lastName, String email, String ssn, String password)
    {
        super(firstName, lastName, email, ssn, password);
    }
}
