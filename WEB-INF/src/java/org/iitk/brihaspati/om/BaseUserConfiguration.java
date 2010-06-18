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
 * extended all references should be to UserConfiguration
 */
public abstract class BaseUserConfiguration extends BaseObject
{
    /** The Peer class */
    private static final UserConfigurationPeer peer =
        new UserConfigurationPeer();

        
    /** The value for the userId field */
    private int userId;
                                          
    /** The value for the questionId field */
    private int questionId = 0;
      
    /** The value for the answer field */
    private String answer;
                                          
    /** The value for the listConfiguration field */
    private int listConfiguration = 10;
      
    /** The value for the photo field */
    private String photo;
                                          
    /** The value for the taskConfiguration field */
    private int taskConfiguration = 7;
  
    
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
     * Get the QuestionId
     *
     * @return int
     */
    public int getQuestionId()
    {
        return questionId;
    }

                        
    /**
     * Set the value of QuestionId
     *
     * @param v new value
     */
    public void setQuestionId(int v) 
    {
    
                  if (this.questionId != v)
              {
            this.questionId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Answer
     *
     * @return String
     */
    public String getAnswer()
    {
        return answer;
    }

                        
    /**
     * Set the value of Answer
     *
     * @param v new value
     */
    public void setAnswer(String v) 
    {
    
                  if (!ObjectUtils.equals(this.answer, v))
              {
            this.answer = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ListConfiguration
     *
     * @return int
     */
    public int getListConfiguration()
    {
        return listConfiguration;
    }

                        
    /**
     * Set the value of ListConfiguration
     *
     * @param v new value
     */
    public void setListConfiguration(int v) 
    {
    
                  if (this.listConfiguration != v)
              {
            this.listConfiguration = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Photo
     *
     * @return String
     */
    public String getPhoto()
    {
        return photo;
    }

                        
    /**
     * Set the value of Photo
     *
     * @param v new value
     */
    public void setPhoto(String v) 
    {
    
                  if (!ObjectUtils.equals(this.photo, v))
              {
            this.photo = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the TaskConfiguration
     *
     * @return int
     */
    public int getTaskConfiguration()
    {
        return taskConfiguration;
    }

                        
    /**
     * Set the value of TaskConfiguration
     *
     * @param v new value
     */
    public void setTaskConfiguration(int v) 
    {
    
                  if (this.taskConfiguration != v)
              {
            this.taskConfiguration = v;
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
          save(UserConfigurationPeer.getMapBuilder()
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
                    UserConfigurationPeer.doInsert((UserConfiguration) this, con);
                    setNew(false);
                }
                else
                {
                    UserConfigurationPeer.doUpdate((UserConfiguration) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  userId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setUserId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setUserId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getUserId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public UserConfiguration copy() throws TorqueException
    {
        return copyInto(new UserConfiguration());
    }
  
    protected UserConfiguration copyInto(UserConfiguration copyObj) throws TorqueException
    {
          copyObj.setUserId(userId);
          copyObj.setQuestionId(questionId);
          copyObj.setAnswer(answer);
          copyObj.setListConfiguration(listConfiguration);
          copyObj.setPhoto(photo);
          copyObj.setTaskConfiguration(taskConfiguration);
  
                    copyObj.setUserId(0);
                                          
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public UserConfigurationPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("UserConfiguration:\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("QuestionId = ")
           .append(getQuestionId())
           .append("\n");
        str.append("Answer = ")
           .append(getAnswer())
           .append("\n");
        str.append("ListConfiguration = ")
           .append(getListConfiguration())
           .append("\n");
        str.append("Photo = ")
           .append(getPhoto())
           .append("\n");
        str.append("TaskConfiguration = ")
           .append(getTaskConfiguration())
           .append("\n");
        return(str.toString());
    }
}
