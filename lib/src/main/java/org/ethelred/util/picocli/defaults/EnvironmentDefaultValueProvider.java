package org.ethelred.util.picocli.defaults;

import com.google.common.annotations.VisibleForTesting;
import java.util.function.Function;
import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;

public class EnvironmentDefaultValueProvider implements IDefaultValueProvider {

    @VisibleForTesting
    protected static Function<String, String> envLoader = System::getenv;

    @Override
    public String defaultValue(ArgSpec argSpec) throws Exception {
        if (argSpec.isOption()) {
            OptionSpec option = (OptionSpec) argSpec;
            CommandSpec command = option.command();
            String name = _normalize(
                command.name() + "-" + option.longestName()
            );
            return envLoader.apply(name);
        }
        return null;
    }

    private String _normalize(String input) {
        return input
            .toUpperCase()
            .replaceAll("^\\W*", "")
            .replaceAll("\\W+", "_");
    }
}
