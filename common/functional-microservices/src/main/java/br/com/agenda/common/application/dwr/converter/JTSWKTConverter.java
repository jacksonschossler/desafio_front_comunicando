package br.com.agenda.common.application.dwr.converter;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.directwebremoting.ConversionException;
import org.directwebremoting.extend.AbstractConverter;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.NonNestedOutboundVariable;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.extend.ProtocolConstants;
import org.directwebremoting.util.JavascriptUtil;

/**
 * Conversor entre tipos de geometria da biblioteca JTS e o formato WKT.
 *
 * @author eduardo
 */
public class JTSWKTConverter extends AbstractConverter
{
	/**
	 *
	 */
	private final WKTReader wktReader;

	/**
	 *
	 */
	private final WKTWriter wktWriter;

	/**
	 *
	 */
	public JTSWKTConverter()
	{
		final GeometryFactory geometryFactory = new GeometryFactory( new PrecisionModel(), 4326 );
		this.wktReader = new WKTReader( geometryFactory );
		this.wktWriter = new WKTWriter();
	}

	@Override
	public Object convertInbound( Class<?> paramType, InboundVariable data ) throws ConversionException
	{
		if ( data == null )
		{
			return null;
		}

		String value = data.urlDecode();

		if ( value.trim().equals( ProtocolConstants.INBOUND_NULL ) )
		{
			return null;
		}

		try
		{
			return this.wktReader.read( value );
		}
		catch ( ParseException e )
		{
			throw new ConversionException( paramType, e );
		}
	}

	@Override
	public OutboundVariable convertOutbound( Object data, OutboundContext outctx ) throws ConversionException
	{
		final String wkt = JavascriptUtil.escapeJavaScript( this.wktWriter.write( (Geometry) data ), false );
		return new NonNestedOutboundVariable( '\"' + wkt + '\"' );
	}
}
