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
public class ResearchRepositoryMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.ResearchRepositoryMapBuilder";


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

        dbMap.addTable("RESEARCH_REPOSITORY");
        TableMap tMap = dbMap.getTable("RESEARCH_REPOSITORY");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("RESEARCH_REPOSITORY.SUBJECT_ID", new Integer(0));
                    tMap.addColumn("RESEARCH_REPOSITORY.REPLY_ID", new Integer(0));
                    tMap.addColumn("RESEARCH_REPOSITORY.SUBJECT", new String());
                    tMap.addColumn("RESEARCH_REPOSITORY.REPLIES", new Integer(0));
                    tMap.addColumn("RESEARCH_REPOSITORY.USER_ID", new Integer(0));
                    tMap.addColumn("RESEARCH_REPOSITORY.REPO_NAME", new String());
                    tMap.addColumn("RESEARCH_REPOSITORY.POST_TIME", new Date());
                    tMap.addColumn("RESEARCH_REPOSITORY.EXPIRY", new Integer(0));
                    tMap.addColumn("RESEARCH_REPOSITORY.EXPIRY_DATE", new Date());
          }
}
