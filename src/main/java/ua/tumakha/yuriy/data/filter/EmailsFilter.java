package ua.tumakha.yuriy.data.filter;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Yuriy Tumakha
 */
public interface EmailsFilter {

    void filter(Path emailsFile, Path domainsFile, Path outputFile) throws IOException;

}
