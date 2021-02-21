package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NativeQueryService {

    @Autowired
    private QueryService queryService;

    public List<Tuple> getReportFromRequestVsLoans(LocalDateTime starDate, LocalDateTime endDate) {
        String query = "SELECT\n" +
                "ua.id,\n" +
                "ua.username,\n" +
                "CAST(a.count AS DECIMAL) AS requests,\n" +
                "CAST(b.count AS DECIMAL) AS loans\n" +
                "FROM (\n" +
                "     \tSELECT\n" +
                "\t\trci.user_account_id_fk,\n" +
                "\t\tcount(*)\n" +
                "\t\tFROM schedule_pilot_db.request_check_in rci\n" +
                "\t\tWHERE rci.created_date >= :startDate AND rci.created_date <= :endDate \n" +
                "\t\tGROUP BY rci.user_account_id_fk\n" +
                "     ) AS a\n" +
                "INNER JOIN (\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\trci.user_account_id_fk,\n" +
                "\t\t\t\tcount(*)\n" +
                "\t\t\t\tFROM schedule_pilot_db.ticket_check_in tci\n" +
                "\t\t\t\tINNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id\n" +
                "\t\t\t\tWHERE tci.created_date >= :startDate AND tci.created_date <= :endDate \n" +
                "GROUP BY rci.user_account_id_fk\n" +
                "           ) AS b ON a.user_account_id_fk = b.user_account_id_fk\n" +
                "INNER JOIN schedule_pilot_db.user_account ua ON a.user_account_id_fk = ua.id LIMIT 10";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", starDate);
        parameters.put("endDate", endDate);

        return this.queryService.getQueryResultListTuple(query, parameters);
    }

    public List<Tuple> getReportFromRequestVsLoansByUserAccount(Long userAccountId, LocalDateTime starDate, LocalDateTime endDate) {
        String query = "SELECT\n" +
                "ua.id,\n" +
                "ua.username,\n" +
                "CAST(a.count AS DECIMAL) AS requests,\n" +
                "CAST(b.count AS DECIMAL) AS loans\n" +
                "FROM (\n" +
                "     \tSELECT\n" +
                "\t\trci.user_account_id_fk,\n" +
                "\t\tcount(*)\n" +
                "\t\tFROM schedule_pilot_db.request_check_in rci\n" +
                "\t\tWHERE rci.created_date >= :startDate AND rci.created_date <= :endDate \n" +
                "AND rci.user_account_id_fk = :userAccountId " +
                "\t\tGROUP BY rci.user_account_id_fk\n" +
                "     ) AS a\n" +
                "INNER JOIN (\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\trci.user_account_id_fk,\n" +
                "\t\t\t\tcount(*)\n" +
                "\t\t\t\tFROM schedule_pilot_db.ticket_check_in tci\n" +
                "\t\t\t\tINNER JOIN schedule_pilot_db.request_check_in rci ON tci.request_check_in_id_fk = rci.id\n" +
                "\t\t\t\tWHERE tci.created_date >= :startDate AND tci.created_date <= :endDate \n" +
                "AND rci.user_account_id_fk = :userAccountId " +
                "GROUP BY rci.user_account_id_fk\n" +
                "           ) AS b ON a.user_account_id_fk = b.user_account_id_fk\n" +
                "INNER JOIN schedule_pilot_db.user_account ua ON a.user_account_id_fk = ua.id LIMIT 10";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", starDate);
        parameters.put("endDate", endDate);
        parameters.put("userAccountId", userAccountId);

        return this.queryService.getQueryResultListTuple(query, parameters);
    }
}
