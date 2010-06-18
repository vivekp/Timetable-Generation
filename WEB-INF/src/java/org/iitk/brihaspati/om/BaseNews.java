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
 * extended all references should be to News
 */
public abstract class BaseNews extends BaseObject
{
    /** The Peer class */
    private static final NewsPeer peer =
        new NewsPeer();

        
    /** The value for the newsId field */
    private int newsId;
      
    /** The value for the groupId field */
    private int groupId;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the newsTitle field */
    private String newsTitle;
      
    /** The value for the newsDescription field */
    private byte[] newsDescription;
      
    /** The value for the publishDate field */
    private Date publishDate;
      
    /** The value for the expiry field */
    private int expiry;
      
    /** The value for the expiryDate field */
    private Date expiryDate;
  
    
    /**
     * Get the NewsId
     *
     * @return int
     */
    public int getNewsId()
    {
        return newsId;
    }

                        
    /**
     * Set the value of NewsId
     *
     * @param v new value
     */
    public void setNewsId(int v) 
    {
    
                  if (this.newsId != v)
              {
            this.newsId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the GroupId
     *
     * @return int
     */
    public int getGroupId()
    {
        return groupId;
    }

                        
    /**
     * Set the value of GroupId
     *
     * @param v new value
     */
    public void setGroupId(int v) 
    {
    
                  if (this.groupId != v)
              {
            this.groupId = v;
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
     * Get the NewsTitle
     *
     * @return String
     */
    public String getNewsTitle()
    {
        return newsTitle;
    }

                        
    /**
     * Set the value of NewsTitle
     *
     * @param v new value
     */
    public void setNewsTitle(String v) 
    {
    
                  if (!ObjectUtils.equals(this.newsTitle, v))
              {
            this.newsTitle = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the NewsDescription
     *
     * @return byte[]
     */
    public byte[] getNewsDescription()
    {
        return newsDescription;
    }

                        
    /**
     * Set the value of NewsDescription
     *
     * @param v new value
     */
    public void setNewsDescription(byte[] v) 
    {
    
                  if (!ObjectUtils.equals(this.newsDescription, v))
              {
            this.newsDescription = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PublishDate
     *
     * @return Date
     */
    public Date getPublishDate()
    {
        return publishDate;
    }

                        
    /**
     * Set the value of PublishDate
     *
     * @param v new value
     */
    public void setPublishDate(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.publishDate, v))
              {
            this.publishDate = v;
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
          save(NewsPeer.getMapBuilder()
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
                    NewsPeer.doInsert((News) this, con);
                    setNew(false);
                }
                else
                {
                    NewsPeer.doUpdate((News) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  newsId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setNewsId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setNewsId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getNewsId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public News copy() throws TorqueException
    {
        return copyInto(new News());
    }
  
    protected News copyInto(News copyObj) throws TorqueException
    {
          copyObj.setNewsId(newsId);
          copyObj.setGroupId(groupId);
          copyObj.setUserId(userId);
          copyObj.setNewsTitle(newsTitle);
          copyObj.setNewsDescription(newsDescription);
          copyObj.setPublishDate(publishDate);
          copyObj.setExpiry(expiry);
          copyObj.setExpiryDate(expiryDate);
  
                    copyObj.setNewsId(0);
                                                      
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public NewsPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("News:\n");
        str.append("NewsId = ")
           .append(getNewsId())
           .append("\n");
        str.append("GroupId = ")
           .append(getGroupId())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("NewsTitle = ")
           .append(getNewsTitle())
           .append("\n");
        str.append("NewsDescription = ")
           .append(getNewsDescription())
           .append("\n");
        str.append("PublishDate = ")
           .append(getPublishDate())
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
