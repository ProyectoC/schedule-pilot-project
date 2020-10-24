package com.schedulepilot.core.tasks;

import com.schedulepilot.core.util.dto.OrderParameter;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This task allows pagination and ordering of queries from an @Repository component
 * <p>
 * It allows to generate the necessary objects to paginate and order for any entity
 *
 * @author cristhian.castillo@ptesa.com
 * @since 1.0
 */

@Component
@Scope("prototype")
public class PaginationAndOrderTask {

    // Constants
    private static final Logger CLASS_LOGGER = LogManager.getLogger(PaginationAndOrderTask.class);

    /**
     * Pattern used to validate the sort fields.
     * Must come in the following form <sort_order>:<field>:<priority>
     */
    private static final Pattern PATTERN = Pattern.compile("(desc|asc):([^ ]*):(\\d)");

    private static final String ASC_SORT_TYPE = "asc";
    private static final String DESC_SORT_TYPE = "desc";
    private static final String PAGE_PARAMETER = "page";
    private static final String PER_PAGE_PARAMETER = "per_page";
    private static final String ORDER_BY_PARAMETER = "order_by";

    // Fields

    private int page;
    private int pageSize;

    /**
     * Priority queue used to sort the fields according to the specified priority level.
     */
    private final PriorityQueue<OrderParameter> orderParametersQueue;

    // Input fields

    private final Map<String, String> parameters;
    private final List<String> listAttributes;
    private final Map<String, String> listMapAttributes;

    // Output fields

    @Getter
    Pageable pageData;

    @Getter
    Sort sortData;

    // Constructors

    public PaginationAndOrderTask(Map<String, String> parameters, List<String> listAttributes) {
        this.parameters = parameters;
        this.listAttributes = listAttributes;
        this.listMapAttributes = new HashMap<>();

        this.page = -1;
        this.pageSize = -1;
        this.orderParametersQueue = new PriorityQueue<>();
    }

    public PaginationAndOrderTask(Map<String, String> parameters, Map<String, String> listMapAttributes) {
        this.parameters = parameters;
        this.listAttributes = new ArrayList<>();
        this.listMapAttributes = listMapAttributes;

        this.page = -1;
        this.pageSize = -1;
        this.orderParametersQueue = new PriorityQueue<>();
    }

    // Class logic

    public void execute() {
        CLASS_LOGGER.info("Starting");

        // OrderBy
        this.initializeOrder();
        this.generateSort();

        // Pagination, IMPORTANT: This method must be executed after the generateSort() method
        this.initializePagination();
        this.generatePagination();

        CLASS_LOGGER.info("Finished successfully");
    }

    /**
     * Validate if the list of parameters contains the parameter 'order_by'.
     * If it does, the process separates all possible sort fields to initialize the priority queue.
     */
    private void initializeOrder() {
        if (parameters.containsKey(ORDER_BY_PARAMETER)) {
            String orderByParameters = parameters.get(ORDER_BY_PARAMETER);
            String[] parameterOrderArray = orderByParameters.split(",");
            if (parameterOrderArray.length > 0) {
                this.initializePriorityQueue(parameterOrderArray);
            }
        }
    }

    /**
     * Initializes the priority queue with the identified sort fields.
     *
     * @param parameterOrderArray List of fields to sort.
     */
    private void initializePriorityQueue(String[] parameterOrderArray) {
        for (String parameterTemp : parameterOrderArray) {
            Matcher matcher = PATTERN.matcher(parameterTemp);
            if (!matcher.matches()) {
                continue;
            }
            String type = matcher.group(1);
            String property = matcher.group(2);
            int priority = Integer.parseInt(matcher.group(3));

            if (!this.listAttributes.isEmpty()) {
                if (!this.isAnAttribute(property)) {
                    continue;
                }
            } else {
                property = this.getKeyFromMapAttributes(property);
                if (property == null) {
                    continue;
                }
            }

            OrderParameter orderParameter = OrderParameter.builder()
                    .type(type)
                    .value(property)
                    .priority(priority).build();
            this.orderParametersQueue.add(orderParameter);
        }
    }

    /**
     * Use the priority queue to create Sort instances.
     */
    private void generateSort() {
        for (int i = 0; i < this.orderParametersQueue.size(); i++) {
            OrderParameter temp = orderParametersQueue.poll();
            if (i == 0) {
                if (temp.getType().equals(ASC_SORT_TYPE)) {
                    this.sortData = orderByAsc(temp.getValue());
                } else {
                    this.sortData = orderByDesc(temp.getValue());
                }
            } else {
                Sort sortTemp;
                if (temp.getType().equals(ASC_SORT_TYPE)) {
                    sortTemp = orderByAsc(temp.getValue());
                } else {
                    sortTemp = orderByDesc(temp.getValue());
                }
                this.sortData = this.sortData.and(sortTemp);
            }
        }
    }

    /**
     * Validate if the list of parameters contains the parameter 'page' and the parameter 'per_page'.
     * If it does, the process initializes the variable of number of records per page and the page number.
     */
    private void initializePagination() {
        if (this.parameters.containsKey(PAGE_PARAMETER) && this.parameters.containsKey(PER_PAGE_PARAMETER)) {
            this.page = Integer.parseInt(this.parameters.get(PAGE_PARAMETER));
            this.pageSize = Integer.parseInt(this.parameters.get(PER_PAGE_PARAMETER));
        }
    }

    /**
     * Try to create a Page applying the Sort parameters or create a Page without applying the Sort parameters.
     */
    private void generatePagination() {
        if ((page > -1 && pageSize > -1) && this.sortData != null) {
            this.pageData = PageRequest.of(page, pageSize, this.sortData);
        } else if (page > -1 && pageSize > -1) {
            this.pageData = PageRequest.of(page, pageSize);
        }
    }

    /**
     * Validate if a field found in the list of parameters in a valid field name to apply the ordering.
     *
     * @param name Parameter name.
     * @return true: if it is a valid parameter, false: when it is not a valid parameter.
     */
    private boolean isAnAttribute(String name) {
        for (String attribute : this.listAttributes) {
            if (attribute.equals(name))
                return true;
        }
        return false;
    }

    /**
     * Returns the correct sql column of ordering according to the name of the parameter map key.
     *
     * @param name Key name.
     * @return Sql column.
     */
    private String getKeyFromMapAttributes(String name) {
        for (Map.Entry<String, String> entry : this.listMapAttributes.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }


    /**
     * Return a Sort instance of descending type.
     *
     * @param property Sort field.
     * @return Sort instance
     */
    private Sort orderByDesc(String property) {
        return new Sort(Sort.Direction.DESC, property);
    }

    /**
     * Return a Sort instance of ascending type.
     *
     * @param property Sort field.
     * @return Sort instance
     */
    private Sort orderByAsc(String property) {
        return new Sort(Sort.Direction.ASC, property);
    }
}
