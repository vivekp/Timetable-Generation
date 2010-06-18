package org.iitk.brihaspati.om.map;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;

/**
  */
public class QuizMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.QuizMapBuilder";


    /**
     * The database map.
     */
    private DatabaseMap dbMap = null;

    /**
     * Tells us if this DatabaseMapBuilder is built so that we
     * don't have to re-build it every time.
     *
     * @return true if this DatabaseMapBuilder is built
     */
    public boolean isBuilt()
    {
        return (dbMap != null);
    }

    /**
     * Gets the databasemap this map builder built.
     *
     * @return the databasemap
     */
    public DatabaseMap getDatabaseMap()
    {
        return this.dbMap;
    }

    /**
     * The doBuild() method builds the DatabaseMap
     *
     * @throws TorqueException
     */
    public void doBuild() throws TorqueException
    {
        dbMap = Torque.getDatabaseMap("default");

        dbMap.addTable("QUIZ");
        TableMap tMap = dbMap.getTable("QUIZ");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("QUIZ.ID", new Integer(0));
                    tMap.addColumn("QUIZ.QUIZ_ID", new String());
                    tMap.addColumn("QUIZ.USER_ID", new String());
                    tMap.addColumn("QUIZ.CID", new String());
                    tMap.addColumn("QUIZ.QUIZ_TITLE", new String());
                    tMap.addColumn("QUIZ.START_TIME", new Date());
                    tMap.addColumn("QUIZ.END_TIME", new Date());
                    tMap.addColumn("QUIZ.POST_DATE", new Date());
                    tMap.addColumn("QUIZ.MAX_MARKS", new Integer(0));
                    tMap.addColumn("QUIZ.EXPIRY_DATE", new Date());
          }
}
