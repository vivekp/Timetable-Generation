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
 * extended all references should be to ResearchRepository
 */
public abstract class BaseResearchRepository extends BaseObject
{
    /** The Peer class */
    private static final ResearchRepositoryPeer peer =
        new ResearchRepositoryPeer();

        
    /** The value for the subjectId field */
    private int subjectId;
      
    /** The value for the replyId field */
    private int replyId;
      
    /** The value for the subject field */
    private String subject;
      
    /** The value for the replies field */
    private int replies;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the repoName field */
    private String repoName;
      
    /** The value for the postTime field */
    private Date postTime;
      
    /** The value for the expiry field */
    private int expiry;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
  
    
    /**
     * Get the SubjectId
     *
     * @return int
     */
    public int getSubjectId()
    {
        return subjectId;
    }

                        
    /**
     * Set the value of SubjectId
     *
     * @param v new value
     */
    public void setSubjectId(int v) 
    {
    
                  if (this.subjectId != v)
              {
            this.subjectId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ReplyId
     *
     * @return int
     */
    public int getReplyId()
    {
        return replyId;
    }

                        
    /**
     * Set the value of ReplyId
     *
     * @param v new value
     */
    public void setReplyId(int v) 
    {
    
                  if (this.replyId != v)
              {
            this.replyId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Subject
     *
     * @return String
     */
    public String getSubject()
    {
        return subject;
    }

                        
    /**
     * Set the value of Subject
     *
     * @param v new value
     */
    public void setSubject(String v) 
    {
    
                  if (!ObjectUtils.equals(this.subject, v))
              {
            this.subject = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Replies
     *
     * @return int
     */
    public int getReplies()
    {
        return replies;
    }

                        
    /**
     * Set the value of Replies
     *
     * @param v new value
     */
    public void setReplies(int v) 
    {
    
                  if (this.replies != v)
              {
            this.replies = v;
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
     * Get the RepoName
     *
     * @return String
     */
    public String getRepoName()
    {
        return repoName;
    }

                        
    /**
     * Set the value of RepoName
     *
     * @param v new value
     */
    public void setRepoName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.repoName, v))
              {
            this.repoName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PostTime
     *
     * @return Date
     */
    public Date getPostTime()
    {
        return postTime;
    }

                        
    /**
     * Set the value of PostTime
     *
     * @param v new value
     */
    public void setPostTime(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.postTime, v))
              {
            this.postTime = v;
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
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(ResearchRepositoryPeer.getMapBuilder()
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
                    ResearchRepositoryPeer.doInsert((ResearchRepository) this, con);
                    setNew(false);
                }
                else
                {
                    ResearchRepositoryPeer.doUpdate((ResearchRepository) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  subjectId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setSubjectId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setSubjectId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getSubjectId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public ResearchRepository copy() throws TorqueException
    {
        return copyInto(new ResearchRepository());
    }
  
    protected ResearchRepository copyInto(ResearchRepository copyObj) throws TorqueException
    {
          copyObj.setSubjectId(subjectId);
          copyObj.setReplyId(replyId);
          copyObj.setSubject(subject);
          copyObj.setReplies(replies);
          copyObj.setUserId(userId);
          copyObj.setRepoName(repoName);
          copyObj.setPostTime(postTime);
          copyObj.setExpiry(expiry);
          copyObj.setExpiryDate(expiryDate);
  
                    copyObj.setSubjectId(0);
                                                            
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public ResearchRepositoryPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("ResearchRepository:\n");
        str.append("SubjectId = ")
           .append(getSubjectId())
           .append("\n");
        str.append("ReplyId = ")
           .append(getReplyId())
           .append("\n");
        str.append("Subject = ")
           .append(getSubject())
           .append("\n");
        str.append("Replies = ")
           .append(getReplies())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("RepoName = ")
           .append(getRepoName())
           .append("\n");
        str.append("PostTime = ")
           .append(getPostTime())
           .append("\n");
        str.append("Expiry = ")
           .append(getExpiry())
           .append("\n");
        str.append("ExpiryDate = ")
           .append(getExpiryDate())
           .append("\n");
        return(str.toString());
    }
}
