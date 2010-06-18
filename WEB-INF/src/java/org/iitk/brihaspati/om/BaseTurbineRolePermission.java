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
 * extended all references should be to TurbineRolePermission
 */
public abstract class BaseTurbineRolePermission extends BaseObject
{
    /** The Peer class */
    private static final TurbineRolePermissionPeer peer =
        new TurbineRolePermissionPeer();

        
    /** The value for the roleId field */
    private int roleId;
      
    /** The value for the permissionId field */
    private int permissionId;
  
    
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
  
    /**
     * Get the PermissionId
     *
     * @return int
     */
    public int getPermissionId()
    {
        return permissionId;
    }

                              
    /**
     * Set the value of PermissionId
     *
     * @param v new value
     */
    public void setPermissionId(int v) throws TorqueException
    {
    
                  if (this.permissionId != v)
              {
            this.permissionId = v;
            setModified(true);
        }
    
                          
                if (aTurbinePermission != null && !(aTurbinePermission.getPermissionId() == v))
                {
            aTurbinePermission = null;
        }
      
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
               obj.addTurbineRolePermissions(this);
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
    
    
                  
    
        private TurbinePermission aTurbinePermission;

    /**
     * Declares an association between this object and a TurbinePermission object
     *
     * @param v TurbinePermission
     * @throws TorqueException
     */
    public void setTurbinePermission(TurbinePermission v) throws TorqueException
    {
            if (v == null)
        {
                    setPermissionId(0);
                  }
        else
        {
            setPermissionId(v.getPermissionId());
        }
                aTurbinePermission = v;
    }

                                            
    /**
     * Get the associated TurbinePermission object
     *
     * @return the associated TurbinePermission object
     * @throws TorqueException
     */
    public TurbinePermission getTurbinePermission() throws TorqueException
    {
        if (aTurbinePermission == null && (this.permissionId > 0))
        {
                          aTurbinePermission = TurbinePermissionPeer.retrieveByPK(SimpleKey.keyFor(this.permissionId));
              
            /* The following can be used instead of the line above to
               guarantee the related object contains a reference
               to this object, but this level of coupling
               may be undesirable in many circumstances.
               As it can lead to a db query with many results that may
               never be used.
               TurbinePermission obj = TurbinePermissionPeer.retrieveByPK(this.permissionId);
               obj.addTurbineRolePermissions(this);
            */
        }
        return aTurbinePermission;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey.  e.g.
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
           */
    public void setTurbinePermissionKey(ObjectKey key) throws TorqueException
    {
      
                        setPermissionId(((NumberKey) key).intValue());
                  }
       
                
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
          save(TurbineRolePermissionPeer.getMapBuilder()
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
                    TurbineRolePermissionPeer.doInsert((TurbineRolePermission) this, con);
                    setNew(false);
                }
                else
                {
                    TurbineRolePermissionPeer.doUpdate((TurbineRolePermission) this, con);
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
                      setRoleId(((NumberKey)keys[0]).intValue());
                        setPermissionId(((NumberKey)keys[1]).intValue());
              }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
         * @param int roleId
         * @param int permissionId
         */
    public void setPrimaryKey( int roleId, int permissionId)
        throws TorqueException
    {
            setRoleId(roleId);
            setPermissionId(permissionId);
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
              pks[0] = SimpleKey.keyFor(getRoleId());
                  pks[1] = SimpleKey.keyFor(getPermissionId());
                  return comboPK;
      }

 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections and sets the
     * related objects to isNew=true.
       */
      public TurbineRolePermission copy() throws TorqueException
    {
        return copyInto(new TurbineRolePermission());
    }
  
    protected TurbineRolePermission copyInto(TurbineRolePermission copyObj) throws TorqueException
    {
          copyObj.setRoleId(roleId);
          copyObj.setPermissionId(permissionId);
  
                    copyObj.setRoleId(0);
                              copyObj.setPermissionId(0);
            
        
        return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TurbineRolePermissionPeer getPeer()
    {
        return peer;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("TurbineRolePermission:\n");
        str.append("RoleId = ")
           .append(getRoleId())
           .append("\n");
        str.append("PermissionId = ")
           .append(getPermissionId())
           .append("\n");
        return(str.toString());
    }
}
