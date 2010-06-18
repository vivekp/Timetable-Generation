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
 * extended all references should be to Note
 */
public abstract class BaseNote extends BaseObject
{
    /** The Peer class */
    private static final NotePeer peer =
        new NotePeer();

        
    /** The value for the noteId field */
    private int noteId;
      
    /** The value for the postedBy field */
    private String postedBy;
      
    /** The value for the datePosted field */
    private Date datePosted;
      
    /** The value for the note field */
    private String note;
  
    
    /**
     * Get the NoteId
     *
     * @return int
     */
    public int getNoteId()
    {
        return noteId;
    }

                        
    /**
     * Set the value of NoteId
     *
     * @param v new value
     */
    public void setNoteId(int v) 
    {
    
                  if (this.noteId != v)
              {
            this.noteId = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PostedBy
     *
     * @return String
     */
    public String getPostedBy()
    {
        return postedBy;
    }

                        
    /**
     * Set the value of PostedBy
     *
     * @param v new value
     */
    public void setPostedBy(String v) 
    {
    
                  if (!ObjectUtils.equals(this.postedBy, v))
              {
            this.postedBy = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the DatePosted
     *
     * @return Date
     */
    public Date getDatePosted()
    {
        return datePosted;
    }

                        
    /**
     * Set the value of DatePosted
     *
     * @param v new value
     */
    public void setDatePosted(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.datePosted, v))
              {
            this.datePosted = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Note
     *
     * @return String
     */
    public String getNote()
    {
        return note;
    }

                        
    /**
     * Set the value of Note
     *
     * @param v new value
     */
    public void setNote(String v) 
    {
    
                  if (!ObjectUtils.equals(this.note, v))
              {
            this.note = v;
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
          save(NotePeer.getMapBuilder()
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
                    NotePeer.doInsert((Note) this, con);
                    setNew(false);
                }
                else
                {
                    NotePeer.doUpdate((Note) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                    
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param  noteId ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        
    {
            setNoteId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) 
    {
            setNoteId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getNoteId());
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public Note copy() throws TorqueException
    {
        return copyInto(new Note());
    }
  
    protected Note copyInto(Note copyObj) throws TorqueException
    {
          copyObj.setNoteId(noteId);
          copyObj.setPostedBy(postedBy);
          copyObj.setDatePosted(datePosted);
          copyObj.setNote(note);
  
                    copyObj.setNoteId(0);
                              
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public NotePeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Note:\n");
        str.append("NoteId = ")
           .append(getNoteId())
           .append("\n");
        str.append("PostedBy = ")
           .append(getPostedBy())
           .append("\n");
        str.append("DatePosted = ")
           .append(getDatePosted())
           .append("\n");
        str.append("Note = ")
           .append(getNote())
           .append("\n");
        return(str.toString());
    }
}
