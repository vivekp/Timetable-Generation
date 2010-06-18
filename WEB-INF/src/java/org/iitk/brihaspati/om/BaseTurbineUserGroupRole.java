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
 * extended all references should be to TurbineUserGroupRole
 */
public abstract class BaseTurbineUserGroupRole extends BaseObject
{
    /** The Peer class */
    private static final TurbineUserGroupRolePeer peer =
        new TurbineUserGroupRolePeer();

        
    /** The value for the userId field */
    private int userId;
      
    /** The value for the groupId field */
    private int groupId;
      
    /** The value for the roleId field */
    private int roleId;
  
    
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
    
                          
                if (aTurbineUser != null && !(aTurbineUser.getUserId() == v))
                {
            aTurbineUser = null;
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
    public void setGroupId(int v) throws TorqueException
    {
    
                  if (this.groupId != v)
              {
            this.groupId = v;
            setModified(true);
        }
    
                          
                if (aTurbineGroup != null && !(aTurbineGroup.getGroupId() == v))
                {
            aTurbineGroup = null;
        }
      
              }
  
    /**
     * Get the RoleId
     *
     * @return int
     */
    public int getRoleId()
    {
        return roleId;
    }

                              
    /**
     * Set the value of RoleId
     *
     * @param v new value
     */
    public void setRoleId(int v) throws TorqueException
    {
    
                  if (this.roleId != v)
              {
            this.roleId = v;
            setModified(true);
        }
    
                          
                if (aTurbineRole != null && !(aTurbineRole.getRoleId() == v))
                {
            aTurbineRole = null;
        }
      
              }
  
      
    
                  
    
        private TurbineUser aTurbineUser;

    /**
     * Declares an association between this object and a TurbineUser object
     *
     * @param v TurbineUser
     * @throws TorqueException
     */
    public void setTurbineUser(TurbineUser v) throws TorqueException
    {
            if (v == null)
        {
                    setUserId(0);
                  }
        else
        {
            setUserId(v.getUserId());
        }
                aTurbineUser = v;
    }

                                            
    /**
     * Get the associated TurbineUser object
     *
     * @return the associated TurbineUser object
     * @throws TorqueException
     */
    public TurbineUser getTurbineUser() throws TorqueException
    {
        if (aTurbineUser == null && (this.userId > 0))
        {
                          aTurbineUser = TurbineUserPeer.retrieveByPK(SimpleKey.keyFor(this.userId));
              
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               TurbineUser obj = TurbineUserPeer.retrieveByPK(this.userId);
               obj.addTurbineUserGroupRoles(this);
            */
        }
        return aTurbineUser;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
           */
    public void setTurbineUserKey(ObjectKey key) throws TorqueException
    {
      
                        setUserId(((NumberKey) key).intValue());
                  }
    
    
                  
    
        private TurbineGroup aTurbineGroup;

    /**
     * Declares an association between this object and a TurbineGroup object
     *
     * @param v TurbineGroup
     * @throws TorqueException
     */
    public void setTurbineGroup(TurbineGroup v) throws TorqueException
    {
            if (v == null)
        {
                    setGroupId(0);
                  }
        else
        {
            setGroupId(v.getGroupId());
        }
                aTurbineGroup = v;
    }

                                            
    /**
     * Get the associated TurbineGroup object
     *
     * @return the associated TurbineGroup object
     * @throws TorqueException
     */
    public TurbineGroup getTurbineGroup() throws TorqueException
    {
        if (aTurbineGroup == null && (this.groupId > 0))
        {
                          aTurbineGroup = TurbineGroupPeer.retrieveByPK(SimpleKey.keyFor(this.groupId));
              
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               TurbineGroup obj = TurbineGroupPeer.retrieveByPK(this.groupId);
               obj.addTurbineUserGroupRoles(this);
            */
        }
        return aTurbineGroup;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
           */
    public void setTurbineGroupKey(ObjectKey key) throws TorqueException
    {
      
                        setGroupId(((NumberKey) key).intValue());
                  }
    
    
                  
    
        private TurbineRole aTurbineRole;

    /**
     * Declares an association between this object and a TurbineRole object
     *
     * @param v TurbineRole
     * @throws TorqueException
     */
    public void setTurbineRole(TurbineRole v) throws TorqueException
    {
            if (v == null)
        {
                    setRoleId(0);
                  }
        else
        {
            setRoleId(v.getRoleId());
        }
                aTurbineRole = v;
    }

                                            
    /**
     * Get the associated TurbineRole object
     *
     * @return the associated TurbineRole object
     * @throws TorqueException
     */
    public TurbineRole getTurbineRole() throws TorqueException
    {
        if (aTurbineRole == null && (this.roleId > 0))
        {
                          aTurbineRole = TurbineRolePeer.retrieveByPK(SimpleKey.keyFor(this.roleId));
              
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               TurbineRole obj = TurbineRolePeer.retrieveByPK(this.roleId);
               obj.addTurbineUserGroupRoles(this);
            */
        }
        return aTurbineRole;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
           */
    public void setTurbineRoleKey(ObjectKey key) throws TorqueException
    {
      
                        setRoleId(((NumberKey) key).intValue());
                  }
       
                
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(TurbineUserGroupRolePeer.getMapBuilder()
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
                    TurbineUserGroupRolePeer.doInsert((TurbineUserGroupRole) this, con);
                    setNew(false);
                }
                else
                {
                    TurbineUserGroupRolePeer.doUpdate((TurbineUserGroupRole) this, con);
                }
            }

                      alreadyInSave = false;
        }
      }


                                                                      
  
    private final SimpleKey[] pks = new SimpleKey[3];
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
                      setUserId(((NumberKey)keys[0]).intValue());
                        setGroupId(((NumberKey)keys[1]).intValue());
                        setRoleId(((NumberKey)keys[2]).intValue());
              }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
         * @param int userId
         * @param int groupId
         * @param int roleId
         */
    public void setPrimaryKey( int userId, int groupId, int roleId)
        throws TorqueException
    {
            setUserId(userId);
            setGroupId(groupId);
            setRoleId(roleId);
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
              pks[0] = SimpleKey.keyFor(getUserId());
                  pks[1] = SimpleKey.keyFor(getGroupId());
                  pks[2] = SimpleKey.keyFor(getRoleId());
                  return comboPK;
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public TurbineUserGroupRole copy() throws TorqueException
    {
        return copyInto(new TurbineUserGroupRole());
    }
  
    protected TurbineUserGroupRole copyInto(TurbineUserGroupRole copyObj) throws TorqueException
    {
          copyObj.setUserId(userId);
          copyObj.setGroupId(groupId);
          copyObj.setRoleId(roleId);
  
                    copyObj.setUserId(0);
                              copyObj.setGroupId(0);
                              copyObj.setRoleId(0);
            
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TurbineUserGroupRolePeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("TurbineUserGroupRole:\n");
        str.append("UserId = ")
           .append(getUserId())
           .append("\n");
        str.append("GroupId = ")
           .append(getGroupId())
           .append("\n");
        str.append("RoleId = ")
           .append(getRoleId())
           .append("\n");
        return(str.toString());
    }
}
