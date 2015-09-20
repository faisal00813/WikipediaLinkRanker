package WikiPojos;


public class Query
{
private Backlinks[] backlinks;

public Backlinks[] getBacklinks ()
{
return backlinks;
}

public void setBacklinks (Backlinks[] backlinks)
{
this.backlinks = backlinks;
}

@Override
public String toString()
{
return "ClassPojo [backlinks = "+backlinks+"]";
}
}

