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
 * extended all references should be to Assignment
 */
public abstract class BaseAssignment extends BaseObject
{
    /** The Peer class */
    private static final AssignmentPeer peer =
        new AssignmentPeer();

        
    /** The value for the assignId field */
    private String assignId;
      
    /** The value for the groupName field */
    private String groupName;
      
    /** The value for the topicName field */
    private String topicName;
      
    /** The value for the curDate field */
    private Date curDate;
      
    /** The value for the dueDate field */
    private Date dueDate;
      
    /** The value for the perDate field */
    private Date perDate;
      
    /** The value for the grade field */
    private int grade;
  
    
    /**
     * Get the AssignId
     *
     * @return String
     */
    public String getAssignId()
    {
        return assignId;
    }

                        
    /**
     * Set the value of AssignId
     *
     * @param v new value
     */
    public void setAssignId(String v) 
    {
    
                  if (!ObjectUtils.equals(this.assignId, v))
              {
            this.assignId = v;
            setModified(true);
        }
    
          
              }
  
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
     * Get the TopicName
     *
     * @return String
     */
    public String getTopicName()
    {
        return topicName;
    }

                        
    /**
     * Set the value of TopicName
     *
     * @param v new value
     */
    public void setTopicName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.topicName, v))
              {
            this.topicName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the CurDate
     *
     * @return Date
     */
    public Date getCurDate()
    {
        return curDate;
    }

                        
    /**
     * Set the value of CurDate
     *
     * @param v new value
     */
    public void setCurDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.curDate, v))
              {
            this.curDate = v;
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
     * Get the PerDate
     *
     * @return Date
     */
    public Date getPerDate()
    {
        return perDate;
    }

                        
    /**
     * Set the value of PerDate
     *
     * @param v new value
     */
    public void setPerDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.perDate, v))
              {
            this.perDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Grade
     *
     * @return int
     */
    public int getGrade()
    {
        return grade;
    }

                        
    /**
     * Set the value of Grade
     *
     * @param v new value
     */
    public void setGrade(int v) 
    {
    
                  if (this.grade != v)
              {
            this.grade = v;
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
          save(AssignmentPeer.getMapBuilder()
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
                    AssignmentPeer.doInsert((Assignment) this, con);
                    setNew(false);
                }
                else
                {
                    AssignmentPeer.doUpdate((Assignment) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  assignId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setAssignId(key.toString());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setAssignId(key);
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getAssignId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Assignment copy() throws TorqueException
    {
        return copyInto(new Assignment());
    }
  
    protected Assignment copyInto(Assignment copyObj) throws TorqueException
    {
          copyObj.setAssignId(assignId);
          copyObj.setGroupName(groupName);
          copyObj.setTopicName(topicName);
          copyObj.setCurDate(curDate);
          copyObj.setDueDate(dueDate);
          copyObj.setPerDate(perDate);
          copyObj.setGrade(grade);
  
                    copyObj.setAssignId((String)null);
                                                
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public AssignmentPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Assignment:\n");
        str.append("AssignId = ")
           .append(getAssignId())
           .append("\n");
        str.append("GroupName = ")
           .append(getGroupName())
           .append("\n");
        str.append("TopicName = ")
           .append(getTopicName())
           .append("\n");
        str.append("CurDate = ")
           .append(getCurDate())
           .append("\n");
        str.append("DueDate = ")
           .append(getDueDate())
           .append("\n");
        str.append("PerDate = ")
           .append(getPerDate())
           .append("\n");
        str.append("Grade = ")
           .append(getGrade())
           .append("\n");
        return(str.toString());
    }
}
