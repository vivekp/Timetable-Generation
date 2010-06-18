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
public class SurveyResultMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.SurveyResultMapBuilder";


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

        dbMap.addTable("SURVEY_RESULT");
        TableMap tMap = dbMap.getTable("SURVEY_RESULT");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("SURVEY_RESULT.RESULT_ID", new Integer(0));
                    tMap.addColumn("SURVEY_RESULT.SURVEY_ID", new Integer(0));
                    tMap.addColumn("SURVEY_RESULT.CID", new String());
                    tMap.addColumn("SURVEY_RESULT.NUM_STU_ATTD", new Integer(0));
                    tMap.addColumn("SURVEY_RESULT.TOTALMARKS_PER", new Integer(0));
                    tMap.addColumn("SURVEY_RESULT.GRADE", new String());
          }
}
