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
 * extended all references should be to GlossaryAlias
 */
public abstract class BaseGlossaryAlias extends BaseObject
{
    /** The Peer class */
    private static final GlossaryAliasPeer peer =
        new GlossaryAliasPeer();

        
    /** The value for the aliasId field */
    private int aliasId;
      
    /** The value for the wordAlias field */
    private String wordAlias;
      
    /** The value for the wordId field */
    private int wordId;
  
    
    /**
     * Get the AliasId
     *
     * @return int
     */
    public int getAliasId()
    {
        return aliasId;
    }

                        
    /**
     * Set the value of AliasId
     *
     * @param v new value
     */
    public void setAliasId(int v) 
    {
    
                  if (this.aliasId != v)
              {
            this.aliasId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the WordAlias
     *
     * @return String
     */
    public String getWordAlias()
    {
        return wordAlias;
    }

                        
    /**
     * Set the value of WordAlias
     *
     * @param v new value
     */
    public void setWordAlias(String v) 
    {
    
                  if (!ObjectUtils.equals(this.wordAlias, v))
              {
            this.wordAlias = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the WordId
     *
     * @return int
     */
    public int getWordId()
    {
        return wordId;
    }

                              
    /**
     * Set the value of WordId
     *
     * @param v new value
     */
    public void setWordId(int v) throws TorqueException
    {
    
                  if (this.wordId != v)
              {
            this.wordId = v;
            setModified(true);
        }
    
                          
                if (aGlossary != null && !(aGlossary.getWordId() == v))
                {
            aGlossary = null;
        }
      
              }
  
      
    
                  
    
        private Glossary aGlossary;

    /**
     * Declares an association between this object and a Glossary object
     *
     * @param v Glossary
     * @throws TorqueException
     */
    public void setGlossary(Glossary v) throws TorqueException
    {
            if (v == null)
        {
                    setWordId(0);
                  }
        else
        {
            setWordId(v.getWordId());
        }
                aGlossary = v;
    }

                                            
    /**
     * Get the associated Glossary object
     *
     * @return the associated Glossary object
     * @throws TorqueException
     */
    public Glossary getGlossary() throws TorqueException
    {
        if (aGlossary == null && (this.wordId > 0))
        {
                          aGlossary = GlossaryPeer.retrieveByPK(SimpleKey.keyFor(this.wordId));
              
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               Glossary obj = GlossaryPeer.retrieveByPK(this.wordId);
               obj.addGlossaryAliass(this);
            */
        }
        return aGlossary;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
           */
    public void setGlossaryKey(ObjectKey key) throws TorqueException
    {
      
                        setWordId(((NumberKey) key).intValue());
                  }
       
                
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(GlossaryAliasPeer.getMapBuilder()
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
                    GlossaryAliasPeer.doInsert((GlossaryAlias) this, con);
                    setNew(false);
                }
                else
                {
                    GlossaryAliasPeer.doUpdate((GlossaryAlias) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  aliasId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setAliasId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setAliasId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getAliasId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public GlossaryAlias copy() throws TorqueException
    {
        return copyInto(new GlossaryAlias());
    }
  
    protected GlossaryAlias copyInto(GlossaryAlias copyObj) throws TorqueException
    {
          copyObj.setAliasId(aliasId);
          copyObj.setWordAlias(wordAlias);
          copyObj.setWordId(wordId);
  
                    copyObj.setAliasId(0);
                        
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public GlossaryAliasPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("GlossaryAlias:\n");
        str.append("AliasId = ")
           .append(getAliasId())
           .append("\n");
        str.append("WordAlias = ")
           .append(getWordAlias())
           .append("\n");
        str.append("WordId = ")
           .append(getWordId())
           .append("\n");
        return(str.toString());
    }
}
