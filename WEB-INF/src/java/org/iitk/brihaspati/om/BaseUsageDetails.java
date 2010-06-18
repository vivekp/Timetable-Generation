package org.iitk.brihaspati.om;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.om.BaseObject;
import org.apache.torque.om.ComboKey;
import org.apache.torque.om.DateKey;
import org.apache.torque.om.NumberKey;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.om.StringKey;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.Transaction;


/**
 * You should not use this class directly.  It should not even be
 * extended all references should be to UsageDetails
 */
public abstract class BaseUsageDetails extends BaseObject
{
    /** The Peer class */
    private static final UsageDetailsPeer peer =
        new UsageDetailsPeer();

        
    /** The value for the entryId field */
    private int entryId;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the loginTime field */
    private Date loginTime;
  
    
    /**
     * Get the EntryId
     *
     * @return int
     */
    public int getEntryId()
    {
        return entryId;
    }

                        
    /**
     * Set the value of EntryId
     *
     * @param v new value
     */
    public void setEntryId(int v) 
    {
    
                  if (this.entryId != v)
              {
            this.entryId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the UserId
     *
     * @return int
     */
    public int getUserId()
    {
        return userId;
    }

                        
    /**
     * Set the value of UserId
     *
     * @param v new value
     */
    public void setUserId(int v) 
    {
    
                  if (this.userId != v)
              {
            this.userId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the LoginTime
     *
     * @return Date
     */
    public Date getLoginTime()
    {
        return loginTime;
    }

                        
    /**
     * Set the value of LoginTime
     *
     * @param v new value
     */
    public void setLoginTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.loginTime, v))
              {
            this.loginTime = v;
            setModified(true);
        }
    
          
              }
  
         
                
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(UsageDetailsPeer.getMapBuilder()
                .getDatabaseMap().getName());
      }

    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
       * Note: this code is here because the method body is
     * auto-generated conditionally and therefore needs to be
     * in this file instead of in the super class, BaseObject.
       *
     * @param dbName
     * @throws TorqueException
     */
    public void save(String dbName) throws TorqueException
    {
        Connection con = null;
          try
        {
            con = Transaction.begin(dbName);
            save(con);
            Transaction.commit(con);
        }
        catch(TorqueException e)
        {
            Transaction.safeRollback(con);
            throw e;
        }
      }

      /** flag to prevent endless save loop, if this object is referenced
        by another object which falls in this transaction. */
    private boolean alreadyInSave = false;
      /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.  This method
     * is meant to be used as part of a transaction, otherwise use
     * the save() method and the connection details will be handled
     * internally
     *
     * @param con
     * @throws TorqueException
     */
    public void save(Connection con) throws TorqueException
    {
          if (!alreadyInSave)
        {
            alreadyInSave = true;


  
            // If this object has been modified, then save it to the database.
            if (isModified())
            {
                if (isNew())
                {
                    UsageDetailsPeer.doInsert((UsageDetails) this, con);
                    setNew(false);
                }
                else
                {
                    UsageDetailsPeer.doUpdate((UsageDetails) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  entryId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setEntryId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setEntryId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getEntryId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public UsageDetails copy() throws TorqueException
    {
        return copyInto(new UsageDetails());
    }
  
    protected UsageDetails copyInto(UsageDetails copyObj) throws TorqueException
    {
          copyObj.setEntryId(entryId);
          copyObj.setUserId(userId);
          copyObj.setLoginTime(loginTime);
  
                    copyObj.setEntryId(0);
                        
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public UsageDetailsPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("UsageDetails:\n");
        str.append("EntryId = ")
           .append(getEntryId())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("LoginTime = ")
           .append(getLoginTime())
           .append("\n");
        return(str.toString());
    }
}
