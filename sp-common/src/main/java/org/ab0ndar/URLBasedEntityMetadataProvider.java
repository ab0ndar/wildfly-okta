/**
 Copyright 2016 ab0ndar

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.ab0ndar;

import org.picketlink.common.util.StaxParserUtil;
import org.picketlink.identity.federation.core.interfaces.IMetadataProvider;
import org.picketlink.identity.federation.core.parsers.saml.metadata.SAMLEntityDescriptorParser;
import org.picketlink.identity.federation.core.saml.md.providers.AbstractMetadataProvider;
import org.picketlink.identity.federation.saml.v2.metadata.EntityDescriptorType;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;
import java.util.Map;

public class URLBasedEntityMetadataProvider extends AbstractMetadataProvider implements
		IMetadataProvider<EntityDescriptorType> {

	private static final String FILE_URL = "URL";

	private String fileUrlOptionValue;

	@SuppressWarnings("unused")
	private PublicKey encryptionKey;

	@SuppressWarnings("unused")
	private PublicKey signingKey;
	private URL fileUrl;

	@Override
	public void init(Map<String, String> options) {
		super.init(options);
		fileUrlOptionValue = options.get(FILE_URL);
		if (fileUrlOptionValue == null)
			throw new IllegalArgumentException(FILE_URL);

		try {
			fileUrl = new URL(fileUrlOptionValue);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(FILE_URL);
		}
	}

	/**
	 * @see IMetadataProvider#getMetaData()
	 */
	public EntityDescriptorType getMetaData() {
		URLConnection connection;
		try {
			connection = fileUrl.openConnection();
			SAMLEntityDescriptorParser parser = new SAMLEntityDescriptorParser();
			return (EntityDescriptorType) parser.parse(StaxParserUtil.getXMLEventReader(connection.getInputStream()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see IMetadataProvider#isMultiple()
	 */
	public boolean isMultiple() {
		return false;
	}

	public void injectEncryptionKey(PublicKey publicKey) {
		this.encryptionKey = publicKey;
	}

	public void injectFileStream(InputStream fileStream) {
	}

	public void injectSigningKey(PublicKey publicKey) {
		this.signingKey = publicKey;
	}

	public String requireFileInjection() {
		return null;
	}
}