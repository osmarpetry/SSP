/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalCourseDao;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalPersonPlanStatusDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalPersonPlanStatusService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalPersonPlanStatusServiceImpl extends
		AbstractExternalDataService<ExternalPersonPlanStatus> implements
		ExternalPersonPlanStatusService {


	@Autowired
	transient private ExternalPersonPlanStatusDao dao;

	@Override
	protected ExternalPersonPlanStatusDao getDao() {
		return dao;
	}
	
	@Override
	public ExternalPersonPlanStatus getBySchoolId(String schoolId)
			throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		return getDao().getBySchoolId(schoolId);
	}

}