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
 * extended all references should be to Courses
 */
public abstract class BaseCourses extends BaseObject
{
    /** The Peer class */
    private static final CoursesPeer peer =
        new CoursesPeer();

        
    /** The value for the groupName field */
    private String groupName;
      
    /** The value for the cname field */
    private String cname;
      
    /** The value for the groupAlias field */
    private String groupAlias;
      
    /** The value for the dept field */
    private String dept;
      
    /** The value for the description field */
    private String description;
                                          
    /** The value for the active field */
    private byte active = 0;
      
    /** The value for the creationdate field */
    private Date creationdate;
      
    /** The value for the lastmodified field */
    private Date lastmodified;
      
    /** The value for the lastaccess field */
    private Date lastaccess;
      
    /** The value for the quota field */
    private BigDecimal quota;
  
    
    /**
     * Get the GroupName
     *
     * @return String
     */
    public String getGroupName()
    {
        return groupName;
    }

                        
    /**
     * Set the value of GroupName
     *
     * @param v new value
     */
    public void setGroupName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.groupName, v))
              {
            this.groupName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Cname
     *
     * @return String
     */
    public String getCname()
    {
        return cname;
    }

                        
    /**
     * Set the value of Cname
     *
     * @param v new value
     */
    public void setCname(String v) 
    {
    
                  if (!ObjectUtils.equals(this.cname, v))
              {
            this.cname = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the GroupAlias
     *
     * @return String
     */
    public String getGroupAlias()
    {
        return groupAlias;
    }

                        
    /**
     * Set the value of GroupAlias
     *
     * @param v new value
     */
    public void setGroupAlias(String v) 
    {
    
                  if (!ObjectUtils.equals(this.groupAlias, v))
              {
            this.groupAlias = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Dept
     *
     * @return String
     */
    public String getDept()
    {
        return dept;
    }

                        
    /**
     * Set the value of Dept
     *
     * @param v new value
     */
    public void setDept(String v) 
    {
    
                  if (!ObjectUtils.equals(this.dept, v))
              {
            this.dept = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Description
     *
     * @return String
     */
    public String getDescription()
    {
        return description;
    }

                        
    /**
     * Set the value of Description
     *
     * @param v new value
     */
    public void setDescription(String v) 
    {
    
                  if (!ObjectUtils.equals(this.description, v))
              {
            this.description = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Active
     *
     * @return byte
     */
    public byte getActive()
    {
        return active;
    }

                        
    /**
     * Set the value of Active
     *
     * @param v new value
     */
    public void setActive(byte v) 
    {
    
                  if (this.active != v)
              {
            this.active = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Creationdate
     *
     * @return Date
     */
    public Date getCreationdate()
    {
        return creationdate;
    }

                        
    /**
     * Set the value of Creationdate
     *
     * @param v new value
     */
    public void setCreationdate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.creationdate, v))
              {
            this.creationdate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Lastmodified
     *
     * @return Date
     */
    public Date getLastmodified()
    {
        return lastmodified;
    }

                        
    /**
     * Set the value of Lastmodified
     *
     * @param v new value
     */
    public void setLastmodified(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.lastmodified, v))
              {
            this.lastmodified = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Lastaccess
     *
     * @return Date
     */
    public Date getLastaccess()
    {
        return lastaccess;
    }

                        
    /**
     * Set the value of Lastaccess
     *
     * @param v new value
     */
    public void setLastaccess(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.lastaccess, v))
              {
            this.lastaccess = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Quota
     *
     * @return BigDecimal
     */
    public BigDecimal getQuota()
    {
        return quota;
    }

                        
    /**
     * Set the value of Quota
     *
     * @param v new value
     */
    public void setQuota(BigDecimal v) 
    {
    
                  if (!ObjectUtils.equals(this.quota, v))
              {
            this.quota = v;
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
          save(CoursesPeer.getMapBuilder()
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
                    CoursesPeer.doInsert((Courses) this, con);
                    setNew(false);
                }
                else
                {
                    CoursesPeer.doUpdate((Courses) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  groupName ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setGroupName(key.toString());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setGroupName(key);
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getGroupName());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Courses copy() throws TorqueException
    {
        return copyInto(new Courses());
    }
  
    protected Courses copyInto(Courses copyObj) throws TorqueException
    {
          copyObj.setGroupName(groupName);
          copyObj.setCname(cname);
          copyObj.setGroupAlias(groupAlias);
          copyObj.setDept(dept);
          copyObj.setDescription(description);
          copyObj.setActive(active);
          copyObj.setCreationdate(creationdate);
          copyObj.setLastmodified(lastmodified);
          copyObj.setLastaccess(lastaccess);
          copyObj.setQuota(quota);
  
                    copyObj.setGroupName((String)null);
                                                                  
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public CoursesPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Courses:\n");
        str.append("GroupName = ")
           .append(getGroupName())
           .append("\n");
        str.append("Cname = ")
           .append(getCname())
           .append("\n");
        str.append("GroupAlias = ")
           .append(getGroupAlias())
           .append("\n");
        str.append("Dept = ")
           .append(getDept())
           .append("\n");
        str.append("Description = ")
           .append(getDescription())
           .append("\n");
        str.append("Active = ")
           .append(getActive())
           .append("\n");
        str.append("Creationdate = ")
           .append(getCreationdate())
           .append("\n");
        str.append("Lastmodified = ")
           .append(getLastmodified())
           .append("\n");
        str.append("Lastaccess = ")
           .append(getLastaccess())
           .append("\n");
        str.append("Quota = ")
           .append(getQuota())
           .append("\n");
        return(str.toString());
    }
}
