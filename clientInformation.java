public class clientInformation
{
    String Name;
    DatagramSocket Identifier;

    public clientInformation(String Name, DatagramSocket Identifier)
    {
        this.Name = Name;
        this.Identifier = Identifier;
    }

    public String getName()
    {
        return Name;
    }

    public DatagramSocket getIdentifier()
    {
        return Identifier;
    }

}