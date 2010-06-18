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
 * extended all references should be to Quiz
 */
public abstract class BaseQuiz extends BaseObject
{
    /** The Peer class */
    private static final QuizPeer peer =
        new QuizPeer();

        
    /** The value for the id field */
    private int id;
      
    /** The value for the quizId field */
    private String quizId;
      
    /** The value for the userId field */
    private String userId;
      
    /** The value for the cid field */
    private String cid;
      
    /** The value for the quizTitle field */
    private String quizTitle;
      
    /** The value for the startTime field */
    private Date startTime;
      
    /** The value for the endTime field */
    private Date endTime;
      
    /** The value for the postDate field */
    private Date postDate;
      
    /** The value for the maxMarks field */
    private int maxMarks;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
  
    
    /**
     * Get the Id
     *
     * @return int
     */
    public int getId()
    {
        return id;
    }

                        
    /**
     * Set the value of Id
     *
     * @param v new value
     */
    public void setId(int v) 
    {
    
                  if (this.id != v)
              {
            this.id = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the QuizId
     *
     * @return String
     */
    public String getQuizId()
    {
        return quizId;
    }

                        
    /**
     * Set the value of QuizId
     *
     * @param v new value
     */
    public void setQuizId(String v) 
    {
    
                  if (!ObjectUtils.equals(this.quizId, v))
              {
            this.quizId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the UserId
     *
     * @return String
     */
    public String getUserId()
    {
        return userId;
    }

                        
    /**
     * Set the value of UserId
     *
     * @param v new value
     */
    public void setUserId(String v) 
    {
    
                  if (!ObjectUtils.equals(this.userId, v))
              {
            this.userId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Cid
     *
     * @return String
     */
    public String getCid()
    {
        return cid;
    }

                        
    /**
     * Set the value of Cid
     *
     * @param v new value
     */
    public void setCid(String v) 
    {
    
                  if (!ObjectUtils.equals(this.cid, v))
              {
            this.cid = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the QuizTitle
     *
     * @return String
     */
    public String getQuizTitle()
    {
        return quizTitle;
    }

                        
    /**
     * Set the value of QuizTitle
     *
     * @param v new value
     */
    public void setQuizTitle(String v) 
    {
    
                  if (!ObjectUtils.equals(this.quizTitle, v))
              {
            this.quizTitle = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the StartTime
     *
     * @return Date
     */
    public Date getStartTime()
    {
        return startTime;
    }

                        
    /**
     * Set the value of StartTime
     *
     * @param v new value
     */
    public void setStartTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.startTime, v))
              {
            this.startTime = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the EndTime
     *
     * @return Date
     */
    public Date getEndTime()
    {
        return endTime;
    }

                        
    /**
     * Set the value of EndTime
     *
     * @param v new value
     */
    public void setEndTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.endTime, v))
              {
            this.endTime = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PostDate
     *
     * @return Date
     */
    public Date getPostDate()
    {
        return postDate;
    }

                        
    /**
     * Set the value of PostDate
     *
     * @param v new value
     */
    public void setPostDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.postDate, v))
              {
            this.postDate = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the MaxMarks
     *
     * @return int
     */
    public int getMaxMarks()
    {
        return maxMarks;
    }

                        
    /**
     * Set the value of MaxMarks
     *
     * @param v new value
     */
    public void setMaxMarks(int v) 
    {
    
                  if (this.maxMarks != v)
              {
            this.maxMarks = v;
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
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(QuizPeer.getMapBuilder()
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
                    QuizPeer.doInsert((Quiz) this, con);
                    setNew(false);
                }
                else
                {
                    QuizPeer.doUpdate((Quiz) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  id ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Quiz copy() throws TorqueException
    {
        return copyInto(new Quiz());
    }
  
    protected Quiz copyInto(Quiz copyObj) throws TorqueException
    {
          copyObj.setId(id);
          copyObj.setQuizId(quizId);
          copyObj.setUserId(userId);
          copyObj.setCid(cid);
          copyObj.setQuizTitle(quizTitle);
          copyObj.setStartTime(startTime);
          copyObj.setEndTime(endTime);
          copyObj.setPostDate(postDate);
          copyObj.setMaxMarks(maxMarks);
          copyObj.setExpiryDate(expiryDate);
  
                    copyObj.setId(0);
                                                                  
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public QuizPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Quiz:\n");
        str.append("Id = ")
           .append(getId())
           .append("\n");
        str.append("QuizId = ")
           .append(getQuizId())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("Cid = ")
           .append(getCid())
           .append("\n");
        str.append("QuizTitle = ")
           .append(getQuizTitle())
           .append("\n");
        str.append("StartTime = ")
           .append(getStartTime())
           .append("\n");
        str.append("EndTime = ")
           .append(getEndTime())
           .append("\n");
        str.append("PostDate = ")
           .append(getPostDate())
           .append("\n");
        str.append("MaxMarks = ")
           .append(getMaxMarks())
           .append("\n");
        str.append("ExpiryDate = ")
           .append(getExpiryDate())
           .append("\n");
        return(str.toString());
    }
}
