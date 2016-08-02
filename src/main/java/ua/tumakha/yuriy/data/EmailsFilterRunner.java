package ua.tumakha.yuriy.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.data.filter.EmailsFilter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Yuriy Tumakha
 */
@Component
public class EmailsFilterRunner implements CommandLineRunner {

    private static final String DEFAULT_EMAILS_FILE = "emails.txt";
    private static final String DEFAULT_DOMAINS_FILE = "domains.txt";
    private static final String DEFAULT_FILTERED_OUTPUT_FILE = "filtered.emails.txt";

    @Autowired
    private EmailsFilter emailsFilter;

    @Override
    public void run(String... args) throws Exception {
        String emailsFile = args.length > 0 ? args[0] : DEFAULT_EMAILS_FILE;
        String domainsFile = args.length > 1 ? args[1] : DEFAULT_DOMAINS_FILE;
        String outputFile = args.length > 2 ? args[2] : DEFAULT_FILTERED_OUTPUT_FILE;

        Path emailsPath = Paths.get(emailsFile);
        Path domainsPath = Paths.get(domainsFile);
        Path outputPath = Paths.get(outputFile);

        emailsFilter.filter(emailsPath, domainsPath, outputPath);
    }

}
