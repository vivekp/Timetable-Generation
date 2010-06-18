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
 * extended all references should be to TurbineUser
 */
public abstract class BaseTurbineUser extends BaseObject
{
    /** The Peer class */
    private static final TurbineUserPeer peer =
        new TurbineUserPeer();

        
    /** The value for the userId field */
    private int userId;
      
    /** The value for the loginName field */
    private String loginName;
      
    /** The value for the passwordValue field */
    private String passwordValue;
      
    /** The value for the firstName field */
    private String firstName;
      
    /** The value for the lastName field */
    private String lastName;
      
    /** The value for the email field */
    private String email;
      
    /** The value for the confirmValue field */
    private String confirmValue;
      
    /** The value for the modified field */
    private Date modified;
      
    /** The value for the created field */
    private Date created;
      
    /** The value for the lastLogin field */
    private Date lastLogin;
      
    /** The value for the objectdata field */
    private byte[] objectdata;
      
    /** The value for the quota field */
    private BigDecimal quota;
  
    
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
    public void setUserId(int v) throws TorqueException
    {
    
                  if (this.userId != v)
              {
            this.userId = v;
            setModified(true);
        }
    
          
                                  
        // update associated TurbineUserGroupRole
        if (collTurbineUserGroupRoles != null)
        {
            for (int i = 0; i < collTurbineUserGroupRoles.size(); i++)
            {
                ((TurbineUserGroupRole) collTurbineUserGroupRoles.get(i))
                    .setUserId(v);
            }
        }
                      }
  
    /**
     * Get the LoginName
     *
     * @return String
     */
    public String getLoginName()
    {
        return loginName;
    }

                        
    /**
     * Set the value of LoginName
     *
     * @param v new value
     */
    public void setLoginName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.loginName, v))
              {
            this.loginName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the PasswordValue
     *
     * @return String
     */
    public String getPasswordValue()
    {
        return passwordValue;
    }

                        
    /**
     * Set the value of PasswordValue
     *
     * @param v new value
     */
    public void setPasswordValue(String v) 
    {
    
                  if (!ObjectUtils.equals(this.passwordValue, v))
              {
            this.passwordValue = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the FirstName
     *
     * @return String
     */
    public String getFirstName()
    {
        return firstName;
    }

                        
    /**
     * Set the value of FirstName
     *
     * @param v new value
     */
    public void setFirstName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.firstName, v))
              {
            this.firstName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the LastName
     *
     * @return String
     */
    public String getLastName()
    {
        return lastName;
    }

                        
    /**
     * Set the value of LastName
     *
     * @param v new value
     */
    public void setLastName(String v) 
    {
    
                  if (!ObjectUtils.equals(this.lastName, v))
              {
            this.lastName = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Email
     *
     * @return String
     */
    public String getEmail()
    {
        return email;
    }

                        
    /**
     * Set the value of Email
     *
     * @param v new value
     */
    public void setEmail(String v) 
    {
    
                  if (!ObjectUtils.equals(this.email, v))
              {
            this.email = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the ConfirmValue
     *
     * @return String
     */
    public String getConfirmValue()
    {
        return confirmValue;
    }

                        
    /**
     * Set the value of ConfirmValue
     *
     * @param v new value
     */
    public void setConfirmValue(String v) 
    {
    
                  if (!ObjectUtils.equals(this.confirmValue, v))
              {
            this.confirmValue = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Modified
     *
     * @return Date
     */
    public Date getModified()
    {
        return modified;
    }

                        
    /**
     * Set the value of Modified
     *
     * @param v new value
     */
    public void setModified(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.modified, v))
              {
            this.modified = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Created
     *
     * @return Date
     */
    public Date getCreated()
    {
        return created;
    }

                        
    /**
     * Set the value of Created
     *
     * @param v new value
     */
    public void setCreated(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.created, v))
              {
            this.created = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the LastLogin
     *
     * @return Date
     */
    public Date getLastLogin()
    {
        return lastLogin;
    }

                        
    /**
     * Set the value of LastLogin
     *
     * @param v new value
     */
    public void setLastLogin(Date v) 
    {
    
                  if (!ObjectUtils.equals(this.lastLogin, v))
              {
            this.lastLogin = v;
            setModified(true);
        }
    
          
              }
  
    /**
     * Get the Objectdata
     *
     * @return byte[]
     */
    public byte[] getObjectdata()
    {
        return objectdata;
    }

                        
    /**
     * Set the value of Objectdata
     *
     * @param v new value
     */
    public void setObjectdata(byte[] v) 
    {
    
                  if (!ObjectUtils.equals(this.objectdata, v))
              {
            this.objectdata = v;
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
     * Collection to store aggregation of collTurbineUserGroupRoles
     */
    protected List collTurbineUserGroupRoles;

    /**
     * Temporary storage of collTurbineUserGroupRoles to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initTurbineUserGroupRoles()
    {
        if (collTurbineUserGroupRoles == null)
        {
            collTurbineUserGroupRoles = new ArrayList();
        }
    }

    /**
     * Method called to associate a TurbineUserGroupRole object to this object
     * through the TurbineUserGroupRole foreign key attribute
     *
     * @param l TurbineUserGroupRole
     * @throws TorqueException
     */
    public void addTurbineUserGroupRole(TurbineUserGroupRole l) throws TorqueException
    {
        getTurbineUserGroupRoles().add(l);
        l.setTurbineUser((TurbineUser) this);
    }

    /**
     * The criteria used to select the current contents of collTurbineUserGroupRoles
     */
    private Criteria lastTurbineUserGroupRolesCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getTurbineUserGroupRoles(new Criteria())
     *
     * @throws TorqueException
     */
    public List getTurbineUserGroupRoles() throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            collTurbineUserGroupRoles = getTurbineUserGroupRoles(new Criteria(10));
        }
        return collTurbineUserGroupRoles;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineUser has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     * If this TurbineUser is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getTurbineUserGroupRoles(Criteria criteria) throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                      criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId() );
                      collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria);
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
                      criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                      if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
                {
                    collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria);
                }
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getTurbineUserGroupRoles(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getTurbineUserGroupRoles(Connection con) throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            collTurbineUserGroupRoles = getTurbineUserGroupRoles(new Criteria(10), con);
        }
        return collTurbineUserGroupRoles;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineUser has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     * If this TurbineUser is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getTurbineUserGroupRoles(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                       criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                       collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria, con);
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
                       criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                       if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
                 {
                     collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelect(criteria, con);
                 }
             }
         }
         lastTurbineUserGroupRolesCriteria = criteria;

         return collTurbineUserGroupRoles;
     }

                              
              
                    
                              
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineUser is new, it will return
     * an empty collection; or if this TurbineUser has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineUser.
     */
    protected List getTurbineUserGroupRolesJoinTurbineUser(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                            collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineUser(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                        if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
            {
                collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineUser(criteria);
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }
                  
                    
                    
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineUser is new, it will return
     * an empty collection; or if this TurbineUser has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineUser.
     */
    protected List getTurbineUserGroupRolesJoinTurbineGroup(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                            collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineGroup(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                        if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
            {
                collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineGroup(criteria);
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }
                  
                    
                    
                                
                                                              
                                        
                    
                    
          
    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this TurbineUser is new, it will return
     * an empty collection; or if this TurbineUser has previously
     * been saved, it will retrieve related TurbineUserGroupRoles from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in TurbineUser.
     */
    protected List getTurbineUserGroupRolesJoinTurbineRole(Criteria criteria)
        throws TorqueException
    {
        if (collTurbineUserGroupRoles == null)
        {
            if (isNew())
            {
               collTurbineUserGroupRoles = new ArrayList();
            }
            else
            {
                            criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                            collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineRole(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            boolean newCriteria = true;
                        criteria.add(TurbineUserGroupRolePeer.USER_ID, getUserId());
                        if (!lastTurbineUserGroupRolesCriteria.equals(criteria))
            {
                collTurbineUserGroupRoles = TurbineUserGroupRolePeer.doSelectJoinTurbineRole(criteria);
            }
        }
        lastTurbineUserGroupRolesCriteria = criteria;

        return collTurbineUserGroupRoles;
    }
                            


          
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(TurbineUserPeer.getMapBuilder()
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
                    TurbineUserPeer.doInsert((TurbineUser) this, con);
                    setNew(false);
                }
                else
                {
                    TurbineUserPeer.doUpdate((TurbineUser) this, con);
                }
            }

                                      
                
            if (collTurbineUserGroupRoles != null)
            {
                for (int i = 0; i < collTurbineUserGroupRoles.size(); i++)
                {
                    ((TurbineUserGroupRole) collTurbineUserGroupRoles.get(i)).save(con);
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
        throws TorqueException
    {
            setUserId(((NumberKey) key).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) throws TorqueException
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
      public TurbineUser copy() throws TorqueException
    {
        return copyInto(new TurbineUser());
    }
  
    protected TurbineUser copyInto(TurbineUser copyObj) throws TorqueException
    {
          copyObj.setUserId(userId);
          copyObj.setLoginName(loginName);
          copyObj.setPasswordValue(passwordValue);
          copyObj.setFirstName(firstName);
          copyObj.setLastName(lastName);
          copyObj.setEmail(email);
          copyObj.setConfirmValue(confirmValue);
          copyObj.setModified(modified);
          copyObj.setCreated(created);
          copyObj.setLastLogin(lastLogin);
          copyObj.setObjectdata(objectdata);
          copyObj.setQuota(quota);
  
                    copyObj.setUserId(0);
                                                                              
                                      
                
        List v = getTurbineUserGroupRoles();
        for (int i = 0; i < v.size(); i++)
        {
            TurbineUserGroupRole obj = (TurbineUserGroupRole) v.get(i);
            copyObj.addTurbineUserGroupRole(obj.copy());
        }
                    
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TurbineUserPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("TurbineUser:\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("LoginName = ")
           .append(getLoginName())
           .append("\n");
        str.append("PasswordValue = ")
           .append(getPasswordValue())
           .append("\n");
        str.append("FirstName = ")
           .append(getFirstName())
           .append("\n");
        str.append("LastName = ")
           .append(getLastName())
           .append("\n");
        str.append("Email = ")
           .append(getEmail())
           .append("\n");
        str.append("ConfirmValue = ")
           .append(getConfirmValue())
           .append("\n");
        str.append("Modified = ")
           .append(getModified())
           .append("\n");
        str.append("Created = ")
           .append(getCreated())
           .append("\n");
        str.append("LastLogin = ")
           .append(getLastLogin())
           .append("\n");
        str.append("Objectdata = ")
           .append(getObjectdata())
           .append("\n");
        str.append("Quota = ")
           .append(getQuota())
           .append("\n");
        return(str.toString());
    }
}
