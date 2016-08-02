package ua.tumakha.yuriy.data.filter.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yuriy Tumakha
 */
class IgnoreDomainsFilter {

    private List<String> domains;

    IgnoreDomainsFilter(List<String> domains) {
        this.domains = domains.stream().filter(Objects::nonNull).map(String::trim).collect(Collectors.toList());
    }


    boolean isAllowedEmail(String email) {
        return domains.stream().allMatch(domain -> !email.endsWith("@" + domain));
    }

}
