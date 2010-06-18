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
 * extended all references should be to Task
 */
public abstract class BaseTask extends BaseObject
{
    /** The Peer class */
    private static final TaskPeer peer =
        new TaskPeer();

        
    /** The value for the taskId field */
    private int taskId;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the title field */
    private String title;
      
    /** The value for the status field */
    private int status;
      
    /** The value for the startDate field */
    private Date startDate;
      
    /** The value for the endDate field */
    private Date endDate;
      
    /** The value for the expiry field */
    private int expiry;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
      
    /** The value for the dueDays field */
    private int dueDays;
      
    /** The value for the dueDate field */
    private Date dueDate;
  
    
    /**
     * Get the TaskId
     *
     * @return int
     */
    public int getTaskId()
    {
        return taskId;
    }

                        
    /**
     * Set the value of TaskId
     *
     * @param v new value
     */
    public void setTaskId(int v) 
    {
    
                  if (this.taskId != v)
              {
            this.taskId = v;
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
     * Get the Title
     *
     * @return String
     */
    public String getTitle()
    {
        return title;
    }

                        
    /**
     * Set the value of Title
     *
     * @param v new value
     */
    public void setTitle(String v) 
    {
    
                  if (!ObjectUtils.equals(this.title, v))
              {
            this.title = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Status
     *
     * @return int
     */
    public int getStatus()
    {
        return status;
    }

                        
    /**
     * Set the value of Status
     *
     * @param v new value
     */
    public void setStatus(int v) 
    {
    
                  if (this.status != v)
              {
            this.status = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the StartDate
     *
     * @return Date
     */
    public Date getStartDate()
    {
        return startDate;
    }

                        
    /**
     * Set the value of StartDate
     *
     * @param v new value
     */
    public void setStartDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.startDate, v))
              {
            this.startDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the EndDate
     *
     * @return Date
     */
    public Date getEndDate()
    {
        return endDate;
    }

                        
    /**
     * Set the value of EndDate
     *
     * @param v new value
     */
    public void setEndDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.endDate, v))
              {
            this.endDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Expiry
     *
     * @return int
     */
    public int getExpiry()
    {
        return expiry;
    }

                        
    /**
     * Set the value of Expiry
     *
     * @param v new value
     */
    public void setExpiry(int v) 
    {
    
                  if (this.expiry != v)
              {
            this.expiry = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ExpiryDate
     *
     * @return Date
     */
    public Date getExpiryDate()
    {
        return expiryDate;
    }

                        
    /**
     * Set the value of ExpiryDate
     *
     * @param v new value
     */
    public void setExpiryDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.expiryDate, v))
              {
            this.expiryDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the DueDays
     *
     * @return int
     */
    public int getDueDays()
    {
        return dueDays;
    }

                        
    /**
     * Set the value of DueDays
     *
     * @param v new value
     */
    public void setDueDays(int v) 
    {
    
                  if (this.dueDays != v)
              {
            this.dueDays = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the DueDate
     *
     * @return Date
     */
    public Date getDueDate()
    {
        return dueDate;
    }

                        
    /**
     * Set the value of DueDate
     *
     * @param v new value
     */
    public void setDueDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.dueDate, v))
              {
            this.dueDate = v;
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
          save(TaskPeer.getMapBuilder()
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
                    TaskPeer.doInsert((Task) this, con);
                    setNew(false);
                }
                else
                {
                    TaskPeer.doUpdate((Task) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                                    
  
    private final SimpleKey[] pks = new SimpleKey[2];
    private final ComboKey comboPK = new ComboKey(pks);
    
    /**
     * Set the PrimaryKey with an ObjectKey
     *
     * @param key
     */
    public void setPrimaryKey(ObjectKey key) throws TorqueException
    {
        SimpleKey[] keys = (SimpleKey[]) key.getValue();
        SimpleKey tmpKey = null;
                      setTaskId(((NumberKey)keys[0]).intValue());
                        setStartDate(((DateKey)keys[1]).getDate());
              }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
         * @param int taskId
         * @param Date startDate
         */
    public void setPrimaryKey( int taskId, Date startDate)
        
    {
            setTaskId(taskId);
            setStartDate(startDate);
        }

    /**
     * Set the PrimaryKey using a String.
     */
    public void setPrimaryKey(String key) throws TorqueException
    {
        setPrimaryKey(new ComboKey(key));
    }
  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
              pks[0] = SimpleKey.keyFor(getTaskId());
                  pks[1] = SimpleKey.keyFor(getStartDate());
                  return comboPK;
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Task copy() throws TorqueException
    {
        return copyInto(new Task());
    }
  
    protected Task copyInto(Task copyObj) throws TorqueException
    {
          copyObj.setTaskId(taskId);
          copyObj.setUserId(userId);
          copyObj.setTitle(title);
          copyObj.setStatus(status);
          copyObj.setStartDate(startDate);
          copyObj.setEndDate(endDate);
          copyObj.setExpiry(expiry);
          copyObj.setExpiryDate(expiryDate);
          copyObj.setDueDays(dueDays);
          copyObj.setDueDate(dueDate);
  
                    copyObj.setTaskId(0);
                                                copyObj.setStartDate((Date)null);
                                          
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TaskPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Task:\n");
        str.append("TaskId = ")
           .append(getTaskId())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("Title = ")
           .append(getTitle())
           .append("\n");
        str.append("Status = ")
           .append(getStatus())
           .append("\n");
        str.append("StartDate = ")
           .append(getStartDate())
           .append("\n");
        str.append("EndDate = ")
           .append(getEndDate())
           .append("\n");
        str.append("Expiry = ")
           .append(getExpiry())
           .append("\n");
        str.append("ExpiryDate = ")
           .append(getExpiryDate())
           .append("\n");
        str.append("DueDays = ")
           .append(getDueDays())
           .append("\n");
        str.append("DueDate = ")
           .append(getDueDate())
           .append("\n");
        return(str.toString());
    }
}
