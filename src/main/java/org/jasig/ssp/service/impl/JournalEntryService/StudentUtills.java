package org.jasig.ssp.service.impl.JournalEntryService;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.JournalEntryDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalCaseNotesStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalStepSearchFormTO;

import java.util.HashMap;
import java.util.List;

public class StudentUtills {
    public static void getSomething(
            List<BaseStudentReportTO> persons,
            HashMap map,
            JournalStepSearchFormTO personSearchForm,
            JournalEntryDao getDao,
            List<JournalCaseNotesStudentReportTO> personsWithJournalEntries
    ) {
        for (BaseStudentReportTO person:persons) {
            if (!map.containsKey(person.getSchoolId()) && StringUtils.isNotBlank(person.getCoachSchoolId())) {
                boolean addStudent = true;
                if (personSearchForm.getJournalSourceIds()!=null) {
                    if (getDao().getJournalCountForPersonForJournalSourceIds(person.getId(), personSearchForm.getJournalSourceIds()) == 0) {
                        addStudent = false;
                    }
                }
                if (addStudent) {
                    final JournalCaseNotesStudentReportTO entry = new JournalCaseNotesStudentReportTO(person);
                    personsWithJournalEntries.add(entry);
                    map.put(entry.getSchoolId(), entry);
                }
            }
        }
    }
}
