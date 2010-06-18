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
 * extended all references should be to Glossary
 */
public abstract class BaseGlossary extends BaseObject
{
    /** The Peer class */
    private static final GlossaryPeer peer =
        new GlossaryPeer();

        
    /** The value for the wordId field */
    private int wordId;
      
    /** The value for the word field */
    private String word;
      
    /** The value for the userId field */
    private int userId;
      
    /** The value for the definition field */
    private byte[] definition;
  
    
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
    
          
                                  
        // update associated GlossaryAlias
        if (collGlossaryAliass != null)
        {
            for (int i = 0; i < collGlossaryAliass.size(); i++)
            {
                ((GlossaryAlias) collGlossaryAliass.get(i))
                    .setWordId(v);
            }
        }
                      }
  
    /**
     * Get the Word
     *
     * @return String
     */
    public String getWord()
    {
        return word;
    }

                        
    /**
     * Set the value of Word
     *
     * @param v new value
     */
    public void setWord(String v) 
    {
    
                  if (!ObjectUtils.equals(this.word, v))
              {
            this.word = v;
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
     * Get the Definition
     *
     * @return byte[]
     */
    public byte[] getDefinition()
    {
        return definition;
    }

                        
    /**
     * Set the value of Definition
     *
     * @param v new value
     */
    public void setDefinition(byte[] v) 
    {
    
                  if (!ObjectUtils.equals(this.definition, v))
              {
            this.definition = v;
            setModified(true);
        }
    
          
              }
  
         
                                
            
    /**
     * Collection to store aggregation of collGlossaryAliass
     */
    protected List collGlossaryAliass;

    /**
     * Temporary storage of collGlossaryAliass to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initGlossaryAliass()
    {
        if (collGlossaryAliass == null)
        {
            collGlossaryAliass = new ArrayList();
        }
    }

    /**
     * Method called to associate a GlossaryAlias object to this object
     * through the GlossaryAlias foreign key attribute
     *
     * @param l GlossaryAlias
     * @throws TorqueException
     */
    public void addGlossaryAlias(GlossaryAlias l) throws TorqueException
    {
        getGlossaryAliass().add(l);
        l.setGlossary((Glossary) this);
    }

    /**
     * The criteria used to select the current contents of collGlossaryAliass
     */
    private Criteria lastGlossaryAliassCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getGlossaryAliass(new Criteria())
     *
     * @throws TorqueException
     */
    public List getGlossaryAliass() throws TorqueException
    {
        if (collGlossaryAliass == null)
        {
            collGlossaryAliass = getGlossaryAliass(new Criteria(10));
        }
        return collGlossaryAliass;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Glossary has previously
     * been saved, it will retrieve related GlossaryAliass from storage.
     * If this Glossary is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getGlossaryAliass(Criteria criteria) throws TorqueException
    {
        if (collGlossaryAliass == null)
        {
            if (isNew())
            {
               collGlossaryAliass = new ArrayList();
            }
            else
            {
                      criteria.add(GlossaryAliasPeer.WORD_ID, getWordId() );
                      collGlossaryAliass = GlossaryAliasPeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                      criteria.add(GlossaryAliasPeer.WORD_ID, getWordId());
                      if (!lastGlossaryAliassCriteria.equals(criteria))
                {
                    collGlossaryAliass = GlossaryAliasPeer.doSelect(criteria);
                }
            }
        }
        lastGlossaryAliassCriteria = criteria;

        return collGlossaryAliass;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getGlossaryAliass(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getGlossaryAliass(Connection con) throws TorqueException
    {
        if (collGlossaryAliass == null)
        {
            collGlossaryAliass = getGlossaryAliass(new Criteria(10), con);
        }
        return collGlossaryAliass;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Glossary has previously
     * been saved, it will retrieve related GlossaryAliass from storage.
     * If this Glossary is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getGlossaryAliass(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collGlossaryAliass == null)
        {
            if (isNew())
            {
               collGlossaryAliass = new ArrayList();
            }
            else
            {
                       criteria.add(GlossaryAliasPeer.WORD_ID, getWordId());
                       collGlossaryAliass = GlossaryAliasPeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                       criteria.add(GlossaryAliasPeer.WORD_ID, getWordId());
                       if (!lastGlossaryAliassCriteria.equals(criteria))
                 {
                     collGlossaryAliass = GlossaryAliasPeer.doSelect(criteria, con);
                 }
             }
         }
         lastGlossaryAliassCriteria = criteria;

         return collGlossaryAliass;
     }

                  
              
                    
                              
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Glossary is new, it will return
     * an empty collection; or if this Glossary has previously
     * been saved, it will retrieve related GlossaryAliass from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Glossary.
     */
    protected List getGlossaryAliassJoinGlossary(Criteria criteria)
        throws TorqueException
    {
        if (collGlossaryAliass == null)
        {
            if (isNew())
            {
               collGlossaryAliass = new ArrayList();
            }
            else
            {
                            criteria.add(GlossaryAliasPeer.WORD_ID, getWordId());
                            collGlossaryAliass = GlossaryAliasPeer.doSelectJoinGlossary(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(GlossaryAliasPeer.WORD_ID, getWordId());
                        if (!lastGlossaryAliassCriteria.equals(criteria))
            {
                collGlossaryAliass = GlossaryAliasPeer.doSelectJoinGlossary(criteria);
            }
        }
        lastGlossaryAliassCriteria = criteria;

        return collGlossaryAliass;
    }
                            


          
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(GlossaryPeer.getMapBuilder()
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
                    GlossaryPeer.doInsert((Glossary) this, con);
                    setNew(false);
                }
                else
                {
                    GlossaryPeer.doUpdate((Glossary) this, con);
                }
            }

                                      
                
            if (collGlossaryAliass != null)
            {
                for (int i = 0; i < collGlossaryAliass.size(); i++)
                {
                    ((GlossaryAlias) collGlossaryAliass.get(i)).save(con);
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
                      setWordId(((NumberKey)keys[0]).intValue());
                        setWord(keys[1].toString());
              }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
         * @param int wordId
         * @param String word
         */
    public void setPrimaryKey( int wordId, String word)
        throws TorqueException
    {
            setWordId(wordId);
            setWord(word);
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
              pks[0] = SimpleKey.keyFor(getWordId());
                  pks[1] = SimpleKey.keyFor(getWord());
                  return comboPK;
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Glossary copy() throws TorqueException
    {
        return copyInto(new Glossary());
    }
  
    protected Glossary copyInto(Glossary copyObj) throws TorqueException
    {
          copyObj.setWordId(wordId);
          copyObj.setWord(word);
          copyObj.setUserId(userId);
          copyObj.setDefinition(definition);
  
                    copyObj.setWordId(0);
                              copyObj.setWord((String)null);
                        
                                      
                
        List v = getGlossaryAliass();
        for (int i = 0; i < v.size(); i++)
        {
            GlossaryAlias obj = (GlossaryAlias) v.get(i);
            copyObj.addGlossaryAlias(obj.copy());
        }
                    
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public GlossaryPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Glossary:\n");
        str.append("WordId = ")
           .append(getWordId())
           .append("\n");
        str.append("Word = ")
           .append(getWord())
           .append("\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("Definition = ")
           .append(getDefinition())
           .append("\n");
        return(str.toString());
    }
}
