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
 * extended all references should be to Rdf
 */
public abstract class BaseRdf extends BaseObject
{
    /** The Peer class */
    private static final RdfPeer peer =
        new RdfPeer();

        
    /** The value for the rdfId field */
    private int rdfId;
      
    /** The value for the title field */
    private String title;
      
    /** The value for the body field */
    private String body;
      
    /** The value for the url field */
    private String url;
      
    /** The value for the author field */
    private String author;
      
    /** The value for the dept field */
    private String dept;
  
    
    /**
     * Get the RdfId
     *
     * @return int
     */
    public int getRdfId()
    {
        return rdfId;
    }

                        
    /**
     * Set the value of RdfId
     *
     * @param v new value
     */
    public void setRdfId(int v) 
    {
    
                  if (this.rdfId != v)
              {
            this.rdfId = v;
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
     * Get the Body
     *
     * @return String
     */
    public String getBody()
    {
        return body;
    }

                        
    /**
     * Set the value of Body
     *
     * @param v new value
     */
    public void setBody(String v) 
    {
    
                  if (!ObjectUtils.equals(this.body, v))
              {
            this.body = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Url
     *
     * @return String
     */
    public String getUrl()
    {
        return url;
    }

                        
    /**
     * Set the value of Url
     *
     * @param v new value
     */
    public void setUrl(String v) 
    {
    
                  if (!ObjectUtils.equals(this.url, v))
              {
            this.url = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Author
     *
     * @return String
     */
    public String getAuthor()
    {
        return author;
    }

                        
    /**
     * Set the value of Author
     *
     * @param v new value
     */
    public void setAuthor(String v) 
    {
    
                  if (!ObjectUtils.equals(this.author, v))
              {
            this.author = v;
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
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(RdfPeer.getMapBuilder()
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
                    RdfPeer.doInsert((Rdf) this, con);
                    setNew(false);
                }
                else
                {
                    RdfPeer.doUpdate((Rdf) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  rdfId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setRdfId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setRdfId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getRdfId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Rdf copy() throws TorqueException
    {
        return copyInto(new Rdf());
    }
  
    protected Rdf copyInto(Rdf copyObj) throws TorqueException
    {
          copyObj.setRdfId(rdfId);
          copyObj.setTitle(title);
          copyObj.setBody(body);
          copyObj.setUrl(url);
          copyObj.setAuthor(author);
          copyObj.setDept(dept);
  
                    copyObj.setRdfId(0);
                                          
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public RdfPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Rdf:\n");
        str.append("RdfId = ")
           .append(getRdfId())
           .append("\n");
        str.append("Title = ")
           .append(getTitle())
           .append("\n");
        str.append("Body = ")
           .append(getBody())
           .append("\n");
        str.append("Url = ")
           .append(getUrl())
           .append("\n");
        str.append("Author = ")
           .append(getAuthor())
           .append("\n");
        str.append("Dept = ")
           .append(getDept())
           .append("\n");
        return(str.toString());
    }
}
