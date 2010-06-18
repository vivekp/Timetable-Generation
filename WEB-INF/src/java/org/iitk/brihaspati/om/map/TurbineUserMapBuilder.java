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
public class TurbineUserMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "org.iitk.brihaspati.om.map.TurbineUserMapBuilder";


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
        dbMap = Torque.getDatabaseMap("brihaspati");

        dbMap.addTable("TURBINE_USER");
        TableMap tMap = dbMap.getTable("TURBINE_USER");

        tMap.setPrimaryKeyMethod(TableMap.ID_BROKER);

        tMap.setPrimaryKeyMethodInfo(tMap.getName());

              tMap.addPrimaryKey("TURBINE_USER.USER_ID", new Integer(0));
                    tMap.addColumn("TURBINE_USER.LOGIN_NAME", new String());
                    tMap.addColumn("TURBINE_USER.PASSWORD_VALUE", new String());
                    tMap.addColumn("TURBINE_USER.FIRST_NAME", new String());
                    tMap.addColumn("TURBINE_USER.LAST_NAME", new String());
                    tMap.addColumn("TURBINE_USER.EMAIL", new String());
                    tMap.addColumn("TURBINE_USER.CONFIRM_VALUE", new String());
                    tMap.addColumn("TURBINE_USER.MODIFIED", new Date());
                    tMap.addColumn("TURBINE_USER.CREATED", new Date());
                    tMap.addColumn("TURBINE_USER.LAST_LOGIN", new Date());
                    tMap.addColumn("TURBINE_USER.OBJECTDATA", new Object());
                    tMap.addColumn("TURBINE_USER.QUOTA", new BigDecimal(0));
          }
}
