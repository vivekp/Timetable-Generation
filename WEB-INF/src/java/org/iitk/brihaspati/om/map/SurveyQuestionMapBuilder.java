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
public class SurveyQuestionMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.SurveyQuestionMapBuilder";


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

        dbMap.addTable("SURVEY_QUESTION");
        TableMap tMap = dbMap.getTable("SURVEY_QUESTION");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("SURVEY_QUESTION.SURVEY_ID", new Integer(0));
                    tMap.addColumn("SURVEY_QUESTION.INST_ID", new Integer(0));
                    tMap.addColumn("SURVEY_QUESTION.CID", new String());
                    tMap.addColumn("SURVEY_QUESTION.QUES1", new String());
                    tMap.addColumn("SURVEY_QUESTION.QUES2", new String());
                    tMap.addColumn("SURVEY_QUESTION.QUES3", new String());
                    tMap.addColumn("SURVEY_QUESTION.QUES4", new String());
                    tMap.addColumn("SURVEY_QUESTION.QUES5", new String());
                    tMap.addColumn("SURVEY_QUESTION.PDATE", new Date());
          }
}
