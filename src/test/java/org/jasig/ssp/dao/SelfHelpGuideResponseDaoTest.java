/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideResponseDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageDaoTest.class);

	@Autowired
	private SelfHelpGuideResponseDao dao;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	protected Person admin;

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testSaveNew()} that checks that the Auditable auto-fill
	 * properties are correctly filled.
	 */
	@Before
	public void setup() {
		admin = new Person(Person.SYSTEM_ADMINISTRATOR_ID);
		securityService.setCurrent(admin);
	}

	@Test
	public void forEarlyAlert() {
		assertList(dao.forEarlyAlert());
	}

	private void assertList(final List<SelfHelpGuideResponse> objects) {
		for (final SelfHelpGuideResponse object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void testGetAll() {
		assertList((List<SelfHelpGuideResponse>) dao.getAll(ObjectStatus.ALL)
				.getRows());
	}

	@Test
	public void getAllForPersonId() {
		assertList(dao.getAllForPersonId(admin.getId(), new SortingAndPaging(
				ObjectStatus.ACTIVE)).getRows());
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		final Person person = personDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next();
		assertNotNull(
				"Needed random Person to use for testing, but none was found in the database.",
				person.getId());

		SelfHelpGuideResponse obj = new SelfHelpGuideResponse();
		obj.setSelfHelpGuide(new SelfHelpGuide(UUID
				.fromString("2597d6a8-c95e-40a5-a3fd-e0d95967b1a0")));
		obj.setPerson(person);
		obj.setCancelled(false);
		obj.setCompleted(false);
		obj.setEarlyAlertSent(true);
		dao.save(obj);

		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		final List<SelfHelpGuideResponse> all = (List<SelfHelpGuideResponse>) dao
				.getAll(ObjectStatus.ACTIVE).getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	public void testSave() {
		final SelfHelpGuideResponse obj = new SelfHelpGuideResponse();
		obj.setEarlyAlertSent(false);
		obj.setCompleted(false);
		final SelfHelpGuideResponse saved = dao.save(obj);

		assertNotNull("Saved instance should not have been null.", saved);
		assertNotNull("Saved instance ID should not have been null.",
				saved.getId());
		assertFalse(saved.isCompleted());

		saved.setCompleted(true);

		final SelfHelpGuideResponse completed = dao.save(saved);
		assertTrue(completed.isCompleted());
	}

	protected void assertList(final Collection<SelfHelpGuideResponse> objects) {
		for (final SelfHelpGuideResponse object : objects) {
			assertNotNull(object.getId());
		}
	}
}
