package bikr.model;
public class Organizer extends User
{
    //default constructor
    public Organizer() { }

    //constructor
    public Organizer(String firstName, String lastName, String email, String ssn, String password) {
        super(firstName, lastName, email, ssn, password);
    }
}
