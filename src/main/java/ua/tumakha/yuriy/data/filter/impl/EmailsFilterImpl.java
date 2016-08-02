package ua.tumakha.yuriy.data.filter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.tumakha.yuriy.data.filter.EmailsFilter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Yuriy Tumakha
 */
@Service
public class EmailsFilterImpl implements EmailsFilter {

    private static final Logger LOG = LoggerFactory.getLogger(EmailsFilterImpl.class);

    private long filteredEmailsCount;

    @Override
    public synchronized void filter(Path emailsFile, Path domainsFile, Path outputFile) throws IOException {
        List<String> domains = Files.readAllLines(domainsFile);
        LOG.info("Domains to ignore loaded from {}", domainsFile.normalize());
        if (domains == null || domains.isEmpty()) {
            LOG.error("Domains list is empty");
            return;
        }
        final IgnoreDomainsFilter domainsFilter = new IgnoreDomainsFilter(domains);

        try (Stream<String> filteredEmails = Files.lines(emailsFile)
                .map(String::trim).filter(not(String::isEmpty)).filter(domainsFilter::isAllowedEmail)) {
            createDirectoriesForFile(outputFile);
            filteredEmailsCount = 0;
            try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardOpenOption.CREATE)) {
                filteredEmails.forEach(email -> writeEmail(writer, email));
            }
            LOG.info("Emails file {} filtered.", emailsFile.normalize());
            LOG.info("{} filtered emails saved to {}", filteredEmailsCount, outputFile.toAbsolutePath().normalize());
        }
    }

    /**
     * Creates directories for file if doesn't exist.
     *
     * @param file
     * @throws IOException
     */
    private void createDirectoriesForFile(Path file) throws IOException {
        Path directory = file.getParent();
        if (directory != null && !Files.exists(directory)) {
            Files.createDirectories(directory);
            LOG.info("Created directory {}", directory);
        }
    }

    private void writeEmail(BufferedWriter writer, String email) {
        try {
            writer.write(email);
            writer.newLine();
            filteredEmailsCount++;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private <T> Predicate<T> not(Predicate<T> p) {
        return p.negate();
    }

}
