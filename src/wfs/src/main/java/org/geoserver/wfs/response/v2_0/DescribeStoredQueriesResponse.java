/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * (c) 2001 - 2013 OpenPlans
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wfs.response.v2_0;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.namespace.QName;
import net.opengis.wfs20.DescribeStoredQueriesResponseType;
import net.opengis.wfs20.StoredQueryDescriptionType;
import net.opengis.wfs20.QueryExpressionTextType;
import org.geoserver.config.GeoServer;
import org.geoserver.platform.Operation;
import org.geoserver.platform.ServiceException;
import org.geotools.wfs.v2_0.WFS;
import org.geotools.xml.Encoder;

public class DescribeStoredQueriesResponse extends WFSResponse {

    public DescribeStoredQueriesResponse(GeoServer gs) {
        super(gs, DescribeStoredQueriesResponseType.class);
    }

    @Override
    protected void encode(Encoder encoder, Object value, OutputStream output, Operation op)
            throws IOException, ServiceException {
        DescribeStoredQueriesResponseType response = (DescribeStoredQueriesResponseType) value;
        for (StoredQueryDescriptionType sqd : response.getStoredQueryDescription()) {
            if(sqd.getQueryExpressionText()!=null){
                for(QueryExpressionTextType qet: sqd.getQueryExpressionText()) {
                    if (qet.getReturnFeatureTypes() != null) {
                        for (QName qName : qet.getReturnFeatureTypes()) {
                            if (qName.getNamespaceURI() != null && qName.getPrefix() != null) {
                                encoder.getNamespaces()
                                    .declarePrefix(qName.getPrefix(), qName.getNamespaceURI());
                            }
                        }
                    }
                }
            }
        }
        encoder.encode(value, WFS.DescribeStoredQueriesResponse, output);
    }
}
