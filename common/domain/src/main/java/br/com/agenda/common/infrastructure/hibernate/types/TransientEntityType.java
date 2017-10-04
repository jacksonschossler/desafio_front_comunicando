package br.com.agenda.common.infrastructure.hibernate.types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import br.com.agenda.common.domain.entity.IEntity;

/**
 * 
 * @author rodrigo.p.fraga
 */
public class TransientEntityType implements UserType, ParameterizedType, CompositeUserType
{
	public static final TransientEntityType INSTANCE = new TransientEntityType();
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private Class<? extends IEntity> entityClass;

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	@Override
	public int[] sqlTypes()
	{
		return new int[]{ LongType.INSTANCE.sqlType() };
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Class returnedClass()
	{
		return IEntity.class;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean equals( Object x, Object y ) throws HibernateException
	{
		return Objects.equals( x, y );
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	@Override
	public int hashCode( Object x ) throws HibernateException
	{
		return Objects.hashCode( x );
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object nullSafeGet( ResultSet rs, String[] names, SessionImplementor session, Object owner ) throws HibernateException, SQLException
	{
		final String columnName = names[0];
        final long columnValue = rs.getLong( columnName );
        
        if ( rs.wasNull() ) 
        {
        	return null;
        }
        else
        {
        	try
    		{
    			final IEntity entity = this.entityClass.newInstance();
    			entity.setId( columnValue );
    			return entity;
    		}
    		catch ( InstantiationException | IllegalAccessException e )
    		{
    			throw new IllegalStateException( "Error creating an entity", e );
    		}        	
        }
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int, org.hibernate.engine.spi.SessionImplementor)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void nullSafeSet( PreparedStatement st, Object value, int index, SessionImplementor session ) throws HibernateException, SQLException
	{
		if ( value == null || !(value instanceof IEntity) )
		{
			st.setNull( index, Types.BIGINT );
		}
		else
		{
			final IEntity entity = (IEntity) value;
			
			if ( entity.getId() != null )
			{
				st.setLong( index, (Long) entity.getId() );
			}
			else
			{
				st.setNull( index, Types.BIGINT );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	@Override
	public Object deepCopy( Object value ) throws HibernateException
	{
		if ( value == null ) return null;
		
		try
		{
			final ByteArrayOutputStream fbos = new ByteArrayOutputStream();
			final ObjectOutputStream out = new ObjectOutputStream(fbos);
	        out.writeObject(value);
	        out.flush();
	        out.close();

	        final ObjectInputStream in = new ObjectInputStream( new ByteArrayInputStream(fbos.toByteArray()) );
	        return in.readObject();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	@Override
	public boolean isMutable()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Serializable disassemble( Object value ) throws HibernateException
	{
		return (IEntity) this.deepCopy( value );
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	@Override
	public Object assemble( Serializable cached, Object owner ) throws HibernateException
	{
		return this.deepCopy( cached );
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object replace( Object original, Object target, Object owner ) throws HibernateException
	{
		return this.deepCopy( original );
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.ParameterizedType#setParameterValues(java.util.Properties)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParameterValues( Properties parameters )
	{
		try
		{
			final String className = parameters.getProperty( "entity" );
			this.entityClass = ( Class<? extends IEntity> ) Class.forName( className );
		}
		catch ( Exception e )
		{
			throw new IllegalArgumentException( "Using a TransientEntityType you MUST set a parameter with an 'entity' key with value of full entity class name.", e );
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#getPropertyNames()
	 */
	@Override
	public String[] getPropertyNames()
	{
		return new String[]{"id"};
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#getPropertyTypes()
	 */
	@Override
	public Type[] getPropertyTypes()
	{
		return new Type[] { new LongType() };
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#getPropertyValue(java.lang.Object, int)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object getPropertyValue( Object component, int property ) throws HibernateException
	{
		if ( component != null )
		{
			final IEntity entity = (IEntity) component;
			
			if ( entity.getId() != null )
			{
				return entity.getId();
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#setPropertyValue(java.lang.Object, int, java.lang.Object)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setPropertyValue( Object component, int property, Object value ) throws HibernateException
	{
		if ( value != null && component != null )
		{
			final IEntity entity = (IEntity) component;
			entity.setId( (Long) value );			
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#disassemble(java.lang.Object, org.hibernate.engine.spi.SessionImplementor)
	 */
	@Override
	public Serializable disassemble( Object value, SessionImplementor session ) throws HibernateException
	{
		return ( Serializable ) value;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#assemble(java.io.Serializable, org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object assemble( Serializable cached, SessionImplementor session, Object owner ) throws HibernateException
	{
		return cached;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#replace(java.lang.Object, java.lang.Object, org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object replace( Object original, Object target, SessionImplementor session, Object owner ) throws HibernateException
	{
		return this.deepCopy(original);
	}
}