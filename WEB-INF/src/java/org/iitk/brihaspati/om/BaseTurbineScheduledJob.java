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
 * extended all references should be to TurbineScheduledJob
 */
public abstract class BaseTurbineScheduledJob extends BaseObject
{
    /** The Peer class */
    private static final TurbineScheduledJobPeer peer =
        new TurbineScheduledJobPeer();

        
    /** The value for the jobId field */
    private int jobId;
                                          
    /** The value for the second field */
    private int second = -1;
                                          
    /** The value for the minute field */
    private int minute = -1;
                                          
    /** The value for the hour field */
    private int hour = -1;
                                          
    /** The value for the weekDay field */
    private int weekDay = -1;
                                          
    /** The value for the dayOfMonth field */
    private int dayOfMonth = -1;
      
    /** The value for the task field */
    private String task;
      
    /** The value for the email field */
    private String email;
      
    /** The value for the property field */
    private byte[] property;
  
    
    /**
     * Get the JobId
     *
     * @return int
     */
    public int getJobId()
    {
        return jobId;
    }

                        
    /**
     * Set the value of JobId
     *
     * @param v new value
     */
    public void setJobId(int v) 
    {
    
                  if (this.jobId != v)
              {
            this.jobId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Second
     *
     * @return int
     */
    public int getSecond()
    {
        return second;
    }

                        
    /**
     * Set the value of Second
     *
     * @param v new value
     */
    public void setSecond(int v) 
    {
    
                  if (this.second != v)
              {
            this.second = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Minute
     *
     * @return int
     */
    public int getMinute()
    {
        return minute;
    }

                        
    /**
     * Set the value of Minute
     *
     * @param v new value
     */
    public void setMinute(int v) 
    {
    
                  if (this.minute != v)
              {
            this.minute = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Hour
     *
     * @return int
     */
    public int getHour()
    {
        return hour;
    }

                        
    /**
     * Set the value of Hour
     *
     * @param v new value
     */
    public void setHour(int v) 
    {
    
                  if (this.hour != v)
              {
            this.hour = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the WeekDay
     *
     * @return int
     */
    public int getWeekDay()
    {
        return weekDay;
    }

                        
    /**
     * Set the value of WeekDay
     *
     * @param v new value
     */
    public void setWeekDay(int v) 
    {
    
                  if (this.weekDay != v)
              {
            this.weekDay = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the DayOfMonth
     *
     * @return int
     */
    public int getDayOfMonth()
    {
        return dayOfMonth;
    }

                        
    /**
     * Set the value of DayOfMonth
     *
     * @param v new value
     */
    public void setDayOfMonth(int v) 
    {
    
                  if (this.dayOfMonth != v)
              {
            this.dayOfMonth = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Task
     *
     * @return String
     */
    public String getTask()
    {
        return task;
    }

                        
    /**
     * Set the value of Task
     *
     * @param v new value
     */
    public void setTask(String v) 
    {
    
                  if (!ObjectUtils.equals(this.task, v))
              {
            this.task = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Email
     *
     * @return String
     */
    public String getEmail()
    {
        return email;
    }

                        
    /**
     * Set the value of Email
     *
     * @param v new value
     */
    public void setEmail(String v) 
    {
    
                  if (!ObjectUtils.equals(this.email, v))
              {
            this.email = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Property
     *
     * @return byte[]
     */
    public byte[] getProperty()
    {
        return property;
    }

                        
    /**
     * Set the value of Property
     *
     * @param v new value
     */
    public void setProperty(byte[] v) 
    {
    
                  if (!ObjectUtils.equals(this.property, v))
              {
            this.property = v;
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
          save(TurbineScheduledJobPeer.getMapBuilder()
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
                    TurbineScheduledJobPeer.doInsert((TurbineScheduledJob) this, con);
                    setNew(false);
                }
                else
                {
                    TurbineScheduledJobPeer.doUpdate((TurbineScheduledJob) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  jobId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setJobId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setJobId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getJobId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public TurbineScheduledJob copy() throws TorqueException
    {
        return copyInto(new TurbineScheduledJob());
    }
  
    protected TurbineScheduledJob copyInto(TurbineScheduledJob copyObj) throws TorqueException
    {
          copyObj.setJobId(jobId);
          copyObj.setSecond(second);
          copyObj.setMinute(minute);
          copyObj.setHour(hour);
          copyObj.setWeekDay(weekDay);
          copyObj.setDayOfMonth(dayOfMonth);
          copyObj.setTask(task);
          copyObj.setEmail(email);
          copyObj.setProperty(property);
  
                    copyObj.setJobId(0);
                                                            
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TurbineScheduledJobPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("TurbineScheduledJob:\n");
        str.append("JobId = ")
           .append(getJobId())
           .append("\n");
        str.append("Second = ")
           .append(getSecond())
           .append("\n");
        str.append("Minute = ")
           .append(getMinute())
           .append("\n");
        str.append("Hour = ")
           .append(getHour())
           .append("\n");
        str.append("WeekDay = ")
           .append(getWeekDay())
           .append("\n");
        str.append("DayOfMonth = ")
           .append(getDayOfMonth())
           .append("\n");
        str.append("Task = ")
           .append(getTask())
           .append("\n");
        str.append("Email = ")
           .append(getEmail())
           .append("\n");
        str.append("Property = ")
           .append(getProperty())
           .append("\n");
        return(str.toString());
    }
}
