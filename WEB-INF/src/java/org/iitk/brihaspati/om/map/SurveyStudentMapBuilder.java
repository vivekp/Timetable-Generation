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
public class SurveyStudentMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.SurveyStudentMapBuilder";


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

        dbMap.addTable("SURVEY_STUDENT");
        TableMap tMap = dbMap.getTable("SURVEY_STUDENT");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("SURVEY_STUDENT.SURVEYST_ID", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.SURVEY_ID", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.INST_ID", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.CID", new String());
                    tMap.addColumn("SURVEY_STUDENT.STU_ID", new String());
                    tMap.addColumn("SURVEY_STUDENT.QUES1", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.QUES2", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.QUES3", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.QUES4", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.QUES5", new Integer(0));
                    tMap.addColumn("SURVEY_STUDENT.TOTALMARKS", new Integer(0));
          }
}
