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

import org.picketlink.common.exceptions.ProcessingException;
import org.picketlink.identity.federation.core.saml.v2.interfaces.SAML2HandlerRequest;
import org.picketlink.identity.federation.core.saml.v2.interfaces.SAML2HandlerResponse;
import org.picketlink.identity.federation.web.handlers.saml2.SAML2AuthenticationHandler;

import java.util.ArrayList;
import java.util.List;

public class SAML2OktaAuthenticationHandler extends SAML2AuthenticationHandler {

	private static final String DEFAULT_USER_ROLE = "DEFAULT_USER_ROLE";

	@Override
	public void handleStatusResponseType(SAML2HandlerRequest request, SAML2HandlerResponse response) throws ProcessingException {
		super.handleStatusResponseType(request, response);
		Object defRoleParamVal = handlerConfig.getParameter(DEFAULT_USER_ROLE);
		if (defRoleParamVal != null) {
			String role = String.valueOf(defRoleParamVal);
			List<String> roles = response.getRoles();
			if (roles == null || roles.isEmpty()) {
				roles = new ArrayList<String>();
				roles.add(role);
				response.setRoles(roles);
			} else if (!roles.contains(role))
				response.getRoles().add(role);
		}
	}
}
