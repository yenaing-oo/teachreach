package comp3350.teachreach.application;

public
class TRData
{
    private static String dbName = "TR";

    public static
    String getDBPathName()
    {
        return dbName;
    }

    public static
    void setDBPathName(final String name)
    {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }
}
