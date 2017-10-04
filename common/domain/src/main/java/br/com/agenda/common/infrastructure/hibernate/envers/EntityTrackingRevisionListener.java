package br.com.agenda.common.infrastructure.hibernate.envers;

import java.io.Serializable;

import org.hibernate.envers.RevisionType;

/**
 * @author rodrigo@eits.com.br
 * @since 06/12/2012
 * @version 1.0
 */
public class EntityTrackingRevisionListener implements org.hibernate.envers.EntityTrackingRevisionListener
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Override
	public void newRevision( Object revisionEntity )
	{
		try
		{
//			final User user = ContextHolder.getAuthenticatedUser();
//			( (Revisao) revisionEntity ).setUserId( user.getId() );
		}
		catch ( Exception e )
		{
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.envers.EntityTrackingRevisionListener#entityChanged(java.lang.Class, java.lang.String, java.io.Serializable, org.hibernate.envers.RevisionType, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void entityChanged( Class entityClass, String entityName, Serializable entityId, RevisionType revisionType, Object revisionEntity )
	{
	}

	/*-------------------------------------------------------------------
	 *						GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
}