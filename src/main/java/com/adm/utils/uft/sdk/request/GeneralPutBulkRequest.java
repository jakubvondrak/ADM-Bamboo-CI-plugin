/*
 * Copyright (c) EntIT Software LLC, a Micro Focus company
 *
 * Certain versions of software and/or documents ("Material") accessible here may contain branding from
 * Hewlett-Packard Company (now HP Inc.) and Hewlett Packard Enterprise Company.  As of September 1, 2017,
 * the Material is now offered by Micro Focus, a separately owned and operated company.  Any reference to the HP
 * and Hewlett Packard Enterprise/HPE marks is historical in nature, and the HP and Hewlett Packard Enterprise/HPE
 * marks are the property of their respective owners.
 * __________________________________________________________________
 *
 * MIT License
 *
 * Copyright (c) EntIT Software LLC, a Micro Focus company
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.adm.utils.uft.sdk.request;

import com.adm.utils.uft.RestXmlUtils;
import com.adm.utils.uft.rest.RESTConstants;
import com.adm.utils.uft.sdk.Client;
import com.adm.utils.uft.sdk.ResourceAccessLevel;
import com.adm.utils.uft.sdk.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GeneralPutBulkRequest extends GeneralRequest {

    protected GeneralPutBulkRequest(Client client) {
        super(client);
    }

    protected abstract List<Map<String, String>> getFields();

    @Override
    protected Map<String, String> getHeaders() {

        Map<String, String> ret = new HashMap<String, String>();
        ret.put(RESTConstants.CONTENT_TYPE, RESTConstants.APP_XML_BULK);
        ret.put(RESTConstants.ACCEPT, RESTConstants.APP_XML);

        return ret;
    }

    @Override
    protected Response perform() {
        return _client.httpPut(
                getUrl(),
                getDataBytes(),
                getHeaders(),
                ResourceAccessLevel.PROTECTED);
    }

    private byte[] getDataBytes() {

        StringBuilder builder = new StringBuilder("<Entities>");
        for (Map<String, String> values : getFields()) {
            builder.append("<Entity><Fields>");
            for (String key : values.keySet()) {
                builder.append(RestXmlUtils.fieldXml(key, values.get(key)));
            }
            builder.append("</Fields></Entity>");
        }

        return builder.append("</Entities>").toString().getBytes();

    }
}

